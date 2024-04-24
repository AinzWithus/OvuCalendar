package com.femwell;

import static com.femwell.R.color;
import static com.femwell.R.id;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.femwell.R;
import com.femwell.databinding.ActivityCreateAccountBinding;

import java.util.Calendar;

public class CreateAccount extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;

    // Declare the color for the hint string of the field
    private int paleHintColor = R.color.pale_hint_color;

    String strusername;
    String stremail;
    String strpassword;
    String strphone_number;
    String strDob;
    Button signupBt;
    TextView loginAlt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Binding the layout
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize views
        initViews();

        // Set up listeners
        setupListeners();
    }

    private void initViews() {
        strusername = "";
        stremail = "";
        strpassword = "";
        strphone_number = "";
        strDob = "";
        signupBt = binding.signUp;
        loginAlt = binding.loginAlt;
    }

    private void setupListeners() {
        signupBt.setOnClickListener(v -> createAccountMethod());

        loginAlt.setOnClickListener(v -> goToLogin());

        binding.DOB.setOnClickListener(this::showDatePickerDialog);

        // Set up hint behavior for input fields
        setHintBehavior(binding.name);
        setHintBehavior(binding.email);
        setHintBehavior(binding.password);
        setHintBehavior(binding.phoneno);
        setHintBehavior(binding.DOB);
    }

    private void createAccountMethod() {
        // some other logic will be added for creating a new account/patient!
        // meanwhile we can just pass to another activity
        Intent intent = new Intent(CreateAccount.this, UserIntention.class);
        startActivity(intent);
        finish();
    }

    private void setHintBehavior(TextInputEditText editText) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // Hide hint when focused
                editText.setHint("");
            } else {
                // Show hint when lost focus
                editText.setHint(getString(R.string.hint_placeholder));
                editText.setHintTextColor(ContextCompat.getColor(this, paleHintColor));
            }
        });
    }

    private void goToLogin() {
        Intent intent = new Intent(CreateAccount.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void showDatePickerDialog(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // save the date in db on the patients DOB
            // For example, update the EditText with the selected date
            TextInputEditText editTextDate = requireActivity().findViewById(R.id.DOB);
            editTextDate.setText(day + "/" + (month + 1) + "/" + year);
        }
    }
}
