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

public class SubVerbalQuestionnaireActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

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
        questions.add(new Question("Choose the word that is most similar to 'Courageous':", createChoices("A) Timid", "B) Fearful", "C) Brave", "D) Cowardly"), 2));
        questions.add(new Question("Choose the word that is most opposite in meaning to 'Abundant':", createChoices("A) Sparse", "B) Plentiful", "C) Rich", "D) Lavish"), 0));
        questions.add(new Question("Select the correctly spelled word:", createChoices("A) Accomodate", "B) Accommodate", "C) Acomodate", "D) Acommodate"), 1));
        questions.add(new Question("Find the synonym of the word 'Elusive':", createChoices("A) Clear", "B) Captivating", "C) Difficult to catch", "D) Easy to understand"), 2));
        questions.add(new Question("Choose the word that best completes the sentence: 'He was known for his _______ manner, always polite and gentle.'", createChoices("A) Brusque", "B) Aloof", "C) Affable", "D) Arrogant"), 2));
        questions.add(new Question("What is the meaning of the word 'Incessant'?", createChoices("A) Brief", "B) Never-ending", "C) Rare", "D) Sporadic"), 1));
        questions.add(new Question("Choose the word that is most similar to 'Ponder':", createChoices("A) Ignore", "B) Think over", "C) Forget", "D) Refuse"), 1));
        questions.add(new Question("Select the antonym for 'Vivid':", createChoices("A) Bright", "B) Dull", "C) Shiny", "D) Clear"), 1));
        questions.add(new Question("Which word does not belong with the others?", createChoices("A) Tiger", "B) Lion", "C) Elephant", "D) Leopard"), 2));
        questions.add(new Question("Choose the word that best completes the sentence: 'The students were very _______ about the new policy.'", createChoices("A) Indifferent", "B) Enthusiastic", "C) Apathetic", "D) Hostile"), 1));
        questions.add(new Question("What is the meaning of the word 'Concur'?", createChoices("A) Disagree", "B) Agree", "C) Compete", "D) Complain"), 1));
        questions.add(new Question("Choose the correct spelling:", createChoices("A) Aceppt", "B) Accept", "C) Except", "D) Exept"), 1));
        questions.add(new Question("Which of the following is most similar to 'Reluctant'?", createChoices("A) Eager", "B) Hesitant", "C) Willing", "D) Compliant"), 1));
        questions.add(new Question("Select the word that is most opposite in meaning to 'Amiable':", createChoices("A) Friendly", "B) Kind", "C) Hostile", "D) Pleasant"), 2));
        questions.add(new Question("Find the synonym for 'Diligent':", createChoices("A) Lazy", "B) Hardworking", "C) Careless", "D) Ignorant"), 1));
        questions.add(new Question("What is the meaning of the word 'Meticulous'?", createChoices("A) Careless", "B) Thorough", "C) Reckless", "D) Forgetful"), 1));
        questions.add(new Question("Choose the word that best completes the sentence: 'She was known for her _______ attitude, always helping others.'", createChoices("A) Selfish", "B) Generous", "C) Rude", "D) Mean"), 1));
        questions.add(new Question("Which word is a synonym for 'Adverse'?", createChoices("A) Helpful", "B) Unfavorable", "C) Beneficial", "D) Positive"), 1));
        questions.add(new Question("What is the meaning of the word 'Lethargic'?", createChoices("A) Energetic", "B) Active", "C) Lazy", "D) Alert"), 2));
        questions.add(new Question("Select the word that is most similar to 'Vex':", createChoices("A) Please", "B) Annoy", "C) Soothe", "D) Calm"), 1));
        questions.add(new Question("Choose the word that best completes the sentence: 'His _______ remarks made everyone feel uncomfortable.'", createChoices("A) Polite", "B) Offensive", "C) Courteous", "D) Respectful"), 1));
        questions.add(new Question("What is the meaning of the word 'Candid'?", createChoices("A) Deceptive", "B) Frank", "C) Secretive", "D) Reserved"), 1));
        questions.add(new Question("Select the antonym for 'Arrogant':", createChoices("A) Proud", "B) Humble", "C) Conceited", "D) Selfish"), 1));
        questions.add(new Question("Which word does not belong with the others?", createChoices("A) Ambition", "B) Goal", "C) Desire", "D) Wealth"), 3));
        questions.add(new Question("Choose the word that best completes the sentence: 'He gave a(n) _______ answer to the question.'", createChoices("A) Vague", "B) Direct", "C) Ambiguous", "D) Clear"), 1));
        questions.add(new Question("What is the meaning of the word 'Obsolete'?", createChoices("A) New", "B) Outdated", "C) Useful", "D) Popular"), 1));
        questions.add(new Question("Select the word that is most similar to 'Concur':", createChoices("A) Disagree", "B) Approve", "C) Oppose", "D) Debate"), 1));
        questions.add(new Question("Find the synonym for 'Elated':", createChoices("A) Sad", "B) Depressed", "C) Excited", "D) Angry"), 2));
        questions.add(new Question("What is the meaning of the word 'Plausible'?", createChoices("A) Improbable", "B) Likely", "C) Impossible", "D) Unbelievable"), 1));
        questions.add(new Question("Choose the word that best completes the sentence: 'She made a(n) _______ decision after much thought.'", createChoices("A) Hasty", "B) Thoughtless", "C) Deliberate", "D) Impulsive"), 2));
        questions.add(new Question("Which word does not belong with the others?", createChoices("A) Happiness", "B) Joy", "C) Sadness", "D) Cheerfulness"), 2));
        questions.add(new Question("What is the meaning of the word 'Scrupulous'?", createChoices("A) Dishonest", "B) Careless", "C) Ethical", "D) Irresponsible"), 2));
        questions.add(new Question("Select the antonym for 'Fervent':", createChoices("A) Passionate", "B) Ardent", "C) Apathetic", "D) Zealous"), 2));
        questions.add(new Question("Choose the word that is most similar to 'Meticulous':", createChoices("A) Careless", "B) Detailed", "C) Negligent", "D) Hasty"), 1));
        questions.add(new Question("What is the meaning of the word 'Aesthetic'?", createChoices("A) Ugly", "B) Artistic", "C) Unpleasant", "D) Functional"), 1));
        questions.add(new Question("Select the word that best completes the sentence: 'His _______ behavior made him popular among his friends.'", createChoices("A) Arrogant", "B) Humble", "C) Stubborn", "D) Generous"), 3));
        questions.add(new Question("Which word does not belong with the others?", createChoices("A) Lively", "B) Animated", "C) Active", "D) Sluggish"), 3));
        questions.add(new Question("Choose the word that is most similar to 'Frivolous':", createChoices("A) Serious", "B) Silly", "C) Important", "D) Grave"), 1));
        questions.add(new Question("What is the meaning of the word 'Pernicious'?", createChoices("A) Harmless", "B) Beneficial", "C) Harmful", "D) Innocuous"), 2));
        questions.add(new Question("Select the antonym for 'Transitory':", createChoices("A) Temporary", "B) Brief", "C) Permanent", "D) Fleeting"), 2));
        questions.add(new Question("Choose the word that best completes the sentence: 'Her _______ response revealed her lack of interest.'", createChoices("A) Eager", "B) Enthusiastic", "C) Tepid", "D) Passionate"), 2));
        questions.add(new Question("What is the meaning of the word 'Innocuous'?", createChoices("A) Harmful", "B) Dangerous", "C) Harmless", "D) Risky"), 2));
        questions.add(new Question("Select the word that is most similar to 'Insidious':", createChoices("A) Harmless", "B) Dangerous", "C) Obvious", "D) Clear"), 1));
        questions.add(new Question("What is the meaning of the word 'Cacophony'?", createChoices("A) Harmony", "B) Melody", "C) Noise", "D) Silence"), 2));
        questions.add(new Question("Which of the following words is a synonym for 'Convoluted'?", createChoices("A) Simple", "B) Complex", "C) Clear", "D) Direct"), 1));
        questions.add(new Question("What is the meaning of the word 'Recalcitrant'?", createChoices("A) Obedient", "B) Defiant", "C) Helpful", "D) Passive"), 1));
        questions.add(new Question("Select the word that is most opposite in meaning to 'Candid':", createChoices("A) Honest", "B) Forthright", "C) Deceptive", "D) Open"), 2));
        questions.add(new Question("Which of the following words means 'to formally withdraw' or 'to take back'?", createChoices("A) Retain", "B) Revoke", "C) Assert", "D) Maintain"), 1));
        questions.add(new Question("Choose the word that best completes the sentence: 'The manager's _______ was crucial for the success of the project.'", createChoices("A) Neglect", "B) Indifference", "C) Involvement", "D) Apathy"), 2));
        questions.add(new Question("What is the meaning of the word 'Inevitable'?", createChoices("A) Avoidable", "B) Uncertain", "C) Certain to happen", "D) Unlikely"), 2));
        questions.add(new Question("Select the antonym for 'Spontaneous':", createChoices("A) Natural", "B) Unplanned", "C) Planned", "D) Instinctive"), 2));
        questions.add(new Question("Choose the word that is most similar to 'Abate':", createChoices("A) Increase", "B) Intensify", "C) Reduce", "D) Expand"), 2));
        questions.add(new Question("What is the meaning of the word 'Sycophant'?", createChoices("A) Critic", "B) Follower", "C) Praise", "D) Obsequious flatterer"), 3));
        questions.add(new Question("Which of the following words means 'a false or misleading statement'?", createChoices("A) Fact", "B) Fiction", "C) Fabrication", "D) Truth"), 2));
        questions.add(new Question("What is the meaning of the word 'Exacerbate'?", createChoices("A) Alleviate", "B) Improve", "C) Worsen", "D) Simplify"), 2));
        questions.add(new Question("Choose the word that best completes the sentence: 'The teacher's _______ in handling classroom disruptions was commendable.'", createChoices("A) Apathy", "B) Incompetence", "C) Skill", "D) Negligence"), 2));
        questions.add(new Question("Select the word that is most similar to 'Ephemeral':", createChoices("A) Eternal", "B) Lasting", "C) Temporary", "D) Permanent"), 2));
        questions.add(new Question("What is the meaning of the word 'Ubiquitous'?", createChoices("A) Rare", "B) Everywhere", "C) Limited", "D) Uncommon"), 1));
        questions.add(new Question("Select the antonym for 'Ambiguous':", createChoices("A) Vague", "B) Unclear", "C) Clear", "D) Confusing"), 2));
        questions.add(new Question("Which word is most similar in meaning to 'Plethora'?", createChoices("A) Scarcity", "B) Excess", "C) Shortage", "D) Lack"), 1));
        questions.add(new Question("What is the meaning of the word 'Plausible'?", createChoices("A) Unlikely", "B) Improbable", "C) Believable", "D) Absurd"), 2));
        questions.add(new Question("Choose the word that best completes the sentence: 'Her _______ approach to problem-solving often led to creative solutions.'", createChoices("A) Conventional", "B) Unorthodox", "C) Routine", "D) Predictable"), 1));
        questions.add(new Question("Select the word that is most opposite in meaning to 'Frugal':", createChoices("A) Thrifty", "B) Economical", "C) Wasteful", "D) Cautious"), 2));
        questions.add(new Question("What is the meaning of the word 'Eloquent'?", createChoices("A) Mute", "B) Inarticulate", "C) Persuasive and articulate", "D) Silent"), 2));
        questions.add(new Question("Which word is a synonym for 'Apathy'?", createChoices("A) Enthusiasm", "B) Interest", "C) Indifference", "D) Excitement"), 2));
        questions.add(new Question("What is the meaning of the word 'Venerable'?", createChoices("A) Young", "B) Hated", "C) Respected due to age", "D) Ignored"), 2));
        questions.add(new Question("Choose the word that best completes the sentence: 'His _______ to the new policy was evident from his constant complaints.'", createChoices("A) Acceptance", "B) Support", "C) Resistance", "D) Agreement"), 2));
        questions.add(new Question("What is the meaning of the word 'Voracious'?", createChoices("A) Indifferent", "B) Greedy", "C) Satisfied", "D) Apathetic"), 1));
        questions.add(new Question("Select the antonym for 'Ebullient':", createChoices("A) Cheerful", "B) Enthusiastic", "C) Bored", "D) Lively"), 2));
        questions.add(new Question("Which word is a synonym for 'Ambivalent'?", createChoices("A) Certain", "B) Uncertain", "C) Definite", "D) Resolute"), 1));
        questions.add(new Question("What is the meaning of the word 'Juxtapose'?", createChoices("A) Separate", "B) Compare side by side", "C) Confuse", "D) Isolate"), 1));
        questions.add(new Question("Choose the word that best completes the sentence: 'The debate became increasingly _______ as both sides presented their arguments.'", createChoices("A) Polite", "B) Civil", "C) Heated", "D) Friendly"), 2));
        questions.add(new Question("What is the meaning of the word 'Nefarious'?", createChoices("A) Good", "B) Evil", "C) Benevolent", "D) Kind"), 1));
        questions.add(new Question("Select the word that is most similar to 'Vexation':", createChoices("A) Calmness", "B) Annoyance", "C) Pleasure", "D) Contentment"), 1));
        questions.add(new Question("What is the meaning of the word 'Redundant'?", createChoices("A) Necessary", "B) Useful", "C) Repetitive", "D) Important"), 2));
        questions.add(new Question("Choose the word that best completes the sentence: 'The scientist's findings were _______ by further research.'", createChoices("A) Disproved", "B) Confirmed", "C) Ignored", "D) Misinterpreted"), 1));
        questions.add(new Question("What is the meaning of the word 'Mellifluous'?", createChoices("A) Harsh", "B) Pleasant sounding", "C) Dull", "D) Unpleasant"), 1));
        questions.add(new Question("Select the antonym for 'Fortitude':", createChoices("A) Strength", "B) Courage", "C) Weakness", "D) Resilience"), 2));
        questions.add(new Question("Which word does not belong with the others?", createChoices("A) Serene", "B) Calm", "C) Tranquil", "D) Agitated"), 3));
        questions.add(new Question("What is the meaning of the word 'Pernicious'?", createChoices("A) Helpful", "B) Harmful", "C) Safe", "D) Beneficial"), 1));
        questions.add(new Question("Choose the word that best completes the sentence: 'The CEO's _______ attitude toward the employees created a positive work environment.'", createChoices("A) Hostile", "B) Disrespectful", "C) Appreciative", "D) Negligent"), 2));
        questions.add(new Question("What is the meaning of the word 'Uplift'?", createChoices("A) Discourage", "B) Inspire", "C) Depress", "D) Weaken"), 1));
        questions.add(new Question("Select the word that is most similar to 'Ardent':", createChoices("A) Passionate", "B) Indifferent", "C) Apathetic", "D) Uninterested"), 0));
        questions.add(new Question("What is the meaning of the word 'Conspicuous'?", createChoices("A) Hidden", "B) Obvious", "C) Subtle", "D) Unnoticed"), 1));
        questions.add(new Question("Choose the word that best completes the sentence: 'Her _______ in negotiations helped her achieve the best terms for the deal.'", createChoices("A) Ineptitude", "B) Skillfulness", "C) Clumsiness", "D) Lack of experience"), 1));
        questions.add(new Question("What is the meaning of the word 'Benevolent'?", createChoices("A) Malevolent", "B) Kind", "C) Hostile", "D) Unfriendly"), 1));
        questions.add(new Question("Select the antonym for 'Resilient':", createChoices("A) Tough", "B) Flexible", "C) Fragile", "D) Strong"), 2));
        questions.add(new Question("Which of the following words is a synonym for 'Vigilant'?", createChoices("A) Careless", "B) Attentive", "C) Neglectful", "D) Disregardful"), 1));
        questions.add(new Question("What is the meaning of the word 'Translucent'?", createChoices("A) Opaque", "B) Clear", "C) Solid", "D) Dark"), 1));
        questions.add(new Question("Choose the word that best completes the sentence: 'The detective's _______ questioning led to the revelation of crucial details.'", createChoices("A) Casual", "B) Carefree", "C) Rigorous", "D) Inattentive"), 2));
        questions.add(new Question("What is the meaning of the word 'Cumbersome'?", createChoices("A) Efficient", "B) Light", "C) Difficult to handle", "D) Simple"), 2));
        questions.add(new Question("Select the word that is most opposite in meaning to 'Diligent':", createChoices("A) Hardworking", "B) Lazy", "C) Persistent", "D) Assiduous"), 1));
        questions.add(new Question("What is the meaning of the word 'Intricate'?", createChoices("A) Simple", "B) Complicated", "C) Straightforward", "D) Unclear"), 1));
        questions.add(new Question("Choose the word that best completes the sentence: 'Her _______ performance in the play earned her high praise from critics.'", createChoices("A) Mediocre", "B) Subpar", "C) Exceptional", "D) Unsatisfactory"), 2));
        questions.add(new Question("What is the meaning of the word 'Skeptical'?", createChoices("A) Trusting", "B) Doubtful", "C) Optimistic", "D) Assured"), 1));
        questions.add(new Question("Select the word that is most similar to 'Sublime':", createChoices("A) Mediocre", "B) Magnificent", "C) Ordinary", "D) Average"), 1));
        questions.add(new Question("What is the meaning of the word 'Wary'?", createChoices("A) Cautious", "B) Careless", "C) Trustful", "D) Unconcerned"), 0));
        questions.add(new Question("Choose the word that best completes the sentence: 'The artist's _______ approach to painting led to many unique and original works.'", createChoices("A) Conventional", "B) Innovative", "C) Standard", "D) Traditional"), 1));
        questions.add(new Question("What is the meaning of the word 'Futile'?", createChoices("A) Productive", "B) Effective", "C) Pointless", "D) Useful"), 2));
        questions.add(new Question("Select the antonym for 'Nimble':", createChoices("A) Quick", "B) Agile", "C) Slow", "D) Graceful"), 2));
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
