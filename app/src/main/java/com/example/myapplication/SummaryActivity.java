package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        String destination = getIntent().getStringExtra(MainActivity.EXTRA_DESTINATION);
        int distance = getIntent().getIntExtra(MainActivity.EXTRA_DISTANCE, 0);
        boolean hasInsurance = getIntent().getBooleanExtra(MainActivity.EXTRA_INSURANCE, false);

        TextView tvSummary = findViewById(R.id.tvSummary);
        tvSummary.setText(String.format("Cel: %s\nDystans: %d km\nUbezpieczenie: %s",
                destination, distance, hasInsurance ? "Tak" : "Nie"));

        findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

        findViewById(R.id.btnCancel).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}