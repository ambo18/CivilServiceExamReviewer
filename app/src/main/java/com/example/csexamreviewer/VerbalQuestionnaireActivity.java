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
        questions.add(new Question("Fill in the blank:\n'The CEO's speech was __________ with inspirational quotes and strategic insights.'", createChoices("A) replete", "B) devoid", "C) sparse", "D) vacant"), 0));
        questions.add(new Question("Antonym:\nSelect the word that is the opposite of 'diligent':", createChoices("A) lazy", "B) industrious", "C) hardworking", "D) dedicated"), 0));
        questions.add(new Question("Analogies:\n'Editor is to manuscript as conductor is to __________.'", createChoices("A) orchestra", "B) music", "C) baton", "D) audience"), 0));
        questions.add(new Question("Sentence Correction:\nWhich of the following sentences is grammatically correct?", createChoices("A) The project was completed by she and her team.", "B) He is the most smarter person in the group.", "C) The report needs to be reviewed by him and I.", "D) She is an expert in financial analysis."), 3));
        questions.add(new Question("Synonyms:\nChoose the word that is most similar in meaning to 'proficient':", createChoices("A) adequate", "B) skilled", "C) amateur", "D) mediocre"), 1));
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
        questions.add(new Question("Question 26", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 27", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 28", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 29", createChoices("A", "B", "C", "D"), 2));  
        questions.add(new Question("Question 30", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 31", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 32", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 33", createChoices("A", "B", "C", "D"), 0));  
        questions.add(new Question("Question 34", createChoices("A", "B", "C", "D"), 2));  
        questions.add(new Question("Question 35", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 36", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 37", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 38", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 39", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 40", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 41", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 42", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 43", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 44", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 45", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 46", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 47", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 48", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 49", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 50", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 51", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 52", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 53", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 54", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 55", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 56", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 57", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 58", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 59", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 60", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 61", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 62", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 63", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 64", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 65", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 66", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 67", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 68", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 69", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 70", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 71", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 72", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 73", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 74", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 75", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 76", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 77", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 78", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 79", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 80", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 81", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 82", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 83", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 84", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 85", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 86", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 87", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 88", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 89", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 90", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 91", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 92", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 93", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 94", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 95", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 96", createChoices("A", "B", "C", "D"), 1));
        questions.add(new Question("Question 97", createChoices("A", "B", "C", "D"), 2));
        questions.add(new Question("Question 98", createChoices("A", "B", "C", "D"), 0));
        questions.add(new Question("Question 99", createChoices("A", "B", "C", "D"), 3));
        questions.add(new Question("Question 100", createChoices("A", "B", "C", "D"), 1));    
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
