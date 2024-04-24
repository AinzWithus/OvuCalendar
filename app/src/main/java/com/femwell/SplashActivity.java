package com.femwell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.femwell.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private final long splashDuration = 1000; // 2 seconds
    private final String isFirstTimeKey = "isFirstTime";
    private final String hasCreatedAccountKey = "hasCreatedAccount";
    private final String isLoggedInKey = "isLoggedIn";
    private final String isLoggedOutKey = "isLoggedOut";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            binding = ActivitySplashBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            SharedPreferences sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

            // Check if the app is launched for the first time or after reinstallation
            boolean isFirstTime = sharedPrefs.getBoolean(isFirstTimeKey, true);
            boolean hasCreatedAccount = sharedPrefs.getBoolean(hasCreatedAccountKey, false);

            // Delay using Handler to display the splash screen
            new Handler().postDelayed(() -> {
                if (isFirstTime) {
                    // Set the isFirstTime flag to false
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(isFirstTimeKey, false);
                    editor.apply();

                    // Redirect to the introduction or onboarding activity
                    startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                } else {
                    // Check if the user has logged  out
                    boolean isLoggedOut = sharedPrefs.getBoolean(isLoggedOutKey, false);
                    if (isLoggedOut) {
                        // Reset the isLoggedOut flag to false
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putBoolean(isLoggedOutKey, false);
                        editor.apply();

                        // Redirect to the login activity
                        startActivity(new Intent(SplashActivity.this, Login.class));
                    } else {
                        // Check if the user is logged in or has created an account
                        boolean isLoggedIn = sharedPrefs.getBoolean(isLoggedInKey, false);

                        if (isLoggedIn || hasCreatedAccount) {
                            // Redirect to the homepage
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        } else {
                            // Redirect to the login activity
                            startActivity(new Intent(SplashActivity.this, Login.class));
                        }
                    }
                }

                // Close the splash activity
                finish();
            } , splashDuration);
        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
}