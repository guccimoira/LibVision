package com.example.imagepro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import org.opencv.android.OpenCVLoader;

public class AdminHomePage extends AppCompatActivity {

    static {
        if(OpenCVLoader.initDebug()){
            Log.d("AdminHomePage: ","Opencv is loaded");
        }
        else {
            Log.d("AdminHomePage: ","Opencv failed to load");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Initialize CardViews
        CardView bookCatalogCard = findViewById(R.id.bookCatalogCard);
        CardView bookCounterCard = findViewById(R.id.bookCounterCard);
        CardView profileCard = findViewById(R.id.profileCard);
        CardView logoutCard = findViewById(R.id.logoutCard);

        // Directly navigate to BookCatalogPage
        bookCatalogCard.setOnClickListener(v -> {
            startActivity(new Intent(AdminHomePage.this, BookCatalogPage.class));
        });

        bookCounterCard.setOnClickListener(v -> {
            startActivity(new Intent(AdminHomePage.this,CameraActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });

        profileCard.setOnClickListener(v -> {
            startActivity(new Intent(AdminHomePage.this, LibrarianProfile.class));
        });

        logoutCard.setOnClickListener(v -> {
            LogoutConfirmationDialog dialog = new LogoutConfirmationDialog();
            dialog.show(getSupportFragmentManager(), "LogoutConfirmationDialog");
        });
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(AdminHomePage.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AdminHomePage.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
