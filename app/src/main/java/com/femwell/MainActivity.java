package com.femwell;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.femwell.databinding.ActivityMainBinding;
import com.femwell.databinding.DialogCalenderViewBinding;
//import com.jakewharton.threetenabp.AndroidThreeTen;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_HOME = R.id.navigation_home;
    private static final int MENU_CYCLES = R.id.navigation_more;
    private static final int MENU_TCALENDAR = R.id.navigation_Testcalendar;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AndroidThreeTen.init(this);
        com.femwell.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setting the current date to the textview calenderIcon
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate currentDate = LocalDate.now();
            binding.textHeader.setText(currentDate.getMonth()+"    "+currentDate.getDayOfMonth()+"  ");
            
        }

        binding.textHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarPopup(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(String date) {
                        // Handle the selected date here
                        binding.textHeader.setText(date);
                        // we shall implement more of the change of the interface to match the selected date
                    }
                });

            }
        });

        // set the HomeFragmenLayout as the first frame
        replaceFragment(new HomeFragment());

        binding.navView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == MENU_HOME) {
                replaceFragment(new HomeFragment());
            } else if (itemId == MENU_CYCLES) {
                replaceFragment(new CyclesFragment());
            } else if (itemId == MENU_TCALENDAR) {
                replaceFragment(new TestCalenderFragment());
            }
            return true;
        });




    }

    // Method for Fragment infalation into the nav_host_fragment frame
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
        fragmentTransaction.commit();

    }

    private void showCalendarPopup(OnDateSelectedListener listener) {
        // Create an instance of AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the layout using Data Binding
        DialogCalenderViewBinding binding = DialogCalenderViewBinding.inflate(LayoutInflater.from(this));

        // Set the custom view of the AlertDialog
        builder.setView(binding.getRoot());

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        // Set the CalendarView's date change listener
        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Month is zero-based in Java Calendar, so add 1
            String formattedDate = String.format(Locale.getDefault(), "%s %02d", new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date(year, month, dayOfMonth)), dayOfMonth);

            // Pass the formatted date to the listener
            listener.onDateSelected(formattedDate);

            // Dismiss the dialog
            dialog.dismiss();
        });

        // Show the AlertDialog
        dialog.show();
    }

    // Define an interface for the callback
    interface OnDateSelectedListener {
        void onDateSelected(String date);
    }


}