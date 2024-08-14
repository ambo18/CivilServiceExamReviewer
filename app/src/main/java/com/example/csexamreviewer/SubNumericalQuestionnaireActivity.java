package com.example.csexamreviewer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SubNumericalQuestionnaireActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    // Views
    private List<TextView> questionTextViews;
    private List<RadioGroup> choiceRadioGroups;
    private List<ImageView> textToSpeechButtons;
    private Button submitButton;

    // Questions
    private List<Question> questions;
    private int currentQuestionIndex = 0;

    // Text-to-speech
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbal);

        // Initialize views
        initializeViews();

        // Initialize questions
        initializeQuestions();

        // Initialize text-to-speech engine
        textToSpeech = new TextToSpeech(this, this);

        // Set onClick listeners
        setOnClickListeners();

        // Shuffle questions initially
        shuffleQuestions();
    }

    private void initializeViews() {
        questionTextViews = new ArrayList<>();
        choiceRadioGroups = new ArrayList<>();
        textToSpeechButtons = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            int resID = getResources().getIdentifier("questionTextView" + i, "id", getPackageName());
            questionTextViews.add(findViewById(resID));

            int radioGroupResID = getResources().getIdentifier("choiceRadioGroup" + i, "id", getPackageName());
            choiceRadioGroups.add(findViewById(radioGroupResID));

            int textToSpeechButtonResID = getResources().getIdentifier("textToSpeechButton" + i, "id", getPackageName());
            textToSpeechButtons.add(findViewById(textToSpeechButtonResID));
        }

        submitButton = findViewById(R.id.submitButton);
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is 25% of 200?", createChoices("A) 25", "B) 50", "C) 75", "D) 100"), 1));
        questions.add(new Question("Solve for x: 2x + 3 = 7.", createChoices("A) 1", "B) 2", "C) 3", "D) 4"), 1));
        questions.add(new Question("What is the square root of 144?", createChoices("A) 10", "B) 11", "C) 12", "D) 13"), 2));
        questions.add(new Question("Calculate the perimeter of a rectangle with length 10 and width 5.", createChoices("A) 25", "B) 30", "C) 35", "D) 40"), 1));
        questions.add(new Question("What is 15 divided by 3?", createChoices("A) 3", "B) 4", "C) 5", "D) 6"), 2));
        questions.add(new Question("Convert 0.75 to a fraction.", createChoices("A) 3/4", "B) 1/2", "C) 1/4", "D) 2/3"), 0));
        questions.add(new Question("If x = 5, what is 3x + 2?", createChoices("A) 12", "B) 15", "C) 17", "D) 20"), 2));
        questions.add(new Question("What is 7 squared?", createChoices("A) 14", "B) 21", "C) 49", "D) 77"), 2));
        questions.add(new Question("Simplify: 5 + 3 * 2.", createChoices("A) 11", "B) 16", "C) 21", "D) 24"), 0));
        questions.add(new Question("What is the result of 18 - 9/3?", createChoices("A) 3", "B) 15", "C) 17", "D) 21"), 1));
        questions.add(new Question("Convert 45% to a decimal.", createChoices("A) 0.045", "B) 0.45", "C) 4.5", "D) 45"), 1));
        questions.add(new Question("Find the greatest common divisor (GCD) of 24 and 36.", createChoices("A) 4", "B) 6", "C) 8", "D) 12"), 3));
        questions.add(new Question("What is the next number in the sequence: 2, 4, 8, 16, ...?", createChoices("A) 24", "B) 32", "C) 48", "D) 64"), 1));
        questions.add(new Question("What is the product of 6 and 7?", createChoices("A) 30", "B) 42", "C) 48", "D) 54"), 1));
        questions.add(new Question("Solve for y: 3y - 9 = 0.", createChoices("A) 0", "B) 1", "C) 2", "D) 3"), 3));
        questions.add(new Question("What is the area of a triangle with a base of 10 and a height of 5?", createChoices("A) 15", "B) 25", "C) 30", "D) 50"), 1));
        questions.add(new Question("If 5x = 20, what is x?", createChoices("A) 2", "B) 3", "C) 4", "D) 5"), 2));
        questions.add(new Question("Which of the following is a prime number?", createChoices("A) 4", "B) 6", "C) 9", "D) 11"), 3));
        questions.add(new Question("What is 10% of 500?", createChoices("A) 25", "B) 50", "C) 75", "D) 100"), 1));
        questions.add(new Question("Find the least common multiple (LCM) of 3 and 4.", createChoices("A) 6", "B) 9", "C) 12", "D) 15"), 2));
        questions.add(new Question("What is the value of π (pi) to two decimal places?", createChoices("A) 3.12", "B) 3.14", "C) 3.16", "D) 3.18"), 1));
        questions.add(new Question("What is the sum of 45 and 55?", createChoices("A) 90", "B) 95", "C) 100", "D) 105"), 2));
        questions.add(new Question("What is the result of 5^3?", createChoices("A) 15", "B) 25", "C) 125", "D) 225"), 2));
        questions.add(new Question("If 3x = 9, what is x?", createChoices("A) 1", "B) 2", "C) 3", "D) 4"), 2));
        questions.add(new Question("Convert 1/4 to a decimal.", createChoices("A) 0.14", "B) 0.24", "C) 0.34", "D) 0.25"), 3));
        questions.add(new Question("What is 12% of 150?", createChoices("A) 12", "B) 15", "C) 18", "D) 24"), 2));
        questions.add(new Question("If y = 7, what is 4y - 5?", createChoices("A) 23", "B) 24", "C) 25", "D) 26"), 0));
        questions.add(new Question("Simplify: 8 * (2 + 3).", createChoices("A) 24", "B) 32", "C) 40", "D) 48"), 2));
        questions.add(new Question("What is the cube root of 27?", createChoices("A) 2", "B) 3", "C) 4", "D) 5"), 1));
        questions.add(new Question("What is the result of 10^2?", createChoices("A) 20", "B) 50", "C) 100", "D) 200"), 2));
        questions.add(new Question("If x = 4, what is x^2 - 2x?", createChoices("A) 4", "B) 8", "C) 12", "D) 16"), 1));
        questions.add(new Question("What is the result of 2 + 3 * 4?", createChoices("A) 14", "B) 20", "C) 24", "D) 32"), 0));
        questions.add(new Question("What is the percentage increase from 50 to 75?", createChoices("A) 25%", "B) 50%", "C) 75%", "D) 100%"), 1));
        questions.add(new Question("What is 1/2 + 1/3?", createChoices("A) 1/5", "B) 2/5", "C) 5/6", "D) 1"), 2));
        questions.add(new Question("Solve for z: 5z = 45.", createChoices("A) 7", "B) 8", "C) 9", "D) 10"), 2));
        questions.add(new Question("Convert 0.6 to a fraction.", createChoices("A) 1/5", "B) 1/3", "C) 2/3", "D) 3/5"), 3));
        questions.add(new Question("What is the difference between 150 and 85?", createChoices("A) 55", "B) 65", "C) 75", "D) 85"), 1));
        questions.add(new Question("What is the result of 4^2?", createChoices("A) 8", "B) 12", "C) 16", "D) 20"), 2));
        questions.add(new Question("If y = 10, what is 3y + 2?", createChoices("A) 30", "B) 32", "C) 35", "D) 40"), 1));
        questions.add(new Question("What is 60% of 90?", createChoices("A) 36", "B) 45", "C) 54", "D) 60"), 2));
        questions.add(new Question("Find the average of 4, 8, and 12.", createChoices("A) 6", "B) 8", "C) 10", "D) 12"), 1));
        questions.add(new Question("What is 3/5 of 100?", createChoices("A) 30", "B) 50", "C) 60", "D) 70"), 2));
        questions.add(new Question("Solve for x: x - 5 = 15.", createChoices("A) 10", "B) 15", "C) 20", "D) 25"), 2));
        questions.add(new Question("What is the least common multiple (LCM) of 6 and 8?", createChoices("A) 12", "B) 18", "C) 24", "D) 48"), 2));
        questions.add(new Question("If x = 3, what is x^3 - 2x?", createChoices("A) 15", "B) 18", "C) 21", "D) 24"), 0));
        questions.add(new Question("What is 3/4 of 80?", createChoices("A) 40", "B) 50", "C) 60", "D) 70"), 2));
        questions.add(new Question("Find the mode of the following numbers: 2, 3, 3, 4, 5.", createChoices("A) 2", "B) 3", "C) 4", "D) 5"), 1));
        questions.add(new Question("What is 7% of 200?", createChoices("A) 7", "B) 10", "C) 14", "D) 20"), 2));
        questions.add(new Question("If y = 8, what is 2y + 3?", createChoices("A) 16", "B) 17", "C) 18", "D) 19"), 3));
        questions.add(new Question("What is the square root of 81?", createChoices("A) 7", "B) 8", "C) 9", "D) 10"), 2));
        questions.add(new Question("Convert 1/5 to a decimal.", createChoices("A) 0.1", "B) 0.2", "C) 0.3", "D) 0.4"), 1));
        questions.add(new Question("What is 5% of 300?", createChoices("A) 10", "B) 15", "C) 20", "D) 30"), 3));
        questions.add(new Question("Solve for z: 4z = 36.", createChoices("A) 6", "B) 7", "C) 8", "D) 9"), 3));
        questions.add(new Question("What is the greatest common divisor (GCD) of 30 and 45?", createChoices("A) 5", "B) 10", "C) 15", "D) 20"), 2));
        questions.add(new Question("Find the median of the following numbers: 2, 4, 6, 8, 10.", createChoices("A) 4", "B) 6", "C) 8", "D) 10"), 1));
        questions.add(new Question("What is 8% of 50?", createChoices("A) 2", "B) 4", "C) 6", "D) 8"), 1));
        questions.add(new Question("If x = 7, what is 5x - 3?", createChoices("A) 32", "B) 33", "C) 34", "D) 35"), 2));
        questions.add(new Question("Simplify: 6 * (2 + 3).", createChoices("A) 20", "B) 25", "C) 30", "D) 35"), 2));
        questions.add(new Question("What is the cube root of 64?", createChoices("A) 2", "B) 3", "C) 4", "D) 5"), 2));
        questions.add(new Question("What is the result of 3^3?", createChoices("A) 9", "B) 18", "C) 27", "D) 36"), 2));
        questions.add(new Question("If y = 6, what is y^2 - 2y?", createChoices("A) 18", "B) 24", "C) 30", "D) 36"), 0));
        questions.add(new Question("What is the result of 4 + 3 * 2?", createChoices("A) 10", "B) 11", "C) 12", "D) 14"), 1));
        questions.add(new Question("What is the percentage decrease from 80 to 60?", createChoices("A) 10%", "B) 20%", "C) 25%", "D) 50%"), 2));
        questions.add(new Question("What is 2/3 + 1/4?", createChoices("A) 5/12", "B) 9/12", "C) 11/12", "D) 12/12"), 2));
        questions.add(new Question("Solve for z: 6z = 54.", createChoices("A) 7", "B) 8", "C) 9", "D) 10"), 2));
        questions.add(new Question("Convert 0.8 to a fraction.", createChoices("A) 1/2", "B) 2/5", "C) 3/5", "D) 4/5"), 3));
        questions.add(new Question("What is the difference between 120 and 75?", createChoices("A) 35", "B) 45", "C) 55", "D) 65"), 1));
        questions.add(new Question("What is the result of 5^2?", createChoices("A) 10", "B) 15", "C) 20", "D) 25"), 3));
        questions.add(new Question("If y = 12, what is 3y + 4?", createChoices("A) 32", "B) 34", "C) 36", "D) 38"), 1));
        questions.add(new Question("What is 75% of 80?", createChoices("A) 45", "B) 55", "C) 60", "D) 65"), 2));
        questions.add(new Question("Find the average of 5, 10, and 15.", createChoices("A) 10", "B) 12", "C) 15", "D) 20"), 0));
        questions.add(new Question("What is 2/3 of 120?", createChoices("A) 60", "B) 70", "C) 80", "D) 90"), 3));
        questions.add(new Question("Solve for x: x + 7 = 22.", createChoices("A) 12", "B) 13", "C) 14", "D) 15"), 2));
        questions.add(new Question("What is the least common multiple (LCM) of 9 and 12?", createChoices("A) 18", "B) 27", "C) 36", "D) 45"), 2));
        questions.add(new Question("If x = 5, what is x^2 - 3x?", createChoices("A) 10", "B) 15", "C) 20", "D) 25"), 0));
        questions.add(new Question("What is 2/5 of 100?", createChoices("A) 30", "B) 40", "C) 50", "D) 60"), 1));
        questions.add(new Question("Find the mode of the following numbers: 4, 5, 6, 6, 7.", createChoices("A) 4", "B) 5", "C) 6", "D) 7"), 2));
        questions.add(new Question("What is 10% of 150?", createChoices("A) 10", "B) 15", "C) 20", "D) 25"), 1));
        questions.add(new Question("If x = 4, what is 6x - 2?", createChoices("A) 22", "B) 23", "C) 24", "D) 25"), 0));
        questions.add(new Question("What is the square root of 49?", createChoices("A) 6", "B) 7", "C) 8", "D) 9"), 1));
        questions.add(new Question("Convert 3/5 to a decimal.", createChoices("A) 0.2", "B) 0.4", "C) 0.6", "D) 0.8"), 2));
        questions.add(new Question("What is 12% of 200?", createChoices("A) 12", "B) 18", "C) 24", "D) 30"), 2));
        questions.add(new Question("Solve for z: 7z = 63.", createChoices("A) 7", "B) 8", "C) 9", "D) 10"), 2));
        questions.add(new Question("What is the greatest common divisor (GCD) of 40 and 60?", createChoices("A) 10", "B) 15", "C) 20", "D) 30"), 2));
        questions.add(new Question("Find the median of the following numbers: 3, 5, 7, 9, 11.", createChoices("A) 5", "B) 7", "C) 9", "D) 11"), 1));
        questions.add(new Question("What is 15% of 120?", createChoices("A) 15", "B) 18", "C) 24", "D) 36"), 1));
        questions.add(new Question("If x = 9, what is 2x + 3?", createChoices("A) 18", "B) 19", "C) 20", "D) 21"), 3));
        questions.add(new Question("Simplify: 7 * (2 + 3).", createChoices("A) 25", "B) 30", "C) 35", "D) 40"), 2));
        questions.add(new Question("What is the cube root of 125?", createChoices("A) 3", "B) 4", "C) 5", "D) 6"), 2));
        questions.add(new Question("What is the result of 4^3?", createChoices("A) 16", "B) 32", "C) 64", "D) 80"), 2));
        questions.add(new Question("If y = 8, what is y^2 - 4y?", createChoices("A) 24", "B) 32", "C) 40", "D) 48"), 1));
        questions.add(new Question("What is the result of 5 + 4 * 3?", createChoices("A) 17", "B) 19", "C) 21", "D) 23"), 1));
        questions.add(new Question("What is the percentage increase from 70 to 105?", createChoices("A) 35%", "B) 45%", "C) 50%", "D) 75%"), 2));
        questions.add(new Question("What is 4/5 + 2/3?", createChoices("A) 22/15", "B) 24/15", "C) 26/15", "D) 28/15"), 0));
        questions.add(new Question("Solve for z: 8z = 72.", createChoices("A) 7", "B) 8", "C) 9", "D) 10"), 2));
        questions.add(new Question("Convert 0.2 to a fraction.", createChoices("A) 1/2", "B) 1/4", "C) 1/5", "D) 2/5"), 2));
        questions.add(new Question("What is the difference between 200 and 140?", createChoices("A) 50", "B) 60", "C) 70", "D) 80"), 1));
        questions.add(new Question("What is the result of 3^4?", createChoices("A) 27", "B) 64", "C) 81", "D) 100"), 2));
        questions.add(new Question("If y = 5, what is 4y + 6?", createChoices("A) 25", "B) 26", "C) 27", "D) 28"), 3));
        questions.add(new Question("What is 5% of 500?", createChoices("A) 20", "B) 25", "C) 30", "D) 35"), 1));
    }    

    private void setOnClickListeners() {
        for (int i = 0; i < textToSpeechButtons.size(); i++) {
            int finalI = i; // To access in lambda expression
            textToSpeechButtons.get(i).setOnClickListener(view -> speakText(questions.get(finalI).getQuestionText() + ". " + getChoicesText(finalI)));
        }

        submitButton.setOnClickListener(view -> showScoreDialog());
    }

    private void speakText(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private String getChoicesText(int index) {
        StringBuilder choicesText = new StringBuilder();
        List<String> choices = questions.get(index).getChoices();
        for (int i = 0; i < choices.size(); i++) {
            choicesText.append(choices.get(i)).append(". ");
        }
        return choicesText.toString();
    }

    private void showScoreDialog() {
        int correctAnswers = 0;
        StringBuilder correctAnswersText = new StringBuilder();
        boolean allAnswered = true;
        int firstUnansweredIndex = -1;
    
        for (int i = 0; i < questions.size(); i++) {
            if (choiceRadioGroups.get(i).getCheckedRadioButtonId() == -1) {
                allAnswered = false;
                if (firstUnansweredIndex == -1) {
                    firstUnansweredIndex = i;
                }
            } else if (checkAnswer(i)) {
                correctAnswers++;
                correctAnswersText.append(i + 1).append(". Correct\n");
            } else {
                correctAnswersText.append(i + 1).append(". (Correct Answer: ")
                        .append(questions.get(i).getChoices().get(questions.get(i).getCorrectAnswerIndex())).append(")\n");
            }
        }
    
        if (!allAnswered) {
            // Scroll to the first unanswered question
            TextView unansweredQuestionTextView = questionTextViews.get(firstUnansweredIndex);
            unansweredQuestionTextView.getParent().requestChildFocus(unansweredQuestionTextView, unansweredQuestionTextView);
    
            // Alert dialog to inform the user to answer all questions
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please answer all the questions before submitting.")
                    .setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
    
        // Build the dialog message
        String dialogMessage = "Your score is " + correctAnswers + "/" + questions.size() + "\n\n";
        dialogMessage += "Correct Answers:\n" + correctAnswersText.toString();
    
        // Create and show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(dialogMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Finish the current activity to go back
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }            

    private boolean checkAnswer(int index) {
        int selectedId = choiceRadioGroups.get(index).getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        int selectedAnswerIndex = choiceRadioGroups.get(index).indexOfChild(selectedRadioButton);
        int correctAnswerIndex = questions.get(index).getCorrectAnswerIndex();
        return selectedAnswerIndex == correctAnswerIndex;
    }
    

    private void shuffleQuestions() {
        Collections.shuffle(questions);
        currentQuestionIndex = 0; // Reset current question index

        // Update UI with shuffled questions
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            TextView textView = questionTextViews.get(i);
            textView.setText(question.getQuestionText());

            List<String> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                RadioButton radioButton = (RadioButton) choiceRadioGroups.get(i).getChildAt(j);
                radioButton.setText(choices.get(j));
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Text-to-speech initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    private List<String> createChoices(String... choices) {
        List<String> choicesList = new ArrayList<>();
        Collections.addAll(choicesList, choices);
        return choicesList;
    }
}
