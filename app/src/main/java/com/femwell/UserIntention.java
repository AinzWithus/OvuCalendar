package com.femwell;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.femwell.R;
import com.femwell.databinding.ActivityCreateAccountBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

public class UserIntention extends AppCompatActivity {
    //delcaration of the useful variables for the pop in and info colloection
    private Dialog popupDialogforPeriodOpt;
    private Dialog popupDialogforPregnacyOpt;

    int periodOkBtnId = R.id.periodOkbtn;
   // int periodSkipBtnId = R.id.periodSkippedbtn;
    int pregnancyOkBtnId = R.id.pregancyOkbtn;
    //int pregnancySkipBtnId = R.id.pregancySkippedbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_intention);


        // initialise the popUpDialog for the collect_period_inital_data layout ress
        popupDialogforPeriodOpt = new Dialog(this); // intialise for the period option
        popupDialogforPeriodOpt.setContentView(R.layout.collect_period_initial_data);

        popupDialogforPregnacyOpt = new Dialog(this);
        popupDialogforPregnacyOpt.setContentView(R.layout.collect_pregnancy_initial_data);

    }

    private void showPopup(Dialog popupDialog, int Id) {
        if (popupDialog != null && !popupDialog.isShowing()) {
            // set the relative position of the popup on screen
            WindowManager.LayoutParams layoutParams;
            layoutParams = Objects.requireNonNull(popupDialog.getWindow()).getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = 750;
            popupDialog.show();

            // Animate popup slide-up entrance
            View popupView = popupDialog.getWindow().getDecorView();
            int translationY = popupView.getHeight();
            ObjectAnimator slideUp = ObjectAnimator.ofFloat(popupView, "translationY", translationY, 0);
            slideUp.setDuration(600);
            slideUp.setInterpolator(new BounceInterpolator());
            popupDialog.getWindow().setAttributes(layoutParams);

            AnimatorSet animationSet = new AnimatorSet();
            animationSet.play(slideUp);
            animationSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animation) {
                    popupDialog.show(); // Show after animation starts
                }

                @Override
                public void onAnimationEnd(@NonNull Animator animation) {
                    // Handle animation end (optional)
                }

                @Override
                public void onAnimationCancel(@NonNull Animator animation) {
                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {
                }
            });

            animationSet.start();

            // set the onclickListener to the skip and ok btn
            Button Dialogbutton = popupDialog.findViewById(Id);

            // Now you can work with the button as needed
            if (Dialogbutton != null) {
                // Button found, set OnClickListener
                Dialogbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserIntention.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                Toast.makeText(UserIntention.this, "Cannot find the btn ", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void goToPopFillPreiod(View view){
        showPopup(popupDialogforPeriodOpt, periodOkBtnId);
    }
    public void goToPregnancyFillUpPop(View view){
        showPopup(popupDialogforPregnacyOpt,pregnancyOkBtnId);
    }

    public void showDatePickerDialog1(View view) {
        TextInputEditText myTextInput = (TextInputEditText) view;


        DatePickerFragment newFragment = new DatePickerFragment(myTextInput, this);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        TextInputEditText editor;
        Context context;
        public DatePickerFragment(TextInputEditText myeditor, Context mycontext){
            this.editor = myeditor;
            this.context = mycontext;

        }

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
            editor.setText(day + "/" + (month + 1) + "/" + year);
        }
    }
}
