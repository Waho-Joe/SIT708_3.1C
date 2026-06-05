package com.example.a31c;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Result extends AppCompatActivity {

    private LinearLayout resultMain;
    private TextView txtResultTitle, txtScore;
    private Button btnNewQuiz, btnFinish;
    private SharedPreferences sharedPreferences;

    private int score, total;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultMain = findViewById(R.id.resultMain);
        txtResultTitle = findViewById(R.id.txtResultTitle);
        txtScore = findViewById(R.id.txtScore);
        btnNewQuiz = findViewById(R.id.btnNewQuiz);
        btnFinish = findViewById(R.id.btnFinish);

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);
        applyTheme(isDarkMode);

        score = getIntent().getIntExtra("score", 0);
        total = getIntent().getIntExtra("total", 0);
        userName = getIntent().getStringExtra("userName");

        if (userName == null || userName.isEmpty()) {
            userName = sharedPreferences.getString("userName", "your name");
        }

        txtResultTitle.setText("Well done, " + userName + "!");
        txtScore.setText("Your score: " + score + "/" + total);

        btnNewQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(Result.this, MainActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
            finish();
        });

        btnFinish.setOnClickListener(v -> {
            finishAffinity();
        });
    }

    private void applyTheme(boolean isDarkMode) {
        if (isDarkMode) {
            resultMain.setBackgroundColor(Color.parseColor("#121212"));

            txtResultTitle.setTextColor(Color.WHITE);
            txtScore.setTextColor(Color.WHITE);

            btnNewQuiz.setTextColor(Color.WHITE);
            btnFinish.setTextColor(Color.WHITE);

            btnNewQuiz.setBackgroundColor(Color.parseColor("#444444"));
            btnFinish.setBackgroundColor(Color.parseColor("#444444"));

        } else {
            resultMain.setBackgroundColor(Color.WHITE);

            txtResultTitle.setTextColor(Color.BLACK);
            txtScore.setTextColor(Color.BLACK);

            btnNewQuiz.setTextColor(Color.BLACK);
            btnFinish.setTextColor(Color.BLACK);

            btnNewQuiz.setBackgroundColor(Color.parseColor("#E0E0E0"));
            btnFinish.setBackgroundColor(Color.parseColor("#E0E0E0"));
        }
    }
}