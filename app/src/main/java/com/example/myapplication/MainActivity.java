package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_DESTINATION = "com.example.travel.DESTINATION";
    public static final String EXTRA_DISTANCE = "com.example.travel.DISTANCE";
    public static final String EXTRA_INSURANCE = "com.example.travel.INSURANCE";

    private EditText etDestination, etDistance;
    private CheckBox cbInsurance;
    private TextView tvStatus;

    private final ActivityResultLauncher<Intent> summaryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    tvStatus.setText("Status: Podróż zatwierdzona! ✅");
                    Toast.makeText(this, "Zapisano do bazy", Toast.LENGTH_SHORT).show();
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    tvStatus.setText("Status: Anulowano przez użytkownika ❌");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDestination = findViewById(R.id.etDestination);
        etDistance = findViewById(R.id.etDistance);
        cbInsurance = findViewById(R.id.cbInsurance);
        tvStatus = findViewById(R.id.tvStatus);

        findViewById(R.id.btnNext).setOnClickListener(v -> sendData());
    }

    private void sendData() {
        String dest = etDestination.getText().toString();
        String distStr = etDistance.getText().toString();

        if (dest.isEmpty() || distStr.isEmpty()) {
            Toast.makeText(this, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra(EXTRA_DESTINATION, dest);
        try {
            intent.putExtra(EXTRA_DISTANCE, Integer.parseInt(distStr));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Dystans musi być liczbą!", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra(EXTRA_INSURANCE, cbInsurance.isChecked());

        summaryLauncher.launch(intent);
    }
}