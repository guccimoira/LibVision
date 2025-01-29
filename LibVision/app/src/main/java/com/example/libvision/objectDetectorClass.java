package com.example.imagepro;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import org.checkerframework.checker.units.qual.A;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class objectDetectorClass {
    // should start from small letter
    private int bookCount;
    // this is used to load model and predict
    private Interpreter interpreter;
    // store all label in array
    private List<String> labelList;
    private int INPUT_SIZE;
    private int PIXEL_SIZE=3; // for RGB
    private int IMAGE_MEAN=0;
    private  float IMAGE_STD=255.0f;
    // use to initialize gpu in app
    private GpuDelegate gpuDelegate;
    private int height=0;
    private  int width=0;

    objectDetectorClass(AssetManager assetManager,String modelPath, String labelPath,int inputSize) throws IOException{
        INPUT_SIZE=inputSize;
        Interpreter.Options options = new Interpreter.Options();
        options.setUseXNNPACK(true); // Optimize CPU inference
        interpreter = new Interpreter(loadModelFile(assetManager, modelPath), options);

        // load labelmap
        labelList=loadLabelList(assetManager,labelPath);


    }

    private List<String> loadLabelList(AssetManager assetManager, String labelPath) throws IOException {
        // to store label
        List<String> labelList=new ArrayList<>();
        // create a new reader
        BufferedReader reader=new BufferedReader(new InputStreamReader(assetManager.open(labelPath)));
        String line;
        // loop through each line and store it to labelList
        while ((line=reader.readLine())!=null){
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    private ByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        // use to get description of file
        AssetFileDescriptor fileDescriptor=assetManager.openFd(modelPath);
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startOffset =fileDescriptor.getStartOffset();
        long declaredLength=fileDescriptor.getDeclaredLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }
    // create new Mat function
    public Mat recognizeImage(Mat matImage) {
        // Convert Mat to Bitmap and handle orientation
        Mat rotatedImage = new Mat();
        Core.flip(matImage.t(), rotatedImage, 1); // Rotate for portrait frame

        Bitmap bitmap = Bitmap.createBitmap(rotatedImage.cols(), rotatedImage.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(rotatedImage, bitmap);

        // Get the image height and width
        height = bitmap.getHeight();
        width = bitmap.getWidth();

        // Scale the image to the input size
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

        // Convert bitmap to byte buffer
        ByteBuffer byteBuffer = convertBitmapToByteBuffer(scaledBitmap);

        // Create input and output structure for the model
        Object[] input = new Object[1];
        input[0] = byteBuffer;

        Map<Integer, Object> outputMap = new TreeMap<>();
        float[][][] boxes = new float[1][10][4];
        float[][] classes = new float[1][10];
        float[][] scores = new float[1][10];

        outputMap.put(0, boxes);
        outputMap.put(1, classes);
        outputMap.put(2, scores);

        try {
            // Run model inference
            interpreter.runForMultipleInputsOutputs(input, outputMap);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any errors during inference (e.g., unsupported operation)
            return matImage; // Return the original image in case of failure
        }

        // Get the output data
        float[][] detectedBoxes = boxes[0];
        float[] detectedScores = scores[0];  // Change from float[][] to float[]
        float[] detectedClasses = classes[0]; // Change from float[][] to float[]

        // Reset book count
        bookCount = 0;

        // Process detected objects
        for (int i = 0; i < 10; i++) {
            float classValue = detectedClasses[i];
            float scoreValue = detectedScores[i];

            if (scoreValue > 0.5) { // Only consider high confidence detections
                String detectedLabel = labelList.get((int) classValue);
                if (!"???".equals(detectedLabel)) {
                    float top = detectedBoxes[i][0] * height;
                    float left = detectedBoxes[i][1] * width;
                    float bottom = detectedBoxes[i][2] * height;
                    float right = detectedBoxes[i][3] * width;

                    // Draw bounding box and label
                    Imgproc.rectangle(rotatedImage, new Point(left, top), new Point(right, bottom), new Scalar(0, 255, 0, 255), 2);
                    Imgproc.putText(rotatedImage, detectedLabel, new Point(left, top), 3, 1, new Scalar(255, 0, 0, 255), 2);

                    // Increment book count if detected label is "book"
                    if ("book".equals(detectedLabel)) {
                        bookCount++;
                    }
                }
            }
        }

        // Get the dimensions of the text
        int fontFace = 3; // Same as using 3
        double fontScale = 1.0;
        int thickness = 2;

        Size textSize = Imgproc.getTextSize("Book Count: " + bookCount, fontFace, fontScale, thickness, null);
        int textWidth = (int) textSize.width;
        int textHeight = (int) textSize.height;

// Calculate the horizontal center position
        int centerX = (rotatedImage.cols() - textWidth) / 2;

// Place the text near the bottom (with some padding above the bottom edge)
        int padding = 20; // Distance from the bottom of the image
        int bottomY = rotatedImage.rows() - padding;

// Set the text color (Yellow)
        Scalar textColor = new Scalar(0, 255, 255, 255);

// Draw the text at the bottom-center of the image
        Imgproc.putText(rotatedImage, "Book Count: " + bookCount, new Point(centerX, bottomY), fontFace, fontScale, textColor, thickness);
        // Rotate back to original orientation
        Core.flip(rotatedImage.t(), matImage, 0);
        return matImage;
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer;
        // some model input should be quant=0  for some quant=1
        // for this quant=0

        int quant=0;
        int size_images=INPUT_SIZE;
        if(quant==0){
            byteBuffer=ByteBuffer.allocateDirect(1*size_images*size_images*3);
        }
        else {
            byteBuffer=ByteBuffer.allocateDirect(4*1*size_images*size_images*3);
        }
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues=new int[size_images*size_images];
        bitmap.getPixels(intValues,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        int pixel=0;

        // some error
        //now run
        for (int i=0;i<size_images;++i){
            for (int j=0;j<size_images;++j){
                final  int val=intValues[pixel++];
                if(quant==0){
                    byteBuffer.put((byte) ((val>>16)&0xFF));
                    byteBuffer.put((byte) ((val>>8)&0xFF));
                    byteBuffer.put((byte) (val&0xFF));
                }
                else {
                    // paste this
                    byteBuffer.putFloat((((val >> 16) & 0xFF))/255.0f);
                    byteBuffer.putFloat((((val >> 8) & 0xFF))/255.0f);
                    byteBuffer.putFloat((((val) & 0xFF))/255.0f);
                }
            }
        }
        return byteBuffer;
    }
}