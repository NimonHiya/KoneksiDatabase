package com.example.koneksidatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrasiActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button buttonRegister;
    TextView textViewLoginLink;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DataHelper(this);
        editTextUsername = findViewById(R.id.editTextUsernameRegister);
        editTextPassword = findViewById(R.id.editTextPasswordRegister);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLoginLink = findViewById(R.id.textViewLoginLink);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (dbHelper.register(username, password)) {
                    Toast.makeText(RegistrasiActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegistrasiActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrasiActivity.this, LoginActivity.class));
            }
        });
    }
}
