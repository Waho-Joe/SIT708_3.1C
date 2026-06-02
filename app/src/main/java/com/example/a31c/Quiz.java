package com.example.a31c;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz extends AppCompatActivity {

    TextView txtWelcome, txtProgressNumber, txtQuestionTitle, txtQuestion;
    ProgressBar progressBar;
    Button btnAnswer1, btnAnswer2, btnAnswer3,btnSubmit;

    int currentQuestionIndex = 0;
    int selectedAnswerIndex = -1;
    int score = 0;

    boolean submitted = false;

    String userName;

    String[] questions = {
            "What is 1 + 1?",
            "What is 1 + 2?",
            "What is 1 + 3?"
    };

    String[][] answers = {
            {"1", "2", "3"},
            {"2", "3", "4"},
            {"3", "4", "5"}
    };

    int[] correctAnswers = {
            1,
            1,
            1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtWelcome = findViewById(R.id.txtWelcome);
        txtProgressNumber = findViewById(R.id.txtProgressNumber);
        txtQuestionTitle = findViewById(R.id.txtQuestionTitle);
        txtQuestion = findViewById(R.id.txtQuestion);

        progressBar = findViewById(R.id.progressBar);

        btnAnswer1 = findViewById(R.id.btnAnswer1);
        btnAnswer2 = findViewById(R.id.btnAnswer2);
        btnAnswer3 = findViewById(R.id.btnAnswer3);

        btnSubmit = findViewById(R.id.btnSubmit);

        btnAnswer1.setOnClickListener(v -> selectAnswer(0));
        btnAnswer2.setOnClickListener(v -> selectAnswer(1));
        btnAnswer3.setOnClickListener(v -> selectAnswer(2));

        btnSubmit.setOnClickListener(v -> {
            if (!submitted) {
                submitAnswer();
            } else {
                nextQuestion();
            }
        });

        userName = getIntent().getStringExtra("userName");

        if (userName == null || userName.isEmpty()) {
            userName = "your name";
        }

        loadQuestion();
    }

    private void loadQuestion() {
        submitted = false;
        selectedAnswerIndex = -1;

        txtWelcome.setText("Welcome " + userName + "!");
        txtQuestionTitle.setText("Math question title:");
        txtQuestion.setText(questions[currentQuestionIndex]);

        btnAnswer1.setText(answers[currentQuestionIndex][0]);
        btnAnswer2.setText(answers[currentQuestionIndex][1]);
        btnAnswer3.setText(answers[currentQuestionIndex][2]);

        txtProgressNumber.setText((currentQuestionIndex + 1) + "/" + questions.length);

        int progress = (int) (((double) currentQuestionIndex / questions.length) * 100);
        progressBar.setProgress(progress);

        resetButtonColors();

        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);

        btnSubmit.setText("Submit");
    }

    private void selectAnswer(int index) {
        if (submitted) {
            return;
        }

        selectedAnswerIndex = index;

        resetButtonColors();

        Button selectedButton = getAnswerButton(index);
        selectedButton.setBackgroundColor(Color.GRAY);
    }

    private void submitAnswer() {
        if (selectedAnswerIndex == -1) {
            return;
        }

        submitted = true;

        int correctIndex = correctAnswers[currentQuestionIndex];

        Button correctButton = getAnswerButton(correctIndex);
        correctButton.setBackgroundColor(Color.rgb(160, 230, 120));

        if (selectedAnswerIndex == correctIndex) {
            score++;
        } else {
            Button wrongButton = getAnswerButton(selectedAnswerIndex);
            wrongButton.setBackgroundColor(Color.rgb(235, 100, 100));
        }

        btnAnswer1.setEnabled(false);
        btnAnswer2.setEnabled(false);
        btnAnswer3.setEnabled(false);

        int progress = (int) (((double) (currentQuestionIndex + 1) / questions.length) * 100);
        progressBar.setProgress(progress);

        btnSubmit.setText("Next");
    }

    private void nextQuestion() {
        currentQuestionIndex++;

        if (currentQuestionIndex < questions.length) {
            loadQuestion();
        } else {
            Intent intent = new Intent(Quiz.this, Result.class);
            intent.putExtra("score", score);
            intent.putExtra("total", questions.length);
            intent.putExtra("userName",userName);
            startActivity(intent);
            finish();
        }
    }

    private Button getAnswerButton(int index) {
        if (index == 0) {
            return btnAnswer1;
        } else if (index == 1) {
            return btnAnswer2;
        } else {
            return btnAnswer3;
        }
    }
    private void resetButtonColors() {
        btnAnswer1.setBackgroundColor(Color.LTGRAY);
        btnAnswer2.setBackgroundColor(Color.LTGRAY);
        btnAnswer3.setBackgroundColor(Color.LTGRAY);

        btnAnswer1.setTextColor(Color.BLACK);
        btnAnswer2.setTextColor(Color.BLACK);
        btnAnswer3.setTextColor(Color.BLACK);
    }
}