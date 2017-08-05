package com.example.android.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.android.quizapp.R.id.answer01;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private final static int NUM_QUESTIONS = 5;
    private double score;
    private String feedback = "";

    // The mother function that handles taking the user's answer and outputting a score
    public void onSubmit(View view) {
        // Regex through the short answer answer to see if it contains "big o" case-insensitive:
        String question01text = ((EditText) findViewById(answer01)).getText().toString();
        Pattern regex = Pattern.compile(getString(R.string.regex_big_o));
        Matcher m = regex.matcher(question01text);

        // Assign the results of the regex to answer01:
        final Boolean ANSWER01 = m.find();

        // Make sure both correct answer02s are checked and that no incorrect answers are checked:
        final Boolean ANSWER02 = ((CheckBox) findViewById(R.id.checkbox_n)).isChecked()
                && ((CheckBox) findViewById(R.id.checkbox_2n)).isChecked()
                && !((CheckBox) findViewById(R.id.checkbox_n_log_n)).isChecked()
                && !((CheckBox) findViewById(R.id.checkbox_n_squared)).isChecked();

        // Alternatively, make sure one correct answer02 is checked and that no incorrect answers are checked:
        final Boolean ANSWER02HALF = (((CheckBox) findViewById(R.id.checkbox_n)).isChecked()
                || ((CheckBox) findViewById(R.id.checkbox_2n)).isChecked())
                && !((CheckBox) findViewById(R.id.checkbox_n_log_n)).isChecked()
                && !((CheckBox) findViewById(R.id.checkbox_n_squared)).isChecked();

        // Make sure the right radio button is selected for the remaining three answers:
        final Boolean ANSWER03 = ((RadioButton) findViewById(R.id.radio_counting_sort)).isChecked();
        final Boolean ANSWER04 = ((RadioButton) findViewById(R.id.radio_big_o)).isChecked();
        final Boolean ANSWER05 = ((RadioButton) findViewById(R.id.radio_hash_table)).isChecked();

        calculateScore(ANSWER01, ANSWER02, ANSWER02HALF, ANSWER03, ANSWER04, ANSWER05);
        showFinalScore();
        resetQuiz();
    }

    // Toast the final qualitative & quantitative score
    private void showFinalScore() {
        Toast.makeText(this, feedback, Toast.LENGTH_LONG).show();
    }

    // Reset the score and feedback but leave the answer fields alone so the user can check later
    private void resetQuiz() {
        score = 0;
        feedback = "";
    }

    // Calculates the score based on the user's answers
    private void calculateScore(boolean ANSWER01,
                                boolean ANSWER02,
                                boolean ANSWER02HALF,
                                boolean ANSWER03,
                                boolean ANSWER04,
                                boolean ANSWER05) {
        if (ANSWER01) {
            ++score;
            feedback += getString(R.string.answer01_correct);
        } else {
            feedback += getString(R.string.answer01_incorrect);
        }
        feedback += getString(R.string.double_line_break);

        if (ANSWER02) {
            ++score;
            feedback += getString(R.string.answer02_correct);
        } else if (ANSWER02HALF) {
            score += 0.5;
            feedback += getString(R.string.answer02_partial);
        } else {
            feedback += getString(R.string.answer02_incorrect);
        }
        feedback += getString(R.string.double_line_break);

        if (ANSWER03) {
            ++score;
            feedback += getString(R.string.answer03_correct);
        } else {
            feedback += getString(R.string.answer03_incorrect);
        }
        feedback += getString(R.string.double_line_break);

        if (ANSWER04) {
            ++score;
            feedback += getString(R.string.answer04_correct);
        } else {
            feedback += getString(R.string.answer04_incorrect);
        }
        feedback += getString(R.string.double_line_break);

        if (ANSWER05) {
            ++score;
            feedback += getString(R.string.answer05_correct);
        } else {
            feedback += getString(R.string.answer05_incorrect);
        }
        feedback += getString(R.string.double_line_break);

        // Calculate the score percentage
        score = 100 * (score / NUM_QUESTIONS);
        feedback += getString(R.string.results_final_score) + score + getString(R.string.results_percent_symbol);
    }
}
