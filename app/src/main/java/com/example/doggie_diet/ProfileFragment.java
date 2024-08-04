package com.example.doggie_diet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonSave = view.findViewById(R.id.buttonSave);

        // Set up button click listener to save the user's details
        buttonSave.setOnClickListener(v -> {
            // Logic to save the user's details goes here
            // You can use Firebase or SharedPreferences to save the details
        });

        return view;
    }
}
