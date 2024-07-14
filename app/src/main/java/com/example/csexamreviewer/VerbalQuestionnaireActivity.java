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
    
        for (int i = 0; i < questions.size(); i++) {
            if (choiceRadioGroups.get(i).getCheckedRadioButtonId() == -1) {
                allAnswered = false;
                break;
            }
            if (checkAnswer(i)) {
                correctAnswers++;
                correctAnswersText.append(i + 1).append(". Correct\n");
            } else {
                correctAnswersText.append(i + 1).append(". (Correct Answer: ")
                        .append(questions.get(i).getChoices().get(questions.get(i).getCorrectAnswerIndex())).append(")\n");
            }
        }
    
        if (!allAnswered) {
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
