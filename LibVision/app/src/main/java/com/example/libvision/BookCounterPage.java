package com.example.imagepro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BookCounterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_counter_page);


    }

    @Override
    public void onBackPressed() {
        // Navigate back to AdminHomePage when the back button is pressed
        super.onBackPressed();
        Intent intent = new Intent(BookCounterPage.this, AdminHomePage.class);
        startActivity(intent);
        finish(); // Close the current activity to prevent returning back here
    }
}
