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
        questions.add(new Question("Sentence Completion:\n'The negotiation process requires patience, flexibility, and __________ communication skills.'", createChoices("A) articulate", "B) ambiguous", "C) verbose", "D) concise"), 0));
        questions.add(new Question("Word Jumble:\nRearrange the letters to form a meaningful word:\n'O C U M M N I A T I O N'\nHint: It's a term related to business dealings.", createChoices("A) Communication", "B) Accumulation", "C) Commutation", "D) Annunciation"), 0));
        questions.add(new Question("Reading Comprehension:\nRead the passage and answer the question:\n'The stock market experienced a sharp decline due to economic uncertainties. What caused the market downturn?'", createChoices("A) Stable economy", "B) Uncertainties", "C) Positive outlook", "D) Government intervention"), 1));
        questions.add(new Question("Sentence Ordering:\nArrange the following words to form a coherent sentence:\n'Strategies - innovative - company - the - implemented - to - stay - competitive.'", createChoices("A) The company implemented innovative strategies to stay competitive.", "B) To stay competitive, the company implemented innovative strategies.", "C) Implemented to stay competitive, the company innovative strategies.", "D) Innovative strategies to stay competitive the company implemented."), 0));
        questions.add(new Question("Error Identification:\nIdentify the error in the following sentence:\n'The team is working hard to ensure their project meets it's deadline.'", createChoices("A) their", "B) meets", "C) it's", "D) working"), 2));
        questions.add(new Question("Fill in the blank:\n'The marketing campaign was a huge __________, resulting in a significant increase in sales.'", createChoices("A) triumph", "B) failure", "C) setback", "D) disappointment"), 0));
        questions.add(new Question("Antonym:\nSelect the word that is the opposite of 'concur':", createChoices("A) agree", "B) dissent", "C) approve", "D) comply"), 1));
        questions.add(new Question("Analogies:\n'Author is to book as sculptor is to __________.'", createChoices("A) marble", "B) paint", "C) canvas", "D) clay"), 3));
        questions.add(new Question("Sentence Correction:\nWhich of the following sentences is grammatically correct?", createChoices("A) The team has been working diligently to achieve their goals.", "B) Him and his colleagues are attending the conference.", "C) Each member of the committee have submitted their reports.", "D) She is more smarter than her peers."), 0));
        questions.add(new Question("Synonyms:\nChoose the word that is most similar in meaning to 'proficient':", createChoices("A) adept", "B) inept", "C) mediocre", "D) amateur"), 0));  
        questions.add(new Question("Fill in the blank:\n'The negotiation reached a __________ when both parties agreed to the terms.'", createChoices("A) stalemate", "B) compromise", "C) impasse", "D) deadlock"), 1));
        questions.add(new Question("Antonym:\nSelect the word that is the opposite of 'obsolete':", createChoices("A) current", "B) modern", "C) outdated", "D) ancient"), 0));
        questions.add(new Question("Analogies:\n'Chef is to kitchen as conductor is to __________.'", createChoices("A) train", "B) symphony", "C) music", "D) orchestra"), 3));
        questions.add(new Question("Sentence Correction:\nWhich of the following sentences is grammatically correct?", createChoices("A) The company's profits have significantly increased last quarter.", "B) Each member of the team have different responsibilities.", "C) Her presentation was more better than his.", "D) The project manager oversees all aspects of the project."), 3));
        questions.add(new Question("Synonyms:\nChoose the word that is most similar in meaning to 'precise':", createChoices("A) accurate", "B) vague", "C) approximate", "D) uncertain"), 0));
        questions.add(new Question("Sentence Completion:\n'The team's ability to adapt to changing circumstances was crucial for __________ success.'", createChoices("A) their", "B) it's", "C) there", "D) its"), 3));
        questions.add(new Question("Word Jumble:\nRearrange the letters to form a meaningful word:\n'I T N E V E S T M N E'\nHint: It's a term related to financial markets.", createChoices("A) Investment", "B) Statement", "C) Sentiment", "D) Entertainment"), 0));
        questions.add(new Question("Reading Comprehension:\nRead the passage and answer the question:\n'The article discusses the benefits of diversifying investment portfolios. Why is diversification important in investing?'", createChoices("A) To focus on a single investment", "B) To reduce risk", "C) To maximize returns", "D) To predict market trends"), 1));
        questions.add(new Question("Sentence Ordering:\nArrange the following words to form a coherent sentence:\n'Strategies - innovative - company - the - implemented - to - stay - competitive.'", createChoices("A) The company implemented innovative strategies to stay competitive.", "B) To stay competitive, the company implemented innovative strategies.", "C) Implemented to stay competitive, the company innovative strategies.", "D) Innovative strategies to stay competitive the company implemented."), 0));
        questions.add(new Question("Error Identification:\nIdentify the error in the following sentence:\n'The team is working hard to ensure their project meets it's deadline.'", createChoices("A) their", "B) meets", "C) it's", "D) working"), 2));
        questions.add(new Question("Fill in the blank:\n'The successful candidate demonstrated exceptional __________ during the interview.'", createChoices("A) prowess", "B) weakness", "C) incompetence", "D) inability"), 0));
        questions.add(new Question("Antonym:\nSelect the word that is the opposite of 'beneficial':", createChoices("A) advantageous", "B) favorable", "C) detrimental", "D) profitable"), 2));
        questions.add(new Question("Analogies:\n'Teacher is to classroom as conductor is to __________.'", createChoices("A) music", "B) orchestra", "C) baton", "D) audience"), 1));
        questions.add(new Question("Sentence Correction:\nWhich of the following sentences is grammatically correct?", createChoices("A) The team have completed their tasks ahead of schedule.", "B) Him and his colleagues are attending the seminar.", "C) She is more talented than any other artist in the gallery.", "D) Each member of the committee has a specific role to play."), 3));
        questions.add(new Question("Synonyms:\nChoose the word that is most similar in meaning to 'proficient':", createChoices("A) adept", "B) inept", "C) mediocre", "D) amateur"), 0));
        questions.add(new Question("Fill in the blank:\n'The team's collaborative effort led to the __________ completion of the project.'", createChoices("A) timely", "B) delayed", "C) hasty", "D) rushed"), 0));
        questions.add(new Question("Antonym:\nSelect the word that is the opposite of 'innovative':", createChoices("A) creative", "B) conventional", "C) original", "D) inventive"), 1));
        questions.add(new Question("Analogies:\n'Painter is to canvas as writer is to __________.'", createChoices("A) pen", "B) paper", "C) novel", "D) ink"), 1));
        questions.add(new Question("Sentence Correction:\nWhich of the following sentences is grammatically correct?", createChoices("A) The team's efforts have resulted in a successful outcome.", "B) She is more cleverer than her colleagues.", "C) Each member of the department are responsible for their tasks.", "D) The project was completed by she and her team."), 0));
        questions.add(new Question("Synonyms:\nChoose the word that is most similar in meaning to 'proficient':", createChoices("A) adept", "B) inept", "C) mediocre", "D) amateur"), 0));
        questions.add(new Question("Sentence Completion:\n'The company's commitment to sustainability is evident in __________ initiatives.'", createChoices("A) their", "B) it's", "C) there", "D) its"), 3));
        questions.add(new Question("Word Jumble:\nRearrange the letters to form a meaningful word:\n'C O R P I T A O N M O M'\nHint: It's a term related to business structures.", createChoices("A) Corporation", "B) Promotion", "C) Comparison", "D) Compartment"), 0));
        questions.add(new Question("Reading Comprehension:\nRead the passage and answer the question:\n'The article highlights the importance of ethical leadership in business. Why is ethical leadership crucial for organizational success?'", createChoices("A) To maximize profits", "B) To maintain employee morale", "C) To build trust and integrity", "D) To avoid legal issues"), 2));
        questions.add(new Question("Sentence Ordering:\nArrange the following words to form a coherent sentence:\n'Innovation - fosters - company - success - and - drives - growth.'", createChoices("A) Innovation fosters company success and drives growth.", "B) Company success and drives growth in innovation fosters.", "C) Success drives growth in company innovation fosters.", "D) Drives success and innovation fosters company growth."), 0));
        questions.add(new Question("Error Identification:\nIdentify the error in the following sentence:\n'The committee members were each given their specific tasks.'", createChoices("A) committee", "B) each", "C) given", "D) their"), 3));
        questions.add(new Question("Fill in the blank:\n'The company's expansion plans were met with __________ from investors.'", createChoices("A) enthusiasm", "B) apathy", "C) indifference", "D) disinterest"), 0));
        questions.add(new Question("Antonym:\nSelect the word that is the opposite of 'advantageous':", createChoices("A) beneficial", "B) favorable", "C) detrimental", "D) profitable"), 2));
        questions.add(new Question("Analogies:\n'Director is to film as author is to __________.'", createChoices("A) book", "B) manuscript", "C) chapter", "D) page"), 0));
        questions.add(new Question("Sentence Correction:\nWhich of the following sentences is grammatically correct?", createChoices("A) The team has been working diligently to achieve their goals.", "B) Him and his colleagues are attending the conference.", "C) Each member of the team have different responsibilities.", "D) She is more smarter than her peers."), 0));
        questions.add(new Question("Synonyms:\nChoose the word that is most similar in meaning to 'precise':", createChoices("A) accurate", "B) vague", "C) approximate", "D) uncertain"), 0));
        questions.add(new Question("Sentence Completion:\n'The negotiation process requires patience, flexibility, and __________ communication skills.'", createChoices("A) articulate", "B) ambiguous", "C) verbose", "D) concise"), 0));
        questions.add(new Question("Word Jumble:\nRearrange the letters to form a meaningful word:\n'O C U M M N I A T I O N'\nHint: It's a term related to business dealings.", createChoices("A) Communication", "B) Continuation", "C) Accommodation", "D) Commotion"), 0));
        questions.add(new Question("Reading Comprehension:\nRead the passage and answer the question:\n'The stock market experienced a sharp decline due to economic uncertainties. What caused the market downturn?'", createChoices("A) Stable economy", "B) Uncertainties", "C) Positive outlook", "D) Government intervention"), 1));
        questions.add(new Question("Sentence Ordering:\nArrange the following words to form a coherent sentence:\n'Strategies - innovative - company - the - implemented - to - stay - competitive.'", createChoices("A) The company implemented innovative strategies to stay competitive.", "B) To stay competitive, the company implemented innovative strategies.", "C) Implemented to stay competitive, the company innovative strategies.", "D) Innovative strategies to stay competitive the company implemented."), 0));
        questions.add(new Question("Error Identification:\nIdentify the error in the following sentence:\n'The team is working hard to ensure their project meets it's deadline.'", createChoices("A) their", "B) meets", "C) it's", "D) working"), 2));
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
