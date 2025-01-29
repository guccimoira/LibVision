package com.example.imagepro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BookCatalogPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_catalog_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Implement back button functionality
        ImageButton backButton = findViewById(R.id.back_button);
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                onBackPressed(); // Go back to AdminHomePage
            });
        }

        // Implement Filipiniana button functionality
        Button filipinianaButton = findViewById(R.id.filipiniana_button); // Make sure the button has the correct ID
        if (filipinianaButton != null) {
            filipinianaButton.setOnClickListener(v -> {
                // Open FilipinianaSectionScrollingActivity when the button is clicked
                Intent intent = new Intent(BookCatalogPage.this, FilipinianaSectionScrollingActivity.class);
                startActivity(intent);
            });
        }


        // Implement Periodicals button functionality
        Button periodicalsButton = findViewById(R.id.periodicals_button); // Make sure the button has the correct ID
        if (periodicalsButton != null) {
            periodicalsButton.setOnClickListener(v -> {
                // Open FilipinianaSectionScrollingActivity when the button is clicked
                Intent intent = new Intent(BookCatalogPage.this, PeriodicalsSectionScrollingActivity.class);
                startActivity(intent);
            });
        }


        // Implement Circulation button functionality
        Button circulationButton = findViewById(R.id.circulation_section_button); // Make sure the button has the correct ID
        if (circulationButton != null) {
            circulationButton.setOnClickListener(v -> {
                // Open CirculationSectionScrollingActivity when the button is clicked
                Intent intent = new Intent(BookCatalogPage.this, CirculationSectionScrollingActivity.class);
                startActivity(intent);
            });
        }

        // Implement Health Science button functionality
        Button healthscienceButton = findViewById(R.id.health_science_button); // Make sure the button has the correct ID
        if (healthscienceButton != null) {
            healthscienceButton.setOnClickListener(v -> {
                // Open CirculationSectionScrollingActivity when the button is clicked
                Intent intent = new Intent(BookCatalogPage.this, HealthScienceScrollingActivity.class);
                startActivity(intent);
            });
        }

        // Implement Law Library button functionality
        Button lawlibraryButton = findViewById(R.id.law_library_button); // Make sure the button has the correct ID
        if (lawlibraryButton != null) {
            lawlibraryButton.setOnClickListener(v -> {
                // Open CirculationSectionScrollingActivity when the button is clicked
                Intent intent = new Intent(BookCatalogPage.this, LawLibraryScrollingActivity.class);
                startActivity(intent);
            });
        }

        // Implement Medical Library button functionality
        Button medicallibraryButton = findViewById(R.id.medical_library_button); // Make sure the button has the correct ID
        if (medicallibraryButton != null) {
            medicallibraryButton.setOnClickListener(v -> {
                // Open CirculationSectionScrollingActivity when the button is clicked
                Intent intent = new Intent(BookCatalogPage.this, MedicalLibraryScrollingActivity.class);
                startActivity(intent);
            });
        }

        // Implement Reference Section button functionality
        Button referencesectionButton = findViewById(R.id.reference_section_button); // Make sure the button has the correct ID
        if (referencesectionButton != null) {
            referencesectionButton.setOnClickListener(v -> {
                // Open CirculationSectionScrollingActivity when the button is clicked
                Intent intent = new Intent(BookCatalogPage.this, ReferenceSectionScrollingActivity.class);
                startActivity(intent);
            });
        }


    }
}
