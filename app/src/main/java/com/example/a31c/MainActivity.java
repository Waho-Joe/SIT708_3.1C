package com.example.a31c;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mainLayout;
    private Button btnStart;
    private EditText editTextName;
    private Switch switchTheme;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainLayout = findViewById(R.id.main);
        btnStart = findViewById(R.id.btnStart);
        editTextName = findViewById(R.id.editTextName);
        switchTheme = findViewById(R.id.switchTheme);

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);
        switchTheme.setChecked(isDarkMode);
        applyTheme(isDarkMode);

        String returnedName = getIntent().getStringExtra("userName");

        if (returnedName != null) {
            editTextName.setText(returnedName);
            sharedPreferences.edit().putString("userName", returnedName).apply();
        } else {
            String savedName = sharedPreferences.getString("userName", "");
            editTextName.setText(savedName);
        }

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("darkMode", isChecked).apply();
            applyTheme(isChecked);
        });

        btnStart.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            sharedPreferences.edit().putString("userName", name).apply();

            Intent intent = new Intent(MainActivity.this, Quiz.class);
            intent.putExtra("userName", name);
            startActivity(intent);
        });
    }

    private void applyTheme(boolean isDarkMode) {
        if (isDarkMode) {
            mainLayout.setBackgroundColor(Color.parseColor("#121212"));

            switchTheme.setTextColor(Color.WHITE);

            editTextName.setTextColor(Color.WHITE);
            editTextName.setHintTextColor(Color.LTGRAY);

            btnStart.setTextColor(Color.WHITE);
            btnStart.setBackgroundColor(Color.parseColor("#444444"));

        } else {
            mainLayout.setBackgroundColor(Color.WHITE);

            switchTheme.setTextColor(Color.parseColor("#333333"));

            editTextName.setTextColor(Color.BLACK);
            editTextName.setHintTextColor(Color.GRAY);

            btnStart.setTextColor(Color.BLACK);
            btnStart.setBackgroundColor(Color.parseColor("#E0E0E0"));
        }
    }
}