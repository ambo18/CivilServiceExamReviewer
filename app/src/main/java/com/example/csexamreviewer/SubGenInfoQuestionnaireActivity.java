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

public class SubGenInfoQuestionnaireActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

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
        questions.add(new Question("Who wrote the novel 'Harry Potter and the Philosopher's Stone'?", createChoices("A. J.K. Rowling", "B. Stephen King", "C. Dan Brown", "D. George R.R. Martin"), 0));
        questions.add(new Question("What is the currency of the United Kingdom?", createChoices("A. Dollar", "B. Euro", "C. Pound", "D. Yen"), 2));
        questions.add(new Question("Which country is known as the 'Land of the Midnight Sun'?", createChoices("A. Norway", "B. Sweden", "C. Finland", "D. Iceland"), 0));
        questions.add(new Question("Who painted the famous artwork 'The Scream'?", createChoices("A. Vincent van Gogh", "B. Pablo Picasso", "C. Edvard Munch", "D. Salvador Dali"), 2));
        questions.add(new Question("What is the largest desert in the world?", createChoices("A. Sahara Desert", "B. Gobi Desert", "C. Kalahari Desert", "D. Arabian Desert"), 0));
        questions.add(new Question("Choose the correct sentence:", createChoices("A. She is going to the store.", "B. She are going to the store.", "C. She am going to the store.", "D. She be going to the store."), 0));
        questions.add(new Question("Which word is a synonym for 'brave'?", createChoices("A. Cowardly", "B. Fearful", "C. Courageous", "D. Timid"), 2));
        questions.add(new Question("What is the plural form of 'child'?", createChoices("A. Childs", "B. Childes", "C. Children", "D. Child's"), 2));
        questions.add(new Question("What is the result of 8 x 7 ÷ 4?", createChoices("A. 14", "B. 15", "C. 16", "D. 17"), 1));
        questions.add(new Question("If a bookshelf has 5 shelves, and each shelf can hold 10 books, how many books can the bookshelf hold in total?", createChoices("A. 20", "B. 30", "C. 40", "D. 50"), 3));
        questions.add(new Question("What is the capital of the Philippines?", createChoices("A. Cebu City", "B. Davao City", "C. Manila", "D. Quezon City"), 2));
        questions.add(new Question("Who is the current President of the Philippines?", createChoices("A. Rodrigo Duterte", "B. Benigno Aquino III", "C. Gloria Macapagal-Arroyo", "D. Ferdinand Marcos Jr."), 3));
        questions.add(new Question("Which country recently won the FIFA World Cup?", createChoices("A. Germany", "B. France", "C. Brazil", "D. Argentina"), 3));
        questions.add(new Question("What is the capital of Australia?", createChoices("A. Sydney", "B. Melbourne", "C. Canberra", "D. Brisbane"), 2));
        questions.add(new Question("Who is considered the Father of Filipino National Language?", createChoices("A. Andres Bonifacio", "B. Jose Rizal", "C. Emilio Aguinaldo", "D. Manuel L. Quezon"), 3));
        questions.add(new Question("What is the official language of the Philippines?", createChoices("A. English", "B. Filipino", "C. Spanish", "D. Mandarin"), 1));
        questions.add(new Question("When is Philippine Independence Day celebrated?", createChoices("A. June 12", "B. July 4", "C. May 1", "D. September 21"), 0));
        questions.add(new Question("Which Philippine national hero is known as the 'Sublime Paralytic'?", createChoices("A. Apolinario Mabini", "B. Jose Rizal", "C. Andres Bonifacio", "D. Emilio Aguinaldo"), 0));
        questions.add(new Question("What is the highest mountain in the Philippines?", createChoices("A. Mount Halcon", "B. Mount Pulag", "C. Mount Apo", "D. Mount Mayon"), 2));
        questions.add(new Question("What is the name of the current Philippine Senate President?", createChoices("A. Francis Pangilinan", "B. Juan Miguel Zubiri", "C. Alan Peter Cayetano", "D. Koko Pimentel"), 1));
        questions.add(new Question("In which country is the city of Prague located?", createChoices("A. Austria", "B. Hungary", "C. Czech Republic", "D. Poland"), 2));
        questions.add(new Question("Who is the author of the book 'The Great Gatsby'?", createChoices("A. F. Scott Fitzgerald", "B. Ernest Hemingway", "C. Mark Twain", "D. Virginia Woolf"), 0));
        questions.add(new Question("Choose the correct sentence:", createChoices("A. They is going to the park.", "B. They are going to the park.", "C. They am going to the park.", "D. They be going to the park."), 1));
        questions.add(new Question("Which word is an antonym for 'fast'?", createChoices("A. Quick", "B. Slow", "C. Rapid", "D. Swift"), 1));
        questions.add(new Question("What is the comparative form of the adjective 'good'?", createChoices("A. Best", "B. Better", "C. Gooder", "D. Goodest"), 1));
        questions.add(new Question("What is the result of 15 + 6 x 2?", createChoices("A. 24", "B. 27", "C. 30", "D. 33"), 1));
        questions.add(new Question("If a box contains 24 chocolates and you take away 8, how many chocolates are left?", createChoices("A. 8", "B. 12", "C. 16", "D. 20"), 2));
        questions.add(new Question("What is the area of a rectangle with length 8 cm and width 4 cm?", createChoices("A. 24 cm²", "B. 30 cm²", "C. 32 cm²", "D. 40 cm²"), 2));
        questions.add(new Question("Who won the 2021 Nobel Peace Prize?", createChoices("A. Greta Thunberg", "B. Malala Yousafzai", "C. The World Food Programme", "D. Dr. Anthony Fauci"), 2));
        questions.add(new Question("Which country recently hosted the 2022 Winter Olympics?", createChoices("A. South Korea", "B. China", "C. Russia", "D. Canada"), 1));
        questions.add(new Question("Who is known as the 'Father of Modern Physics'?", createChoices("A. Isaac Newton", "B. Albert Einstein", "C. Galileo Galilei", "D. Nikola Tesla"), 1));
        questions.add(new Question("What is the largest organ in the human body?", createChoices("A. Liver", "B. Heart", "C. Brain", "D. Skin"), 3));
        questions.add(new Question("Which country is famous for the ancient monument Machu Picchu?", createChoices("A. Peru", "B. Mexico", "C. Brazil", "D. Chile"), 0));
        questions.add(new Question("Who wrote the play 'Hamlet'?", createChoices("A. William Shakespeare", "B. George Bernard Shaw", "C. Anton Chekhov", "D. Tennessee Williams"), 0));
        questions.add(new Question("What is the main ingredient in guacamole?", createChoices("A. Avocado", "B. Tomato", "C. Onion", "D. Garlic"), 0));
        questions.add(new Question("Choose the correct spelling:", createChoices("A. Embarrass", "B. Embarass", "C. Embaras", "D. Embarras"), 0));
        questions.add(new Question("Which word is a synonym for 'joyful'?", createChoices("A. Sad", "B. Happy", "C. Cheerful", "D. Mournful"), 2));
        questions.add(new Question("What is the past tense of 'eat'?", createChoices("A. Ate", "B. Eated", "C. Eat", "D. Eaten"), 0));
        questions.add(new Question("What is the result of 18 ÷ 3 x 2?", createChoices("A. 6", "B. 10", "C. 12", "D. 36"), 0));
        questions.add(new Question("If a box contains 36 marbles and you take away 12, how many marbles are left?", createChoices("A. 12", "B. 18", "C. 24", "D. 30"), 2));
        questions.add(new Question("What is the volume of a cube with a side length of 5 cm?", createChoices("A. 25 cm³", "B. 100 cm³", "C. 125 cm³", "D. 150 cm³"), 2));
        questions.add(new Question("Who is the current Prime Minister of India?", createChoices("A. Narendra Modi", "B. Rahul Gandhi", "C. Manmohan Singh", "D. Indira Gandhi"), 0));
        questions.add(new Question("Which country recently won the UEFA European Championship in 2020?", createChoices("A. Italy", "B. England", "C. Spain", "D. France"), 0));
        questions.add(new Question("Who won the 2021 Nobel Prize in Literature?", createChoices("A. Kazuo Ishiguro", "B. Louise Glück", "C. Bob Dylan", "D. Toni Morrison"), 1));
        questions.add(new Question("What is the chemical symbol for gold?", createChoices("A. Au", "B. Ag", "C. Fe", "D. Hg"), 0));
        questions.add(new Question("What is the process by which plants release oxygen called?", createChoices("A. Photosynthesis", "B. Respiration", "C. Decomposition", "D. Transpiration"), 0));
        questions.add(new Question("What is the unit of measurement for electric current?", createChoices("A. Watt", "B. Ampere", "C. Volt", "D. Ohm"), 1));
        questions.add(new Question("Who is the author of the play 'Romeo and Juliet'?", createChoices("A. William Shakespeare", "B. Charles Dickens", "C. Jane Austen", "D. Mark Twain"), 0));
        questions.add(new Question("What is the currency of Japan?", createChoices("A. Yen", "B. Euro", "C. Dollar", "D. Pound"), 0));
        questions.add(new Question("Which planet is known as the 'Morning Star'?", createChoices("A. Mars", "B. Venus", "C. Mercury", "D. Jupiter"), 1));
        questions.add(new Question("What is the capital city of Canada?", createChoices("A. Toronto", "B. Vancouver", "C. Ottawa", "D. Montreal"), 2));
        questions.add(new Question("Who wrote the novel 'Pride and Prejudice'?", createChoices("A. Charlotte Bronte", "B. Jane Austen", "C. Emily Dickinson", "D. Virginia Woolf"), 1));
        questions.add(new Question("Choose the correct spelling:", createChoices("A. Acommodate", "B. Accomodate", "C. Accommodate", "D. Acomodate"), 2));
        questions.add(new Question("Which word is a synonym for 'happy'?", createChoices("A. Sad", "B. Joyful", "C. Angry", "D. Tired"), 1));
        questions.add(new Question("What is the past tense of 'run'?", createChoices("A. Ran", "B. Runned", "C. Running", "D. Ranned"), 0));
        questions.add(new Question("What is 30% of 80?", createChoices("A. 15", "B. 20", "C. 25", "D. 30"), 1));
        questions.add(new Question("If a shirt costs $20 and is on sale for 25% off, what is the sale price?", createChoices("A. $15", "B. $18", "C. $21", "D. $22.50"), 0));
        questions.add(new Question("What is the next number in the sequence: 2, 4, 6, 8, __?", createChoices("A. 9", "B. 10", "C. 12", "D. 14"), 2));
        questions.add(new Question("Who is the current Secretary-General of the United Nations?", createChoices("A. Angela Merkel", "B. Antonio Guterres", "C. Justin Trudeau", "D. Emmanuel Macron"), 1));
        questions.add(new Question("Which country recently won the FIFA World Cup?", createChoices("A. Germany", "B. France", "C. Brazil", "D. Argentina"), 1));
        questions.add(new Question("What is the capital of Australia?", createChoices("A. Sydney", "B. Melbourne", "C. Canberra", "D. Brisbane"), 2));
        questions.add(new Question("Who is considered the Father of Filipino National Language?", createChoices("A. Andres Bonifacio", "B. Jose Rizal", "C. Emilio Aguinaldo", "D. Manuel L. Quezon"), 3));
        questions.add(new Question("What is the official language of the Philippines?", createChoices("A. English", "B. Filipino", "C. Spanish", "D. Mandarin"), 1));
        questions.add(new Question("When is Philippine Independence Day celebrated?", createChoices("A. June 12", "B. July 4", "C. May 1", "D. September 21"), 0));
        questions.add(new Question("Which Philippine national hero is known as the 'Sublime Paralytic'?", createChoices("A. Apolinario Mabini", "B. Jose Rizal", "C. Andres Bonifacio", "D. Emilio Aguinaldo"), 0));
        questions.add(new Question("What is the highest mountain in the Philippines?", createChoices("A. Mount Halcon", "B. Mount Pulag", "C. Mount Apo", "D. Mount Mayon"), 2));
        questions.add(new Question("Who is the current President of the Philippines?", createChoices("A. Rodrigo Duterte", "B. Benigno Aquino III", "C. Gloria Macapagal-Arroyo", "D. Ferdinand Marcos Jr."), 3));
        questions.add(new Question("Who is the Vice President of the Philippines?", createChoices("A. Leni Robredo", "B. Bongbong Marcos", "C. Manny Pacquiao", "D. Sara Duterte"), 3));
        questions.add(new Question("What is the current population of the Philippines?", createChoices("A. Approximately 110 million", "B. Approximately 120 million", "C. Approximately 130 million", "D. Approximately 140 million"), 0));
        questions.add(new Question("When is the next presidential election in the Philippines scheduled to take place?", createChoices("A. 2022", "B. 2024", "C. 2026", "D. 2028"), 1));
        questions.add(new Question("Who is the author of 'The Adventures of Tom Sawyer'?", createChoices("A. Mark Twain", "B. Charles Dickens", "C. J.K. Rowling", "D. George Orwell"), 0));
        questions.add(new Question("What is the currency of South Africa?", createChoices("A. Rand", "B. Peso", "C. Dinar", "D. Rupee"), 0));
        questions.add(new Question("Which country is known as the 'Land of the Rising Sun'?", createChoices("A. China", "B. South Korea", "C. Japan", "D. Thailand"), 2));
        questions.add(new Question("Who painted the famous artwork 'The Starry Night'?", createChoices("A. Vincent van Gogh", "B. Pablo Picasso", "C. Leonardo da Vinci", "D. Claude Monet"), 0));
        questions.add(new Question("What is the largest desert in the world?", createChoices("A. Sahara Desert", "B. Gobi Desert", "C. Kalahari Desert", "D. Arabian Desert"), 0));

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
