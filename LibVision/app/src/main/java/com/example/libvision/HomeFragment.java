package com.example.imagepro;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {


    private ViewPager2 viewPager2;
    private List<Integer> images = Arrays.asList(R.drawable.pola1, R.drawable.pola2, R.drawable.pola3,
            R.drawable.pola4, R.drawable.pola5, R.drawable.pola6);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        // Set up the ViewPager2 for the image slider
        viewPager2 = view.findViewById(R.id.image_slider);
        ImageSliderAdapter adapter = new ImageSliderAdapter(images);
        viewPager2.setAdapter(adapter);

        // Set up the clickable links
        TextView librariansLink = view.findViewById(R.id.LibrariansLink);
        TextView aboutUsLink = view.findViewById(R.id.AboutUsLink);


        librariansLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLibrariansActivity();
            }
        });


        aboutUsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutUsActivity();
            }
        });


        return view;
    }

    // Method to open LibrariansActivity
    private void openLibrariansActivity() {
        Intent intent = new Intent(getActivity(), TheLibrarians.class); // Use LibrariansActivity class
        startActivity(intent);
    }

    // Method to open AboutUsActivity
    private void openAboutUsActivity() {
        Intent intent = new Intent(getActivity(), TheAboutUs.class); // Use AboutUsActivity class
        startActivity(intent);
    }
}
