package com.example.csexamreviewer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
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
    private Button submitButton, nextButton;

    // Questions
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private final int QUESTIONS_PER_PAGE = 20;
    private final int TOTAL_PAGES = 5;

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

        // Show initial page
        showPage(0);
    }

    private void initializeViews() {
        questionTextViews = new ArrayList<>();
        choiceRadioGroups = new ArrayList<>();
        textToSpeechButtons = new ArrayList<>();
    
        // Initialize TextViews, RadioGroups, and ImageViews
        for (int i = 1; i <= 100; i++) {
            int textViewResID = getResources().getIdentifier("questionTextView" + i, "id", getPackageName());
            TextView textView = findViewById(textViewResID);
            if (textView != null) {
                questionTextViews.add(textView);
            } else {
                Log.e("VerbalQuestionnaireActivity", "TextView with ID questionTextView" + i + " not found.");
            }
    
            int radioGroupResID = getResources().getIdentifier("choiceRadioGroup" + i, "id", getPackageName());
            RadioGroup radioGroup = findViewById(radioGroupResID);
            if (radioGroup != null) {
                choiceRadioGroups.add(radioGroup);
            } else {
                Log.e("VerbalQuestionnaireActivity", "RadioGroup with ID choiceRadioGroup" + i + " not found.");
            }
    
            int textToSpeechButtonResID = getResources().getIdentifier("textToSpeechButton" + i, "id", getPackageName());
            ImageView textToSpeechButton = findViewById(textToSpeechButtonResID);
            if (textToSpeechButton != null) {
                textToSpeechButtons.add(textToSpeechButton);
            } else {
                Log.e("VerbalQuestionnaireActivity", "ImageView with ID textToSpeechButton" + i + " not found.");
            }
        }
    
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("Fill in the blank:\n'The CEO's speech was __________ with inspirational quotes and strategic insights.'", createChoices("A. Replete", "B. Devoid", "C. Sparse", "D. Vacant"), 0));
        questions.add(new Question("Antonym:\nSelect the word that is the opposite of 'diligent':", createChoices("A. Lazy", "B. Industrious", "C. Hardworking", "D. Dedicated"), 0));
        questions.add(new Question("Analogies:\n'Editor is to manuscript as conductor is to __________.'", createChoices("A. Orchestra", "B. Music", "C. Baton", "D. Audience"), 0));
        questions.add(new Question("Sentence Correction:\nWhich of the following sentences is grammatically correct?", createChoices("A. The project was completed by she and her team.", "B. He is the most smarter person in the group.", "C. The report needs to be reviewed by him and I.", "D. She is an expert in financial analysis."), 3));
        questions.add(new Question("Synonyms:\nChoose the word that is most similar in meaning to 'proficient':", createChoices("A. Adequate", "B. Skilled", "C. Amateur", "D. Mediocre"), 1));
        questions.add(new Question("Sentence Completion:\n'The negotiation process requires patience, flexibility, and __________ communication skills.'", createChoices("A. Articulate", "B. Ambiguous", "C. Verbose", "D. Concise"), 0));
        questions.add(new Question("Word Jumble:\nRearrange the letters to form a meaningful word:\n'O C U M M N I A T I O N'\nHint: It's a term related to business dealings.", createChoices("A. Communication", "B. Accumulation", "C. Commutation", "D. Annunciation"), 0));
        questions.add(new Question("Reading Comprehension:\nRead the passage and answer the question:\n'The stock market experienced a sharp decline due to economic uncertainties. What caused the market downturn?'", createChoices("A. Stable economy", "B. Uncertainties", "C. Positive outlook", "D. Government intervention"), 1));
        questions.add(new Question("Sentence Ordering:\nArrange the following words to form a coherent sentence:\n'Strategies - innovative - company - the - implemented - to - stay - competitive.'", createChoices("A. The company implemented innovative strategies to stay competitive.", "B. To stay competitive, the company implemented innovative strategies.", "C. Implemented to stay competitive, the company innovative strategies.", "D. Innovative strategies to stay competitive the company implemented."), 0));
        questions.add(new Question("Error Identification:\nIdentify the error in the following sentence:\n'The team is working hard to ensure their project meets it's deadline.'", createChoices("A. Their", "B. Meets", "C. It's", "D. Working"), 2));
        questions.add(new Question("Fill in the blank:\n'The marketing campaign was a huge __________, resulting in a significant increase in sales.'", createChoices("A. Triumph", "B. Failure", "C. Setback", "D. Disappointment"), 0));
        questions.add(new Question("Antonym:\nSelect the word that is the opposite of 'concur':", createChoices("A. Agree", "B. Dissent", "C. Approve", "D. Comply"), 1));
        questions.add(new Question("Analogies:\n'Author is to book as sculptor is to __________.'", createChoices("A. Marble", "B. Paint", "C. Canvas", "D. Clay"), 3));
        questions.add(new Question("Sentence Correction:\nWhich of the following sentences is grammatically correct?", createChoices("A. The team has been working diligently to achieve their goals.", "B. Him and his colleagues are attending the conference.", "C. Each member of the committee have submitted their reports.", "D. She is more smarter than her peers."), 0));
        questions.add(new Question("Synonyms:\nChoose the word that is most similar in meaning to 'proficient':", createChoices("A. Adept", "B. Inept", "C. Mediocre", "D. Amateur"), 0));
        questions.add(new Question("Fill in the blank:\n'The negotiation reached a __________ when both parties agreed to the terms.'", createChoices("A. Stalemate", "B. Compromise", "C. Impasse", "D. Deadlock"), 1));
        questions.add(new Question("Antonym:\nSelect the word that is the opposite of 'obsolete':", createChoices("A. Current", "B. Modern", "C. Outdated", "D. Ancient"), 0));
        questions.add(new Question("Analogies:\n'Chef is to kitchen as conductor is to __________.'", createChoices("A. Train", "B. Symphony", "C. Music", "D. Orchestra"), 3));
        questions.add(new Question("Sentence Correction:\nWhich of the following sentences is grammatically correct?", createChoices("A. The company's profits have significantly increased last quarter.", "B. Each member of the team have different responsibilities.", "C. Her presentation was more better than his.", "D. The project manager oversees all aspects of the project."), 3));
        questions.add(new Question("Synonyms:\nChoose the word that is most similar in meaning to 'precise':", createChoices("A. Accurate", "B. Vague", "C. Approximate", "D. Uncertain"), 0));
        questions.add(new Question("Sentence Completion:\n'The team's ability to adapt to changing circumstances was crucial for __________ success.'", createChoices("A. Their", "B. It's", "C. There", "D. Its"), 3));
        questions.add(new Question("Word Jumble:\nRearrange the letters to form a meaningful word:\n'I T N E V E S T M N E'\nHint: It's a term related to financial markets.", createChoices("A. Investment", "B. Statement", "C. Sentiment", "D. Entertainment"), 0));
        questions.add(new Question("Reading Comprehension:\nRead the passage and answer the question:\n'The article discusses the benefits of diversifying investment portfolios. Why is diversification important in investing?'", createChoices("A. To focus on a single investment", "B. To reduce risk", "C. To maximize returns", "D. To predict market trends"), 1));
        questions.add(new Question("Sentence Ordering:\nArrange the following words to form a coherent sentence:\n'Strategies - innovative - company - the - implemented - to - stay - competitive.'", createChoices("A. The company implemented innovative strategies to stay competitive.", "B. To stay competitive, the company implemented innovative strategies.", "C. Implemented to stay competitive, the company innovative strategies.", "D. Innovative strategies to stay competitive the company implemented."), 0));
        questions.add(new Question("Error Identification:\nIdentify the error in the following sentence:\n'The team is working hard to ensure their project meets it's deadline.'", createChoices("A. Their", "B. Meets", "C. It's", "D. Working"), 2));
        questions.add(new Question("Fill in the blank: \"The successful candidate demonstrated exceptional __________ during the interview.\"", createChoices("A. Prowess", "B. Weakness", "C. Incompetence", "D. Inability"), 0));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"beneficial\":", createChoices("A. Advantageous", "B. Favorable", "C. Detrimental", "D. Profitable"), 2));
        questions.add(new Question("Analogies: \"Teacher is to classroom as conductor is to __________.\"", createChoices("A. Music", "B. Orchestra", "C. Baton", "D. Audience"), 1));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The team have completed their tasks ahead of schedule.", "B. Him and his colleagues are attending the seminar.", "C. She is more talented than any other artist in the gallery.", "D. Each member of the committee has a specific role to play."), 3));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"proficient\":", createChoices("A. Adept", "B. Inept", "C. Mediocre", "D. Amateur"), 0));
        questions.add(new Question("Fill in the blank: \"The team's collaborative effort led to the __________ completion of the project.\"", createChoices("A. Timely", "B. Delayed", "C. Hasty", "D. Rushed"), 0));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"innovative\":", createChoices("A. Creative", "B. Conventional", "C. Original", "D. Inventive"), 1));
        questions.add(new Question("Analogies: \"Painter is to canvas as writer is to __________.\"", createChoices("A. Pen", "B. Paper", "C. Novel", "D. Ink"), 1));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The team's efforts have resulted in a successful completion of the project.", "B. Each members of the team have contributed to the project.", "C. The report needs to be reviewed by him and I.", "D. She is more smarter than her peers."), 0));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"precise\":", createChoices("A. Accurate", "B. Vague", "C. Approximate", "D. Uncertain"), 0));
        questions.add(new Question("Fill in the blank: \"The negotiation reached a __________ when both parties agreed to the terms.\"", createChoices("A. Stalemate", "B. Compromise", "C. Impasse", "D. Deadlock"), 1));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"obsolete\":", createChoices("A. Current", "B. Modern", "C. Outdated", "D. Ancient"), 0));
        questions.add(new Question("Analogies: \"Chef is to kitchen as conductor is to __________.\"", createChoices("A. Train", "B. Symphony", "C. Music", "D. Orchestra"), 3));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The company's profits have significantly increased last quarter.", "B. Each member of the team have different responsibilities.", "C. Her presentation was more better than his.", "D. The project manager oversees all aspects of the project."), 3));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"precise\":", createChoices("A. Accurate", "B. Vague", "C. Approximate", "D. Uncertain"), 0));
        questions.add(new Question("Sentence Completion: \"The team's ability to adapt to changing circumstances was crucial for __________ success.\"", createChoices("A. Their", "B. It's", "C. There", "D. Its"), 3));
        questions.add(new Question("Word Jumble: Rearrange the letters to form a meaningful word: \"I T N E V E S T M N E\" Hint: It's a term related to financial markets.", createChoices("A. Investment", "B. Statement", "C. Sentiment", "D. Entertainment"), 0));
        questions.add(new Question("Reading Comprehension: Read the passage and answer the question: \"The article discusses the benefits of diversifying investment portfolios. Why is diversification important in investing?\"", createChoices("A. To focus on a single investment", "B. To reduce risk", "C. To maximize returns", "D. To predict market trends"), 1));
        questions.add(new Question("Sentence Ordering: Arrange the following words to form a coherent sentence: \"Strategies - innovative - company - the - implemented - to - stay - competitive.\"", createChoices("A. The company implemented innovative strategies to stay competitive.", "B. To stay competitive, the company implemented innovative strategies.", "C. Implemented to stay competitive, the company innovative strategies.", "D. Innovative strategies to stay competitive the company implemented."), 0));
        questions.add(new Question("Error Identification: Identify the error in the following sentence: \"The team is working hard to ensure their project meets it's deadline.\"", createChoices("A. Their", "B. Meets", "C. It's", "D. Working"), 2));
        questions.add(new Question("Fill in the blank: \"The successful candidate demonstrated exceptional __________ during the interview.\"", createChoices("A. Prowess", "B. Weakness", "C. Incompetence", "D. Inability"), 0));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"beneficial\":", createChoices("A. Advantageous", "B. Favorable", "C. Detrimental", "D. Profitable"), 2));
        questions.add(new Question("Analogies: \"Teacher is to classroom as conductor is to __________.\"", createChoices("A. Music", "B. Orchestra", "C. Baton", "D. Audience"), 1));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The team have completed their tasks ahead of schedule.", "B. Him and his colleagues are attending the seminar.", "C. She is more talented than any other artist in the gallery.", "D. Each member of the committee has a specific role to play."), 3));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"proficient\":", createChoices("A. Adept", "B. Inept", "C. Mediocre", "D. Amateur"), 0));
        questions.add(new Question("Fill in the blank: \"The team's collaborative effort led to the __________ completion of the project.\"", createChoices("A. Timely", "B. Delayed", "C. Hasty", "D. Rushed"), 0));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"innovative\":", createChoices("A. Creative", "B. Conventional", "C. Original", "D. Inventive"), 1));
        questions.add(new Question("Analogies: \"Painter is to canvas as writer is to __________.\"", createChoices("A. Pen", "B. Paper", "C. Novel", "D. Ink"), 1));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The team's efforts have resulted in a successful completion of the project.", "B. Each members of the team have contributed to the project.", "C. The report needs to be reviewed by him and I.", "D. She is more smarter than her peers."), 0));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"precise\":", createChoices("A. Accurate", "B. Vague", "C. Approximate", "D. Uncertain"), 0));
        questions.add(new Question("Fill in the blank: \"The negotiation reached a __________ when both parties agreed to the terms.\"", createChoices("A. Stalemate", "B. Compromise", "C. Impasse", "D. Deadlock"), 1));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"obsolete\":", createChoices("A. Current", "B. Modern", "C. Outdated", "D. Ancient"), 0));
        questions.add(new Question("Analogies: \"Chef is to kitchen as conductor is to __________.\"", createChoices("A. Train", "B. Symphony", "C. Music", "D. Orchestra"), 3));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The company's profits have significantly increased last quarter.", "B. Each member of the team have different responsibilities.", "C. Her presentation was more better than his.", "D. The project manager oversees all aspects of the project."), 3));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"precise\":", createChoices("A. Accurate", "B. Vague", "C. Approximate", "D. Uncertain"), 0));
        questions.add(new Question("Sentence Completion: \"The team's ability to adapt to changing circumstances was crucial for __________ success.\"", createChoices("A. Their", "B. It's", "C. There", "D. Its"), 3));
        questions.add(new Question("Word Jumble: Rearrange the letters to form a meaningful word: \"I T N E V E S T M N E\" Hint: It's a term related to financial markets.", createChoices("A. Investment", "B. Statement", "C. Sentiment", "D. Entertainment"), 0));
        questions.add(new Question("Reading Comprehension: Read the passage and answer the question: \"The article discusses the benefits of diversifying investment portfolios. Why is diversification important in investing?\"", createChoices("A. To focus on a single investment", "B. To reduce risk", "C. To maximize returns", "D. To predict market trends"), 1));
        questions.add(new Question("Sentence Ordering: Arrange the following words to form a coherent sentence: \"Strategies - innovative - company - the - implemented - to - stay - competitive.\"", createChoices("A. The company implemented innovative strategies to stay competitive.", "B. To stay competitive, the company implemented innovative strategies.", "C. Implemented to stay competitive, the company innovative strategies.", "D. Innovative strategies to stay competitive the company implemented."), 0));
        questions.add(new Question("Error Identification: Identify the error in the following sentence: \"The team is working hard to ensure their project meets it's deadline.\"", createChoices("A. Their", "B. Meets", "C. It's", "D. Working"), 2));
        questions.add(new Question("Fill in the blank: \"The successful candidate demonstrated exceptional __________ during the interview.\"", createChoices("A. Prowess", "B. Weakness", "C. Incompetence", "D. Inability"), 0));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"beneficial\":", createChoices("A. Advantageous", "B. Favorable", "C. Detrimental", "D. Profitable"), 2));
        questions.add(new Question("Analogies: \"Teacher is to classroom as conductor is to __________.\"", createChoices("A. Music", "B. Orchestra", "C. Baton", "D. Audience"), 1));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The team have completed their tasks ahead of schedule.", "B. Him and his colleagues are attending the seminar.", "C. She is more talented than any other artist in the gallery.", "D. Each member of the committee has a specific role to play."), 3));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"proficient\":", createChoices("A. Adept", "B. Inept", "C. Mediocre", "D. Amateur"), 0));
        questions.add(new Question("Fill in the blank: \"The team's collaborative effort led to the __________ completion of the project.\"", createChoices("A. Timely", "B. Delayed", "C. Hasty", "D. Rushed"), 0));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"innovative\":", createChoices("A. Creative", "B. Conventional", "C. Original", "D. Inventive"), 1));
        questions.add(new Question("Analogies: \"Painter is to canvas as writer is to __________.\"", createChoices("A. Pen", "B. Paper", "C. Novel", "D. Ink"), 1));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The team's efforts have resulted in a successful completion of the project.", "B. Each members of the team have contributed to the project.", "C. The report needs to be reviewed by him and I.", "D. She is more smarter than her peers."), 0));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"precise\":", createChoices("A. Accurate", "B. Vague", "C. Approximate", "D. Uncertain"), 0));
        questions.add(new Question("Fill in the blank: \"The negotiation reached a __________ when both parties agreed to the terms.\"", createChoices("A. Stalemate", "B. Compromise", "C. Impasse", "D. Deadlock"), 1));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"obsolete\":", createChoices("A. Current", "B. Modern", "C. Outdated", "D. Ancient"), 0));
        questions.add(new Question("Analogies: \"Chef is to kitchen as conductor is to __________.\"", createChoices("A. Train", "B. Symphony", "C. Music", "D. Orchestra"), 3));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The company's profits have significantly increased last quarter.", "B. Each member of the team have different responsibilities.", "C. Her presentation was more better than his.", "D. The project manager oversees all aspects of the project."), 3));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"precise\":", createChoices("A. Accurate", "B. Vague", "C. Approximate", "D. Uncertain"), 0));
        questions.add(new Question("Sentence Completion: \"The team's ability to adapt to changing circumstances was crucial for __________ success.\"", createChoices("A. Their", "B. It's", "C. There", "D. Its"), 3));
        questions.add(new Question("Word Jumble: Rearrange the letters to form a meaningful word: \"I T N E V E S T M N E\" Hint: It's a term related to financial markets.", createChoices("A. Investment", "B. Statement", "C. Sentiment", "D. Entertainment"), 0));
        questions.add(new Question("Reading Comprehension: Read the passage and answer the question: \"The article discusses the benefits of diversifying investment portfolios. Why is diversification important in investing?\"", createChoices("A. To focus on a single investment", "B. To reduce risk", "C. To maximize returns", "D. To predict market trends"), 1));
        questions.add(new Question("Sentence Ordering: Arrange the following words to form a coherent sentence: \"Strategies - innovative - company - the - implemented - to - stay - competitive.\"", createChoices("A. The company implemented innovative strategies to stay competitive.", "B. To stay competitive, the company implemented innovative strategies.", "C. Implemented to stay competitive, the company innovative strategies.", "D. Innovative strategies to stay competitive the company implemented."), 0));
        questions.add(new Question("Error Identification: Identify the error in the following sentence: \"The team is working hard to ensure their project meets it's deadline.\"", createChoices("A. Their", "B. Meets", "C. It's", "D. Working"), 2));
        questions.add(new Question("Fill in the blank: \"The successful candidate demonstrated exceptional __________ during the interview.\"", createChoices("A. Prowess", "B. Weakness", "C. Incompetence", "D. Inability"), 0));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"beneficial\":", createChoices("A. Advantageous", "B. Favorable", "C. Detrimental", "D. Profitable"), 2));
        questions.add(new Question("Analogies: \"Teacher is to classroom as conductor is to __________.\"", createChoices("A. Music", "B. Orchestra", "C. Baton", "D. Audience"), 1));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The team have completed their tasks ahead of schedule.", "B. Him and his colleagues are attending the seminar.", "C. She is more talented than any other artist in the gallery.", "D. Each member of the committee has a specific role to play."), 3));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"proficient\":", createChoices("A. Adept", "B. Inept", "C. Mediocre", "D. Amateur"), 0));
        questions.add(new Question("Fill in the blank: \"The team's collaborative effort led to the __________ completion of the project.\"", createChoices("A. Timely", "B. Delayed", "C. Hasty", "D. Rushed"), 0));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"innovative\":", createChoices("A. Creative", "B. Conventional", "C. Original", "D. Inventive"), 1));
        questions.add(new Question("Analogies: \"Painter is to canvas as writer is to __________.\"", createChoices("A. Pen", "B. Paper", "C. Novel", "D. Ink"), 1));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The team's efforts have resulted in a successful completion of the project.", "B. Each members of the team have contributed to the project.", "C. The report needs to be reviewed by him and I.", "D. She is more smarter than her peers."), 0));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"precise\":", createChoices("A. Accurate", "B. Vague", "C. Approximate", "D. Uncertain"), 0));
        questions.add(new Question("Fill in the blank: \"The negotiation reached a __________ when both parties agreed to the terms.\"", createChoices("A. Stalemate", "B. Compromise", "C. Impasse", "D. Deadlock"), 1));
        questions.add(new Question("Antonym: Select the word that is the opposite of \"obsolete\":", createChoices("A. Current", "B. Modern", "C. Outdated", "D. Ancient"), 0));
        questions.add(new Question("Analogies: \"Chef is to kitchen as conductor is to __________.\"", createChoices("A. Train", "B. Symphony", "C. Music", "D. Orchestra"), 3));
        questions.add(new Question("Sentence Correction: Which of the following sentences is grammatically correct?", createChoices("A. The company's profits have significantly increased last quarter.", "B. Each member of the team have different responsibilities.", "C. Her presentation was more better than his.", "D. The project manager oversees all aspects of the project."), 3));
        questions.add(new Question("Synonyms: Choose the word that is most similar in meaning to \"precise\":", createChoices("A. Accurate", "B. Vague", "C. Approximate", "D. Uncertain"), 0));
    }    

    private void setOnClickListeners() {
        nextButton.setOnClickListener(view -> {
            if (areAllQuestionsAnswered()) {
                if (currentQuestionIndex < TOTAL_PAGES - 1) {
                    currentQuestionIndex++;
                    showPage(currentQuestionIndex);
                }
            } else {
                showUnansweredQuestionsAlert();
            }
        });
    
        for (int i = 0; i < textToSpeechButtons.size(); i++) {
            int finalI = i; // To access in lambda expression
            ImageView button = textToSpeechButtons.get(finalI);
            if (button != null) {
                button.setOnClickListener(view -> speakText(questions.get(finalI).getQuestionText() + ". " + getChoicesText(finalI)));
            } else {
                Log.e("VerbalQuestionnaireActivity", "ImageView in textToSpeechButtons list is null at index " + finalI);
            }
        }
    
        submitButton.setOnClickListener(view -> {
            // Save current page's answers before showing score
            saveCurrentPageAnswers();
            showScoreDialog();
        });
    }
    
    private boolean areAllQuestionsAnswered() {
        int start = currentQuestionIndex * QUESTIONS_PER_PAGE;
        int end = Math.min(start + QUESTIONS_PER_PAGE, questions.size());
    
        for (int i = start; i < end; i++) {
            int selectedId = choiceRadioGroups.get(i - start).getCheckedRadioButtonId();
            if (selectedId == -1) {
                return false; // Found an unanswered question
            }
        }
        return true; // All questions are answered
    }
    
    private void showUnansweredQuestionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please answer all questions before proceeding.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Save current page's answers before navigating
                        saveCurrentPageAnswers();
                        
                        // Find the first unanswered question and show it
                        int unansweredQuestionIndex = findFirstUnansweredQuestionIndex();
                        if (unansweredQuestionIndex != -1) {
                            showQuestion(unansweredQuestionIndex);
                        }
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }    
    
    private int findFirstUnansweredQuestionIndex() {
        int start = currentQuestionIndex * QUESTIONS_PER_PAGE;
        int end = Math.min(start + QUESTIONS_PER_PAGE, questions.size());
    
        for (int i = start; i < end; i++) {
            int selectedId = choiceRadioGroups.get(i - start).getCheckedRadioButtonId();
            if (selectedId == -1) {
                return i; // Return the index of the first unanswered question
            }
        }
        return -1; // All questions are answered
    }
    
    private void showQuestion(int index) {
        // Calculate the page number of the question
        int pageIndex = index / QUESTIONS_PER_PAGE;
        int questionIndexOnPage = index % QUESTIONS_PER_PAGE;
    
        // Update the current page index
        currentQuestionIndex = pageIndex;
    
        // Show the corresponding page
        showPage(pageIndex);
    
        // Scroll to the specific question if needed
        scrollToQuestion(questionIndexOnPage);
    }
    
    private void scrollToQuestion(int questionIndexOnPage) {
        // Find the view for the specific question
        if (questionIndexOnPage < questionTextViews.size()) {
            // Set focus to the TextView of the unanswered question
            questionTextViews.get(questionIndexOnPage).requestFocus();
        } else {
            Log.e("VerbalQuestionnaireActivity", "Invalid question index for scrolling: " + questionIndexOnPage);
        }
    }                

    private void showPage(int pageIndex) {
        int start = pageIndex * QUESTIONS_PER_PAGE;
        int end = Math.min(start + QUESTIONS_PER_PAGE, questions.size());
        
        // Hide all question views first
        for (int i = 0; i < questionTextViews.size(); i++) {
            questionTextViews.get(i).setVisibility(View.GONE);
            choiceRadioGroups.get(i).setVisibility(View.GONE);
            textToSpeechButtons.get(i).setVisibility(View.GONE);
        }
        
        // Show only the views for the current page
        for (int i = start; i < end; i++) {
            Question question = questions.get(i);
            TextView textView = questionTextViews.get(i - start);
            RadioGroup radioGroup = choiceRadioGroups.get(i - start);
            ImageView textToSpeechButton = textToSpeechButtons.get(i - start);
        
            textView.setText(question.getQuestionText());
            textView.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
            textToSpeechButton.setVisibility(View.VISIBLE);
        
            List<String> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(j);
                radioButton.setText(choices.get(j));
            }
        
            // Restore the previously selected answer
            if (question.getSelectedAnswerIndex() != -1) {
                ((RadioButton) radioGroup.getChildAt(question.getSelectedAnswerIndex())).setChecked(true);
            } else {
                radioGroup.clearCheck(); // Ensure no selection if nothing was previously selected
            }
        }
        
        // Show or hide the next and submit buttons
        nextButton.setVisibility(pageIndex < TOTAL_PAGES - 1 ? View.VISIBLE : View.INVISIBLE);
        submitButton.setVisibility(pageIndex == TOTAL_PAGES - 1 ? View.VISIBLE : View.INVISIBLE);
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

    private void saveCurrentPageAnswers() {
        int start = currentQuestionIndex * QUESTIONS_PER_PAGE;
        int end = Math.min(start + QUESTIONS_PER_PAGE, questions.size());
    
        for (int i = start; i < end; i++) {
            int selectedId = choiceRadioGroups.get(i - start).getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);
                int selectedAnswerIndex = choiceRadioGroups.get(i - start).indexOfChild(selectedRadioButton);
                questions.get(i).setSelectedAnswerIndex(selectedAnswerIndex);
            }
        }
    }    

    private void showScoreDialog() {
        int correctAnswers = 0;
        StringBuilder correctAnswersText = new StringBuilder();
        
        // Calculate the score and correct answers
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
        int selectedId = choiceRadioGroups.get(index % QUESTIONS_PER_PAGE).getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        int selectedAnswerIndex = choiceRadioGroups.get(index % QUESTIONS_PER_PAGE).indexOfChild(selectedRadioButton);
        questions.get(index).setSelectedAnswerIndex(selectedAnswerIndex); // Save the selected answer
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