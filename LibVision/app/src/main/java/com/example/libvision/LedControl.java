package com.example.imagepro;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LedControl extends AppCompatActivity {


    private boolean isClicked = false;  // Default state is Off
    private String ipAddress = "http://192.168.1.184";  // NodeMCU's IP address
    private OkHttpClient client = new OkHttpClient();   // Initialize OkHttpClient


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_led_control);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button customButton = findViewById(R.id.customButton);


        // Set the initial state of the button to "Light Off"
        customButton.setText("Light On");
        customButton.setTextColor(getResources().getColor(R.color.white)); // White for "Off"
        customButton.setSelected(true); // "Off" state


        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClicked = !isClicked;


                if (isClicked) {
                    // Change to "LED On" state
                    customButton.setText("Light Off");
                    customButton.setTextColor(getResources().getColor(R.color.dark_green)); // Custom Green Color for On
                    customButton.setSelected(false); // Set selected state for "On"
                    sendRequest("off"); // Send HTTP request to turn on the light
                } else {
                    // Change to "LED Off" state
                    customButton.setText("Light On");
                    customButton.setTextColor(getResources().getColor(R.color.white)); // Custom White Color for Off
                    customButton.setSelected(true); // Set selected state for "Off"
                    sendRequest("on"); // Send HTTP request to turn off the light
                }
            }
        });
    }


    private void sendRequest(final String action) {
        // Use a new thread to perform network operations
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Build the HTTP request
                    Request request = new Request.Builder()
                            .url(ipAddress + "/" + action)
                            .build();


                    // Execute the request
                    Response response = client.newCall(request).execute();


                    // Check if the response is successful
                    if (response.isSuccessful()) {
                        // Optionally, show a toast to indicate the result
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LedControl.this, "LED " + (action.equals("on") ? "OFF" : "ON"), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle error: Show a toast message if there is an exception
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LedControl.this, "Failed to control LED. Check connection.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
