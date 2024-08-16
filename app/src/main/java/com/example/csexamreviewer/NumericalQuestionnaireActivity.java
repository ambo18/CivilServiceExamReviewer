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

public class NumericalQuestionnaireActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

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
                Log.e("NumericalQuestionnaireActivity", "TextView with ID questionTextView" + i + " not found.");
            }
    
            int radioGroupResID = getResources().getIdentifier("choiceRadioGroup" + i, "id", getPackageName());
            RadioGroup radioGroup = findViewById(radioGroupResID);
            if (radioGroup != null) {
                choiceRadioGroups.add(radioGroup);
            } else {
                Log.e("NumericalQuestionnaireActivity", "RadioGroup with ID choiceRadioGroup" + i + " not found.");
            }
    
            int textToSpeechButtonResID = getResources().getIdentifier("textToSpeechButton" + i, "id", getPackageName());
            ImageView textToSpeechButton = findViewById(textToSpeechButtonResID);
            if (textToSpeechButton != null) {
                textToSpeechButtons.add(textToSpeechButton);
            } else {
                Log.e("NumericalQuestionnaireActivity", "ImageView with ID textToSpeechButton" + i + " not found.");
            }
        }
    
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is 35% of 240?", createChoices("A. 84", "B. 85", "C. 86", "D. 87"), 0));
        questions.add(new Question("If a car travels at an average speed of 60 miles per hour, how long will it take to travel 300 miles?", createChoices("A. 3 hours", "B. 4 hours", "C. 5 hours", "D. 6 hours"), 2));
        questions.add(new Question("Solve: 3.5 x 4.8", createChoices("A. 16.8", "B. 17.2", "C. 18.2", "D. 19.6"), 0));
        questions.add(new Question("If a company's revenue is $500,000 and its expenses are $350,000, what is the company's profit?", createChoices("A. $150,000", "B. $200,000", "C. $250,000", "D. $300,000"), 0));
        questions.add(new Question("What is the square root of 144?", createChoices("A. 10", "B. 12", "C. 14", "D. 16"), 1));
        questions.add(new Question("A book originally costs $40 and is on sale for 25% off. What is the sale price of the book?", createChoices("A. $30", "B. $32", "C. $35", "D. $38"), 0));
        questions.add(new Question("If a rectangle has a length of 10 cm and a width of 5 cm, what is its area?", createChoices("A. 25 sq cm", "B. 40 sq cm", "C. 50 sq cm", "D. 60 sq cm"), 2));
        questions.add(new Question("A company purchased 150 units of a product at $20 each. They sold all the units at $30 each. What is the total profit?", createChoices("A. $1,500", "B. $2,250", "C. $3,000", "D. $4,500"), 1));
        questions.add(new Question("If a store sells a shirt for $25 and makes a 20% profit, what is the cost price of the shirt?", createChoices("A. $18", "B. $20", "C. $21", "D. $22"), 1));
        questions.add(new Question("Solve: 2/5 + 3/4", createChoices("A. 11/20", "B. 13/20", "C. 7/10", "D. 3/2"), 0));
        questions.add(new Question("What is 15% of 400?", createChoices("A. 50", "B. 60", "C. 75", "D. 90"), 1));
        questions.add(new Question("If a train travels at 80 km/h for 3 hours, how far does it travel?", createChoices("A. 240 km", "B. 260 km", "C. 280 km", "D. 300 km"), 0));
        questions.add(new Question("Solve: 6.4 ÷ 0.8", createChoices("A. 7", "B. 8", "C. 9", "D. 10"), 1));
        questions.add(new Question("If a company's revenue is $800,000 and its expenses are $600,000, what is the company's profit margin?", createChoices("A. 20%", "B. 25%", "C. 30%", "D. 35%"), 0));
        questions.add(new Question("What is 5 squared?", createChoices("A. 20", "B. 25", "C. 30", "D. 35"), 1));
        questions.add(new Question("A shirt is on sale for 30% off its original price of $50. What is the sale price of the shirt?", createChoices("A. $35", "B. $40", "C. $45", "D. $50"), 0));
        questions.add(new Question("If a circle has a radius of 6 cm, what is its circumference?", createChoices("A. 12π cm", "B. 18π cm", "C. 24π cm", "D. 36π cm"), 2));
        questions.add(new Question("A company purchased 200 units of a product at $10 each. They sold all the units at $15 each. What is the total profit?", createChoices("A. $1,000", "B. $2,000", "C. $3,000", "D. $4,000"), 1));
        questions.add(new Question("If a store sells a dress for $80 and makes a 25% profit, what is the cost price of the dress?", createChoices("A. $60", "B. $64", "C. $68", "D. $72"), 0));
        questions.add(new Question("Solve: 3/4 - 1/2", createChoices("A. 1/4", "B. 1/3", "C. 1/2", "D. 2/3"), 0));
        questions.add(new Question("What is 40% of 500?", createChoices("A. 200", "B. 250", "C. 300", "D. 350"), 0));
        questions.add(new Question("If a car travels at an average speed of 70 kilometers per hour, how long will it take to travel 350 kilometers?", createChoices("A. 3 hours", "B. 4 hours", "C. 5 hours", "D. 6 hours"), 2));
        questions.add(new Question("Solve: 4.2 × 5.8", createChoices("A. 24.36", "B. 24.56", "C. 24.76", "D. 24.96"), 0));
        questions.add(new Question("If a company's revenue is $700,000 and its expenses are $450,000, what is the company's profit?", createChoices("A. $250,000", "B. $300,000", "C. $350,000", "D. $400,000"), 0));
        questions.add(new Question("What is the square root of 169?", createChoices("A. 10", "B. 12", "C. 13", "D. 14"), 2));
        questions.add(new Question("A book originally costs $50 and is on sale for 20% off. What is the sale price of the book?", createChoices("A. $35", "B. $40", "C. $45", "D. $48"), 1));
        questions.add(new Question("If a rectangle has a length of 12 cm and a width of 6 cm, what is its area?", createChoices("A. 36 sq cm", "B. 48 sq cm", "C. 60 sq cm", "D. 72 sq cm"), 3));
        questions.add(new Question("A company purchased 180 units of a product at $25 each. They sold all the units at $35 each. What is the total profit?", createChoices("A. $1,800", "B. $2,700", "C. $3,600", "D. $4,500"), 1));
        questions.add(new Question("If a store sells a shirt for $30 and makes a 25% profit, what is the cost price of the shirt?", createChoices("A. $20", "B. $24", "C. $25", "D. $28"), 1));
        questions.add(new Question("Solve: 3/4 + 2/3", createChoices("A. 5/7", "B. 11/12", "C. 5/6", "D. 1 1/12"), 1));
        questions.add(new Question("What is 25% of 400?", createChoices("A. 75", "B. 80", "C. 90", "D. 100"), 1));
        questions.add(new Question("If a train travels at 100 km/h for 4 hours, how far does it travel?", createChoices("A. 300 km", "B. 400 km", "C. 500 km", "D. 600 km"), 1));
        questions.add(new Question("Solve: 7.2 ÷ 0.9", createChoices("A. 8", "B. 9", "C. 10", "D. 11"), 0));
        questions.add(new Question("If a company's revenue is $1,000,000 and its expenses are $700,000, what is the company's profit margin?", createChoices("A. 20%", "B. 30%", "C. 40%", "D. 50%"), 1));
        questions.add(new Question("What is 6 squared?", createChoices("A. 30", "B. 36", "C. 42", "D. 48"), 1));
        questions.add(new Question("A shirt is on sale for 40% off its original price of $60. What is the sale price of the shirt?", createChoices("A. $30", "B. $36", "C. $42", "D. $48"), 1));
        questions.add(new Question("If a circle has a radius of 8 cm, what is its circumference?", createChoices("A. 16π cm", "B. 24π cm", "C. 32π cm", "D. 40π cm"), 2));
        questions.add(new Question("A company purchased 250 units of a product at $15 each. They sold all the units at $20 each. What is the total profit?", createChoices("A. $1,250", "B. $2,500", "C. $3,750", "D. $5,000"), 1));
        questions.add(new Question("If a store sells a dress for $100 and makes a 30% profit, what is the cost price of the dress?", createChoices("A. $65", "B. $70", "C. $75", "D. $80"), 2));
        questions.add(new Question("Solve: 4/5 - 1/3", createChoices("A. 1/15", "B. 1/2", "C. 7/15", "D. 17/15"), 2));
        questions.add(new Question("What is the square root of 144?", createChoices("A. 10", "B. 12", "C. 14", "D. 16"), 1));
        questions.add(new Question("If a car travels at an average speed of 60 miles per hour, how far will it travel in 3 hours?", createChoices("A. 120 miles", "B. 150 miles", "C. 180 miles", "D. 200 miles"), 2));
        questions.add(new Question("What is 25% of 200?", createChoices("A. 40", "B. 50", "C. 60", "D. 70"), 1));
        questions.add(new Question("If a rectangle has a length of 12 cm and a width of 6 cm, what is its area?", createChoices("A. 36 sq cm", "B. 48 sq cm", "C. 60 sq cm", "D. 72 sq cm"), 0));
        questions.add(new Question("What is the next number in the sequence: 3, 6, 12, 24, __?", createChoices("A. 36", "B. 48", "C. 54", "D. 60"), 1));
        questions.add(new Question("If a company's profit margin is 25% and its revenue is $200,000, what is the profit?", createChoices("A. $40,000", "B. $50,000", "C. $60,000", "D. $70,000"), 0));
        questions.add(new Question("Calculate the average of 15, 20, 25, and 30.", createChoices("A. 17.5", "B. 20", "C. 22.5", "D. 25"), 2));
        questions.add(new Question("What is the perimeter of a square with a side length of 8 cm?", createChoices("A. 24 cm", "B. 32 cm", "C. 40 cm", "D. 48 cm"), 1));
        questions.add(new Question("If a store sells a product for $80 with a 20% discount, what is the discounted price?", createChoices("A. $60", "B. $64", "C. $68", "D. $72"), 1));
        questions.add(new Question("A train travels 300 miles in 4 hours. What is the average speed of the train in miles per hour?", createChoices("A. 60 mph", "B. 70 mph", "C. 75 mph", "D. 80 mph"), 2));
        questions.add(new Question("If a recipe calls for 2 cups of sugar and you want to make 5 batches, how many cups of sugar do you need?", createChoices("A. 8", "B. 10", "C. 12", "D. 14"), 2));
        questions.add(new Question("What is the product of 18 and 7?", createChoices("A. 126", "B. 132", "C. 144", "D. 150"), 0));
        questions.add(new Question("Calculate the area of a circle with a radius of 6 cm.", createChoices("A. 18π sq cm", "B. 24π sq cm", "C. 36π sq cm", "D. 48π sq cm"), 2));
        questions.add(new Question("A company's expenses were $40,000 and its revenue was $60,000. What is the profit margin as a percentage?", createChoices("A. 30%", "B. 40%", "C. 50%", "D. 60%"), 1));
        questions.add(new Question("Find the median of the numbers 12, 18, 22, 27, and 34.", createChoices("A. 18", "B. 22", "C. 27", "D. 34"), 1));
        questions.add(new Question("If a box contains 36 pens and 4 pens are taken out, how many pens are left in the box?", createChoices("A. 28", "B. 30", "C. 32", "D. 34"), 2));
        questions.add(new Question("Solve the equation: 3x + 7 = 22.", createChoices("A. x = 5", "B. x = 6", "C. x = 7", "D. x = 8"), 0));
        questions.add(new Question("If a store sells a product for $120 at a 15% discount, what is the discounted price?", createChoices("A. $96", "B. $102", "C. $108", "D. $114"), 1));
        questions.add(new Question("Calculate the cube of 4.", createChoices("A. 64", "B. 72", "C. 80", "D. 96"), 0));
        questions.add(new Question("What is 40% of 250?", createChoices("A. 90", "B. 100", "C. 110", "D. 120"), 3));
        questions.add(new Question("If a car travels 360 miles in 6 hours, what is the average speed of the car in miles per hour?", createChoices("A. 50 mph", "B. 55 mph", "C. 60 mph", "D. 65 mph"), 2));
        questions.add(new Question("Calculate the average of 30, 40, 50, and 60.", createChoices("A. 35", "B. 40", "C. 45", "D. 50"), 1));
        questions.add(new Question("A rectangular box has dimensions of 10 cm x 5 cm x 3 cm. What is the volume of the box?", createChoices("A. 100 cm³", "B. 150 cm³", "C. 200 cm³", "D. 250 cm³"), 0));
        questions.add(new Question("What is the next number in the sequence: 4, 9, 16, 25, __?", createChoices("A. 30", "B. 36", "C. 40", "D. 49"), 3));
        questions.add(new Question("If a company's revenue is $500,000 and its expenses are $300,000, what is the profit margin as a percentage?", createChoices("A. 40%", "B. 50%", "C. 60%", "D. 70%"), 1));
        questions.add(new Question("Determine the mode of the numbers 12, 15, 18, 12, and 22.", createChoices("A. 12", "B. 15", "C. 18", "D. 22"), 0));
        questions.add(new Question("What is the perimeter of a rectangle with a length of 12 cm and a width of 8 cm?", createChoices("A. 28 cm", "B. 32 cm", "C. 36 cm", "D. 40 cm"), 2));
        questions.add(new Question("If a store sells a product for $75 with a 10% discount, what is the discounted price?", createChoices("A. $65", "B. $67.50", "C. $70", "D. $72.50"), 1));
        questions.add(new Question("A train travels 400 miles in 5 hours. What is the speed of the train in miles per hour?", createChoices("A. 75 mph", "B. 80 mph", "C. 85 mph", "D. 90 mph"), 1));
        questions.add(new Question("What is the area of a triangle with a base of 15 cm and a height of 10 cm?", createChoices("A. 75 sq cm", "B. 100 sq cm", "C. 125 sq cm", "D. 150 sq cm"), 1));
        questions.add(new Question("If a company's profit was $80,000 and its revenue was $400,000, what is the profit margin as a percentage?", createChoices("A. 15%", "B. 20%", "C. 25%", "D. 30%"), 1));
        questions.add(new Question("What is 15% of 180?", createChoices("A. 24", "B. 27", "C. 30", "D. 33"), 1));
        questions.add(new Question("A car travels 240 miles on 12 gallons of gas. What is the car's miles per gallon (MPG)?", createChoices("A. 15 mpg", "B. 18 mpg", "C. 20 mpg", "D. 22 mpg"), 2));
        questions.add(new Question("Calculate the sum of 45, 60, and 75.", createChoices("A. 150", "B. 165", "C. 180", "D. 195"), 1));
        questions.add(new Question("If a phone costs $600 and is on sale for 30% off, what is the sale price?", createChoices("A. $420", "B. $450", "C. $480", "D. $510"), 2));
        questions.add(new Question("Find the value of x in the equation 5x - 3 = 22.", createChoices("A. x = 5", "B. x = 6", "C. x = 7", "D. x = 8"), 1));
        questions.add(new Question("If a store sells a product for $150 with a 20% discount, what is the discounted price?", createChoices("A. $110", "B. $120", "C. $130", "D. $140"), 1));
        questions.add(new Question("Calculate the cube of 3.", createChoices("A. 24", "B. 27", "C. 30", "D. 33"), 1));
        questions.add(new Question("What is 35% of 280?", createChoices("A. 84", "B. 98", "C. 105", "D. 112"), 3));
        questions.add(new Question("What is the next number in the sequence: 6, 12, 24, 48, __?", createChoices("A. 64", "B. 72", "C. 96", "D. 100"), 2));
        questions.add(new Question("If a company's revenue is $300,000 and its expenses are $200,000, what is the profit margin as a percentage?", createChoices("A. 25%", "B. 33.33%", "C. 40%", "D. 50%"), 1));
        questions.add(new Question("Determine the median of the numbers 16, 20, 24, 28, and 32.", createChoices("A. 20", "B. 24", "C. 28", "D. 32"), 1));
        questions.add(new Question("What is the perimeter of a square with a side length of 10 cm?", createChoices("A. 30 cm", "B. 35 cm", "C. 40 cm", "D. 45 cm"), 2));
        questions.add(new Question("If a store sells a product for $90 with a 15% discount, what is the discounted price?", createChoices("A. $70", "B. $75", "C. $80", "D. $85"), 1));
        questions.add(new Question("Calculate the cube of 6.", createChoices("A. 216", "B. 225", "C. 234", "D. 243"), 0));
        questions.add(new Question("What is 45% of 240?", createChoices("A. 96", "B. 108", "C. 112", "D. 120"), 3));
        questions.add(new Question("A car travels 280 miles in 4 hours. What is the average speed of the car in miles per hour?", createChoices("A. 60 mph", "B. 65 mph", "C. 70 mph", "D. 75 mph"), 2));
        questions.add(new Question("Calculate the average of 25, 30, 35, and 40.", createChoices("A. 27.5", "B. 30", "C. 32.5", "D. 35"), 2));
        questions.add(new Question("If a rectangle has a length of 18 cm and a width of 12 cm, what is its area?", createChoices("A. 180 sq cm", "B. 200 sq cm", "C. 216 sq cm", "D. 240 sq cm"), 2));
        questions.add(new Question("If a company's profit was $120,000 and its revenue was $600,000, what is the profit margin as a percentage?", createChoices("A. 15%", "B. 20%", "C. 25%", "D. 30%"), 1));
        questions.add(new Question("What is 20% of 300?", createChoices("A. 40", "B. 50", "C. 60", "D. 70"), 1));
        questions.add(new Question("A car travels 200 miles on 8 gallons of gas. What is the car's miles per gallon (MPG)?", createChoices("A. 20 mpg", "B. 22.5 mpg", "C. 25 mpg", "D. 27.5 mpg"), 2));
        questions.add(new Question("Calculate the sum of 55, 65, and 75.", createChoices("A. 160", "B. 175", "C. 185", "D. 195"), 2));
        questions.add(new Question("If a store sells a product for $200 with a 25% discount, what is the discounted price?", createChoices("A. $140", "B. $150", "C. $160", "D. $170"), 1));
        questions.add(new Question("Find the value of x in the equation 2x + 5 = 17.", createChoices("A. x = 6", "B. x = 7", "C. x = 8", "D. x = 9"), 0));
        questions.add(new Question("If a rectangle has a length of 20 cm and a width of 15 cm, what is its perimeter?", createChoices("A. 50 cm", "B. 60 cm", "C. 70 cm", "D. 80 cm"), 3));
        questions.add(new Question("What is 30% of 240?", createChoices("A. 60", "B. 70", "C. 72", "D. 80"), 2));
        questions.add(new Question("Calculate the area of a circle with a radius of 8 cm.", createChoices("A. 64π sq cm", "B. 96π sq cm", "C. 128π sq cm", "D. 160π sq cm"), 0));
        questions.add(new Question("If a company's revenue is $400,000 and its expenses are $250,000, what is the profit margin as a percentage?", createChoices("A. 20%", "B. 30%", "C. 35%", "D. 40%"), 2));
        questions.add(new Question("What is the next number in the sequence: 10, 20, 40, 80, __?", createChoices("A. 100", "B. 120", "C. 160", "D. 200"), 2)); 
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
                Log.e("NumericalQuestionnaireActivity", "ImageView in textToSpeechButtons list is null at index " + finalI);
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
            Log.e("NumericalQuestionnaireActivity", "Invalid question index for scrolling: " + questionIndexOnPage);
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