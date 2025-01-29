package com.example.imagepro;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ProfileFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);


//        // Set up the Edit Profile button click listener
//        Button editProfileButton = rootView.findViewById(R.id.EditProfileLink);
//        editProfileButton.setOnClickListener(v -> openEditProfile());


        // Set up the Logout button click listener
        Button logoutButton = rootView.findViewById(R.id.Logout);
        logoutButton.setOnClickListener(v -> showLogoutConfirmationDialog());


        return rootView;
    }


    // Method to open the Edit Profile activity
    private void openEditProfile() {
        // Create an intent to start EditProfileActivity
        Intent intent = new Intent(getActivity(), EditProfile.class);
        startActivity(intent);
    }


    // Method to show the logout confirmation dialog
    private void showLogoutConfirmationDialog() {
        // Create the dialog
        Dialog logoutDialog = new Dialog(requireContext());
        logoutDialog.setContentView(R.layout.activity_logout_confirmation_dialog);
        logoutDialog.setCancelable(true);


        // Find the buttons in the dialog
        Button cancelLogoutButton = logoutDialog.findViewById(R.id.cancelLogoutButton);
        Button confirmLogoutButton = logoutDialog.findViewById(R.id.logoutButton);


        // Cancel button dismisses the dialog
        cancelLogoutButton.setOnClickListener(v -> logoutDialog.dismiss());


        // Confirm button performs the logout action
        confirmLogoutButton.setOnClickListener(v -> {
            logoutDialog.dismiss();
            performLogout();
        });


        // Show the dialog
        logoutDialog.show();
    }


    // Method to perform the logout action and redirect to the login page
    private void performLogout() {
        // Add your logout logic here (e.g., clear user session or tokens)


        // Redirect to the login activity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the activity stack
        startActivity(intent);
        requireActivity().finish();
    }
}
