package com.femwell;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment{

    private Button SymptomsBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SymptomsBtn = view.findViewById(R.id.logSymptomsBtn);
        SymptomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a reference to the activity
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    // Start the new activity
                    activity.startActivity(new Intent(activity, LogSymptoms.class));
                }
            }
        });
        return view;
    }
}