package com.example.imagepro;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutConfirmationDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create a dialog and use your custom layout
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.activity_logout_confirmation_dialog);

        // Set up the buttons in the custom layout
        Button btnYes = dialog.findViewById(R.id.logoutButton);
        Button btnNo = dialog.findViewById(R.id.cancelLogoutButton);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout logic
                FirebaseAuth.getInstance().signOut();  // Firebase sign out

                // Redirect to LoginActivity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Clear backstack
                startActivity(intent);

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        return dialog;
    }
}
