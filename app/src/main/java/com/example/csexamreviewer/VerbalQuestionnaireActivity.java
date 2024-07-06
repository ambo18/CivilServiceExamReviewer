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

public class VerbalQuestionnaireActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

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
        questionTextViews.add(findViewById(R.id.questionTextView1));
        questionTextViews.add(findViewById(R.id.questionTextView2));
        questionTextViews.add(findViewById(R.id.questionTextView3));
        questionTextViews.add(findViewById(R.id.questionTextView4));
        questionTextViews.add(findViewById(R.id.questionTextView5));
        questionTextViews.add(findViewById(R.id.questionTextView6));
        questionTextViews.add(findViewById(R.id.questionTextView7));
        questionTextViews.add(findViewById(R.id.questionTextView8));
        questionTextViews.add(findViewById(R.id.questionTextView9));
        questionTextViews.add(findViewById(R.id.questionTextView10));
        questionTextViews.add(findViewById(R.id.questionTextView11));
        questionTextViews.add(findViewById(R.id.questionTextView12));
        questionTextViews.add(findViewById(R.id.questionTextView13));
        questionTextViews.add(findViewById(R.id.questionTextView14));
        questionTextViews.add(findViewById(R.id.questionTextView15));
        questionTextViews.add(findViewById(R.id.questionTextView16));
        questionTextViews.add(findViewById(R.id.questionTextView17));
        questionTextViews.add(findViewById(R.id.questionTextView18));
        questionTextViews.add(findViewById(R.id.questionTextView19));
        questionTextViews.add(findViewById(R.id.questionTextView20));
        questionTextViews.add(findViewById(R.id.questionTextView21));
        questionTextViews.add(findViewById(R.id.questionTextView22));
        questionTextViews.add(findViewById(R.id.questionTextView23));
        questionTextViews.add(findViewById(R.id.questionTextView24));
        questionTextViews.add(findViewById(R.id.questionTextView25));
        questionTextViews.add(findViewById(R.id.questionTextView26));
        questionTextViews.add(findViewById(R.id.questionTextView27));
        questionTextViews.add(findViewById(R.id.questionTextView28));
        questionTextViews.add(findViewById(R.id.questionTextView29));
        questionTextViews.add(findViewById(R.id.questionTextView30));

        choiceRadioGroups = new ArrayList<>();
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup1));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup2));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup3));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup4));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup5));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup6));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup7));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup8));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup9));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup10));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup11));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup12));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup13));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup14));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup15));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup16));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup17));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup18));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup19));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup20));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup21));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup22));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup23));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup24));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup25));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup26));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup27));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup28));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup29));
        choiceRadioGroups.add(findViewById(R.id.choiceRadioGroup30));

        textToSpeechButtons = new ArrayList<>();
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton1));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton2));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton3));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton4));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton5));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton6));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton7));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton8));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton9));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton10));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton11));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton12));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton13));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton14));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton15));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton16));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton17));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton18));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton19));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton20));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton21));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton22));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton23));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton24));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton25));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton26));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton27));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton28));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton29));
        textToSpeechButtons.add(findViewById(R.id.textToSpeechButton30));

        submitButton = findViewById(R.id.submitButton);
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("Question 1?", createChoices("A", "B", "C", "D"), 1));  
        questions.add(new Question("Question 2?", createChoices("A", "B", "C", "D"), 1));  
        questions.add(new Question("Question 3", createChoices("A", "B", "C", "D"), 0));   
        questions.add(new Question("Question 4", createChoices("A", "B", "C", "D"), 2));   
        questions.add(new Question("Question 5", createChoices("A", "B", "C", "D"), 1));   
        questions.add(new Question("Question 6", createChoices("A", "B", "C", "D"), 1));   
        questions.add(new Question("Question 7", createChoices("A", "B", "C", "D"), 0));   
        questions.add(new Question("Question 8", createChoices("A", "B", "C", "D"), 3));   
        questions.add(new Question("Question 9", createChoices("A", "B", "C", "D"), 0));   
        questions.add(new Question("Question 10", createChoices("A", "B", "C", "D"), 1));  
        questions.add(new Question("Question 11", createChoices("A", "B", "C", "D"), 2));  
        questions.add(new Question("Question 12", createChoices("A", "B", "C", "D"), 2));  
        questions.add(new Question("Question 13", createChoices("A", "B", "C", "D"), 2));  
        questions.add(new Question("Question 14", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 15", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 16", createChoices("A", "B", "C", "D"), 1));  
        questions.add(new Question("Question 17", createChoices("A", "B", "C", "D"), 1));  
        questions.add(new Question("Question 18", createChoices("A", "B", "C", "D"), 1));  
        questions.add(new Question("Question 19", createChoices("A", "B", "C", "D"), 1));  
        questions.add(new Question("Question 20", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 21", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 22", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 23", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 24", createChoices("A", "B", "C", "D"), 2));  
        questions.add(new Question("Question 25", createChoices("A", "B", "C", "D"), 3));  
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
    
        for (int i = 0; i < questions.size(); i++) {
            if (checkAnswer(i)) {
                correctAnswers++;
                correctAnswersText.append(i + 1).append(". Correct\n");
            } else {
                correctAnswersText.append(i + 1).append(". (Correct Answer: ")
                        .append(questions.get(i).getChoices().get(questions.get(i).getCorrectAnswerIndex())).append(")\n");
            }
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
