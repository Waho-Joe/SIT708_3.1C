package com.example.a31c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Result extends AppCompatActivity {

    TextView txtResultTitle, txtScore;
    Button btnNewQuiz, btnFinish;
    int score, total;
    String userName;

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

        txtResultTitle = findViewById(R.id.txtResultTitle);
        txtScore = findViewById(R.id.txtScore);
        btnNewQuiz = findViewById(R.id.btnNewQuiz);
        btnFinish = findViewById(R.id.btnFinish);

        score = getIntent().getIntExtra("score", 0);
        total = getIntent().getIntExtra("total", 0);
        userName = getIntent().getStringExtra("userName");

        if (userName == null || userName.isEmpty()) {
            userName = "your name";
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
}