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

public class ClericalQuestionnaireActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

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
        questions.add(new Question("Which of these is NOT a primary color?", createChoices("a) Red", "b) Yellow", "c) Green", "d) Blue"), 2));
        questions.add(new Question("What is the capital of Canada?", createChoices("a) Toronto", "b) Vancouver", "c) Ottawa", "d) Montreal"), 2));
        questions.add(new Question("Who wrote the famous novel \"To Kill a Mockingbird\"?", createChoices("a) Harper Lee", "b) J.D. Salinger", "c) Ernest Hemingway", "d) F. Scott Fitzgerald"), 0));
        questions.add(new Question("What is the chemical symbol for silver?", createChoices("a) Ag", "b) Au", "c) Hg", "d) Sn"), 0));
        questions.add(new Question("Which of these is NOT a type of cloud?", createChoices("a) Cumulus", "b) Stratus", "c) Nimbus", "d) Nebula"), 3));
        questions.add(new Question("What is the name of the largest ocean on Earth?", createChoices("a) Atlantic Ocean", "b) Indian Ocean", "c) Arctic Ocean", "d) Pacific Ocean"), 3));
        questions.add(new Question("Who painted the famous artwork \"Starry Night\"?", createChoices("a) Vincent van Gogh", "b) Leonardo da Vinci", "c) Claude Monet", "d) Pablo Picasso"), 0));
        questions.add(new Question("What is the name of the first person to walk on the moon?", createChoices("a) Yuri Gagarin", "b) Buzz Aldrin", "c) Neil Armstrong", "d) Alan Shepard"), 2));
        questions.add(new Question("Which of these is NOT a continent?", createChoices("a) Asia", "b) Antarctica", "c) Greenland", "d) Australia"), 2));
        questions.add(new Question("What is the name of the famous scientist who developed the theory of relativity?", createChoices("a) Isaac Newton", "b) Albert Einstein", "c) Marie Curie", "d) Charles Darwin"), 1));
        questions.add(new Question("What is the name of the longest river in the world?", createChoices("a) Amazon River", "b) Yangtze River", "c) Nile River", "d) Mississippi River"), 2));
        questions.add(new Question("What is the name of the smallest country in the world?", createChoices("a) Monaco", "b) Vatican City", "c) Nauru", "d) Tuvalu"), 1));
        questions.add(new Question("What is the name of the first woman to fly solo across the Atlantic Ocean?", createChoices("a) Amelia Earhart", "b) Bessie Coleman", "c) Harriet Quimby", "d) Jacqueline Cochran"), 0));
        questions.add(new Question("What is the name of the company that developed the first personal computer?", createChoices("a) IBM", "b) Microsoft", "c) Apple Inc.", "d) Hewlett-Packard"), 2));
        questions.add(new Question("What is the name of the first animal in space?", createChoices("a) Laika, a Soviet dog", "b) Ham, a chimpanzee", "c) Albert II, a monkey", "d) Felix Baumgartner, a skydiver"), 0));
        questions.add(new Question("What is the name of the inventor of the telephone?", createChoices("a) Thomas Edison", "b) Alexander Graham Bell", "c) Nikola Tesla", "d) Guglielmo Marconi"), 1));
        questions.add(new Question("What is the name of the author of the Harry Potter series?", createChoices("a) J.R.R. Tolkien", "b) Stephen King", "c) J.K. Rowling", "d) Suzanne Collins"), 2));
        questions.add(new Question("What is the name of the largest freshwater lake in the world?", createChoices("a) Lake Baikal", "b) Lake Superior", "c) Lake Victoria", "d) Lake Huron"), 1));
        questions.add(new Question("What is the name of the first human to orbit the Earth?", createChoices("a) Neil Armstrong", "b) Yuri Gagarin", "c) Alan Shepard", "d) John Glenn"), 1));
        questions.add(new Question("What is the name of the most populous country in the world?", createChoices("a) India", "b) United States", "c) China", "d) Indonesia"), 2));
        questions.add(new Question("What is the name of the highest waterfall in the world?", createChoices("a) Niagara Falls", "b) Victoria Falls", "c) Angel Falls", "d) Iguazu Falls"), 2));
        questions.add(new Question("What is the name of the most popular social media platform in the world?", createChoices("a) Twitter", "b) Instagram", "c) Facebook", "d) YouTube"), 2));
        questions.add(new Question("What is the name of the longest mountain range in the world?", createChoices("a) Himalayas", "b) Andes Mountains", "c) Rocky Mountains", "d) Alps"), 1));
        questions.add(new Question("What is the name of the hottest continent in the world?", createChoices("a) Asia", "b) Africa", "c) Australia", "d) South America"), 1));
        questions.add(new Question("What is the name of the smallest continent in the world?", createChoices("a) Australia", "b) Europe", "c) Antarctica", "d) South America"), 0));
     
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
