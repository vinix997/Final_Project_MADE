package com.example.submission_3.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.submission_3.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, new SettingsFragment())
                .commit();
    }

}
