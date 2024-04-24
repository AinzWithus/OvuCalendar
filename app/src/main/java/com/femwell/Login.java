package com.femwell;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        final Button loginBtn = findViewById(R.id.loginbtn);
        final TextView signUp = findViewById(R.id.signUpOpt);
        super.onCreate(savedInstanceState);


       // Making the Input Text Views interactive with the login button
        EditText name = findViewById(R.id.editTextName);
        EditText password = findViewById(R.id.editTextPassword);

        EditText[] editTextArray = {name, password,};

        Drawable clickbg = ContextCompat.getDrawable(this, R.drawable.corner_radius_pinch);
        for (EditText editText : editTextArray) {
            editText.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    // Execute method when EditText gains focus
                    loginBtn.setEnabled(true);
                    loginBtn.setBackground(clickbg);
                }
            });
        }

        // onclick for The login btn

        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this,MainActivity.class);
            startActivity(intent);
            finish();
        });

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, CreateAccount.class);
            startActivity(intent);
            finish();
        });

    }
}