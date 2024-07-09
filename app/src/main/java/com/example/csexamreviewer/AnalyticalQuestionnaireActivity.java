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

public class AnalyticalQuestionnaireActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

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
        questions.add(new Question("A project has a budget of $50,000. After completing 60% of the project, $30,000 has been spent. How much of the budget is remaining?", createChoices("A. $10,000", "B. $15,000", "C. $20,000", "D. $25,000"), 0));
        questions.add(new Question("If a team of 8 people can complete a task in 6 days, how many days will it take for 12 people to complete the same task?", createChoices("A. 3 days", "B. 4 days", "C. 2 days", "D. 5 days"), 2));
        questions.add(new Question("A company's profit increased by 25% from last year to this year. If the profit last year was $80,000, what is the profit this year?", createChoices("A. $90,000", "B. $100,000", "C. $110,000", "D. $120,000"), 1));
        questions.add(new Question("If an item is priced at $80 and is discounted by 20%, what is the final price of the item?", createChoices("A. $60", "B. $64", "C. $68", "D. $70"), 2));
        questions.add(new Question("In a survey, 60% of the respondents preferred Option A over Option B. If 300 people participated in the survey, how many people preferred Option A?", createChoices("A. 120", "B. 160", "C. 180", "D. 200"), 2));
        questions.add(new Question("If a product's price is increased by 15% and the new price is $115, what was the original price of the product?", createChoices("A. $100", "B. $105", "C. $110", "D. $120"), 1));
        questions.add(new Question("A company's revenue increased by 10% from Quarter 1 to Quarter 2. If the revenue in Quarter 1 was $150,000, what was the revenue in Quarter 2?", createChoices("A. $160,000", "B. $165,000", "C. $170,000", "D. $175,000"), 1));
        questions.add(new Question("If a box contains 24 red balls, 36 blue balls, and 48 green balls, what is the ratio of red balls to the total number of balls in the box?", createChoices("A. 1:2", "B. 1:3", "C. 1:4", "D. 1:5"), 2));
        questions.add(new Question("A company's expenses were $120,000, and its revenue was $200,000. What is the profit margin as a percentage?", createChoices("A. 30%", "B. 40%", "C. 50%", "D. 60%"), 1));
        questions.add(new Question("If a project has 5 phases, and each phase is estimated to take 10 days to complete, how long will the entire project take to finish?", createChoices("A. 35 days", "B. 40 days", "C. 50 days", "D. 60 days"), 2));
        questions.add(new Question("A company's sales increased by 15% from Quarter 3 to Quarter 4. If the sales in Quarter 3 were $300,000, what was the sales in Quarter 4?", createChoices("A. $315,000", "B. $345,000", "C. $360,000", "D. $375,000"), 1));
        questions.add(new Question("If an investment doubles in value in 5 years, what is the annual growth rate of the investment?", createChoices("A. 10%", "B. 15%", "C. 20%", "D. 25%"), 1));
        questions.add(new Question("A project requires 200 hours of work. If 5 team members work on the project, how many hours will it take to complete the project?", createChoices("A. 30 hours", "B. 40 hours", "C. 50 hours", "D. 60 hours"), 1));
        questions.add(new Question("If a company's market share increased from 20% to 25%, what is the percentage increase in market share?", createChoices("A. 15%", "B. 20%", "C. 25%", "D. 30%"), 1));
        questions.add(new Question("A team completes a project in 10 days working 6 hours a day. If the team increases their daily work hours to 8 hours, how many days will it take to complete the same project?", createChoices("A. 6 days", "B. 7.5 days", "C. 8 days", "D. 9 days"), 1));
        questions.add(new Question("If a company's net income is $80,000 and its total assets are $400,000, what is the return on assets (ROA) as a percentage?", createChoices("A. 10%", "B. 20%", "C. 25%", "D. 30%"), 1));
        questions.add(new Question("A company's expenses were $150,000, and its revenue was $300,000. What is the profit margin as a percentage?", createChoices("A. 30%", "B. 50%", "C. 60%", "D. 70%"), 1));
        questions.add(new Question("If a store sells a product for $50 with a 20% profit margin, what is the cost price of the product?", createChoices("A. $30", "B. $40", "C. $45", "D. $48"), 1));
        questions.add(new Question("A project requires a budget of $80,000. If 60% of the budget has been spent, how much money has been spent on the project?", createChoices("A. $40,000", "B. $48,000", "C. $52,000", "D. $60,000"), 1));
        questions.add(new Question("If a company's revenue is $400,000 and its expenses are $250,000, what is the profit margin as a percentage?", createChoices("A. 30%", "B. 35%", "C. 40%", "D. 45%"), 1));
        questions.add(new Question("A project has a budget of $100,000. If the project is 40% complete and $45,000 has been spent, what percentage of the budget has been utilized?", createChoices("A. 45%", "B. 50%", "C. 55%", "D. 60%"), 1));
        questions.add(new Question("If a team can complete a task in 8 hours working together, and one team member can complete the task in 12 hours alone, how many hours will it take for the other team member to complete the task alone?", createChoices("A. 18 hours", "B. 24 hours", "C. 32 hours", "D. 36 hours"), 2));
        questions.add(new Question("A company's revenue increased by 20% from last year to this year. If the revenue last year was $500,000, what is the revenue this year?", createChoices("A. $550,000", "B. $600,000", "C. $650,000", "D. $700,000"), 1));
        questions.add(new Question("If an item is priced at $120 and is discounted by 25%, what is the final price of the item?", createChoices("A. $80", "B. $90", "C. $100", "D. $110"), 2));
        questions.add(new Question("In a survey, 70% of the respondents preferred Option A over Option B. If 400 people participated in the survey, how many people preferred Option A?", createChoices("A. 240", "B. 260", "C. 280", "D. 300"), 2));
        questions.add(new Question("If an investment grows by 10% annually, how many years will it take for the investment to double in value?", createChoices("A) 6 years", "B) 7 years", "C) 8 years", "D) 9 years"), 1));
        questions.add(new Question("A project requires 150 hours of work. If 3 team members work on the project, how many hours will it take to complete the project?", createChoices("A) 40 hours", "B) 50 hours", "C) 60 hours", "D) 70 hours"), 1));
        questions.add(new Question("If a company's market share increased from 30% to 35%, what is the percentage increase in market share?", createChoices("A) 10%", "B) 16.67%", "C) 20%", "D) 25%"), 1));
        questions.add(new Question("A team completes a project in 15 days working 5 hours a day. If the team increases their daily work hours to 6 hours, how many days will it take to complete the same project?", createChoices("A) 10 days", "B) 11 days", "C) 12 days", "D) 13 days"), 2));
        questions.add(new Question("If a company's net income is $100,000 and its total assets are $500,000, what is the return on assets (ROA) as a percentage?", createChoices("A) 15%", "B) 20%", "C) 25%", "D) 30%"), 1));
        questions.add(new Question("A company's sales increased by 20% from Quarter 2 to Quarter 3. If the sales in Quarter 2 were $400,000, what was the sales in Quarter 3?", createChoices("A) $440,000", "B) $480,000", "C) $520,000", "D) $560,000"), 1));
        questions.add(new Question("If an investment triples in value in 10 years, what is the annual growth rate of the investment?", createChoices("A) 15%", "B) 20%", "C) 25%", "D) 30%"), 1));
        questions.add(new Question("A project requires 250 hours of work. If 5 team members work on the project, how many hours will it take to complete the project?", createChoices("A) 40 hours", "B) 50 hours", "C) 60 hours", "D) 70 hours"), 1));
        questions.add(new Question("If a company's market share increased from 25% to 30%, what is the percentage increase in market share?", createChoices("A) 15%", "B) 20%", "C) 25%", "D) 30%"), 1));
        questions.add(new Question("A team completes a project in 12 days working 4 hours a day. If the team increases their daily work hours to 5 hours, how many days will it take to complete the same project?", createChoices("A) 8 days", "B) 9 days", "C) 10 days", "D) 11 days"), 2));
        questions.add(new Question("If a company's net income is $120,000 and its total assets are $600,000, what is the return on assets (ROA) as a percentage?", createChoices("A) 15%", "B) 20%", "C) 25%", "D) 30%"), 1));
        questions.add(new Question("A company's expenses were $200,000, and its revenue was $400,000. What is the profit margin as a percentage?", createChoices("A) 30%", "B) 50%", "C) 60%", "D) 70%"), 1));
        questions.add(new Question("If a store sells a product for $60 with a 30% profit margin, what is the cost price of the product?", createChoices("A) $30", "B) $40", "C) $45", "D) $48"), 1));
        questions.add(new Question("A project requires a budget of $120,000. If 70% of the budget has been spent, how much money has been spent on the project?", createChoices("A) $50,000", "B) $84,000", "C) $90,000", "D) $100,000"), 2));
        questions.add(new Question("If a company's revenue is $500,000 and its expenses are $300,000, what is the profit margin as a percentage?", createChoices("A) 30%", "B) 40%", "C) 50%", "D) 60%"), 1));
        questions.add(new Question("A project has a budget of $80,000. If the project is 50% complete and $40,000 has been spent, what percentage of the budget has been utilized?", createChoices("A) 40%", "B) 50%", "C) 60%", "D) 70%"), 1));
        questions.add(new Question("If a team can complete a task in 10 hours working together, and one team member can complete the task in 15 hours alone, how many hours will it take for the other team member to complete the task alone?", createChoices("A) 20 hours", "B) 30 hours", "C) 35 hours", "D) 40 hours"), 1));
        questions.add(new Question("A company's revenue increased by 25% from last year to this year. If the revenue last year was $400,000, what is the revenue this year?", createChoices("A) $450,000", "B) $500,000", "C) $550,000", "D) $600,000"), 1));
        questions.add(new Question("If an item is priced at $150 and is discounted by 30%, what is the final price of the item?", createChoices("A) $90", "B) $105", "C) $110", "D) $120"), 1));
        questions.add(new Question("In a survey, 80% of the respondents preferred Option A over Option B. If 500 people participated in the survey, how many people preferred Option A?", createChoices("A) 350", "B) 400", "C) 450", "D) 500"), 2));
        questions.add(new Question("If an investment grows by 5% annually, how many years will it take for the investment to double in value?", createChoices("A) 14 years", "B) 15 years", "C) 16 years", "D) 17 years"), 1));
        questions.add(new Question("A project requires 200 hours of work. If 4 team members work on the project, how many hours will it take to complete the project?", createChoices("A) 40 hours", "B) 50 hours", "C) 60 hours", "D) 70 hours"), 1));
        questions.add(new Question("If a company's market share increased from 20% to 25%, what is the percentage increase in market share?", createChoices("A) 15%", "B) 25%", "C) 30%", "D) 35%"), 1));
        questions.add(new Question("A team completes a project in 10 days working 6 hours a day. If the team increases their daily work hours to 7 hours, how many days will it take to complete the same project?", createChoices("A) 8 days", "B) 9 days", "C) 10 days", "D) 11 days"), 2));
        questions.add(new Question("If a company's net income is $150,000 and its total assets are $750,000, what is the return on assets (ROA) as a percentage?", createChoices("A) 15%", "B) 20%", "C) 25%", "D) 30%"), 1));
        questions.add(new Question("A company's sales increased by 15% from Quarter 1 to Quarter 2. If the sales in Quarter 1 were $400,000, what was the sales in Quarter 2?", createChoices("A) $430,000", "B) $460,000", "C) $490,000", "D) $520,000"), 1));
        questions.add(new Question("If an investment doubles in value in 8 years, what is the annual growth rate of the investment?", createChoices("A) 8%", "B) 9%", "C) 10%", "D) 11%"), 1));
        questions.add(new Question("A project requires 300 hours of work. If 6 team members work on the project, how many hours will it take to complete the project?", createChoices("A) 40 hours", "B) 50 hours", "C) 60 hours", "D) 70 hours"), 1));
        questions.add(new Question("If a company's market share increased from 30% to 35%, what is the percentage increase in market share?", createChoices("A) 15%", "B) 16.67%", "C) 20%", "D) 25%"), 1));
        questions.add(new Question("A team completes a project in 12 days working 5 hours a day. If the team increases their daily work hours to 6 hours, how many days will it take to complete the same project?", createChoices("A) 8 days", "B) 9 days", "C) 10 days", "D) 11 days"), 1));
        questions.add(new Question("If a company's net income is $120,000 and its total assets are $600,000, what is the return on assets (ROA) as a percentage?", createChoices("A) 15%", "B) 20%", "C) 25%", "D) 30%"), 1));
        questions.add(new Question("A company's expenses were $200,000, and its revenue was $400,000. What is the profit margin as a percentage?", createChoices("A) 30%", "B) 50%", "C) 60%", "D) 70%"), 1));
        questions.add(new Question("If a store sells a product for $60 with a 30% profit margin, what is the cost price of the product?", createChoices("A) $30", "B) $40", "C) $45", "D) $48"), 1));
        questions.add(new Question("A project requires a budget of $120,000. If 70% of the budget has been spent, how much money has been spent on the project?", createChoices("A) $50,000", "B) $84,000", "C) $90,000", "D) $100,000"), 1));
        questions.add(new Question("If a company's revenue is $500,000 and its expenses are $300,000, what is the profit margin as a percentage?", createChoices("A) 30%", "B) 40%", "C) 50%", "D) 60%"), 1));
        questions.add(new Question("A project has a budget of $100,000. If the project is 60% complete and $50,000 has been spent, what percentage of the budget has been utilized?", createChoices("A) 45%", "B) 50%", "C) 55%", "D) 60%"), 1));
        questions.add(new Question("If a team can complete a task in 12 hours working together, and one team member can complete the task in 18 hours alone, how many hours will it take for the other team member to complete the task alone?", createChoices("A) 24 hours", "B) 36 hours", "C) 48 hours", "D) 60 hours"), 1));
        questions.add(new Question("A company's revenue increased by 30% from last year to this year. If the revenue last year was $500,000, what is the revenue this year?", createChoices("A) $600,000", "B) $650,000", "C) $700,000", "D) $750,000"), 1));
        questions.add(new Question("If an item is priced at $120 and is discounted by 40%, what is the final price of the item?", createChoices("A) $60", "B) $72", "C) $80", "D) $90"), 1));
        questions.add(new Question("In a survey, 75% of the respondents preferred Option A over Option B. If 600 people participated in the survey, how many people preferred Option A?", createChoices("A) 400", "B) 450", "C) 450", "D) 500"), 2));
        questions.add(new Question("If an investment grows by 7% annually, how many years will it take for the investment to double in value?", createChoices("A) 10 years", "B) 10.5 years", "C) 11 years", "D) 12 years"), 1));
        questions.add(new Question("A project requires 250 hours of work. If 5 team members work on the project, how many hours will it take to complete the project?", createChoices("A) 40 hours", "B) 50 hours", "C) 60 hours", "D) 70 hours"), 1));
        questions.add(new Question("If a company's market share increased from 20% to 25%, what is the percentage increase in market share?", createChoices("A) 15%", "B) 25%", "C) 30%", "D) 35%"), 1));
        questions.add(new Question("A team completes a project in 12 days working 5 hours a day. If the team increases their daily work hours to 6 hours, how many days will it take to complete the same project?", createChoices("A) 8 days", "B) 9 days", "C) 10 days", "D) 11 days"), 1));
        questions.add(new Question("If a company's net income is $150,000 and its total assets are $750,000, what is the return on assets (ROA) as a percentage?", createChoices("A) 10%", "B) 20%", "C) 30%", "D) 40%"), 1));
        questions.add(new Question("A company's sales increased by 25% from Quarter 3 to Quarter 4. If the sales in Quarter 3 were $500,000, what was the sales in Quarter 4?", createChoices("A) $600,000", "B) $625,000", "C) $650,000", "D) $675,000"), 1));
        questions.add(new Question("If an investment triples in value in 15 years, what is the annual growth rate of the investment?", createChoices("A) 10%", "B) 15%", "C) 20%", "D) 25%"), 1));
        questions.add(new Question("A project requires 300 hours of work. If 6 team members work on the project, how many hours will it take to complete the project?", createChoices("A) 40 hours", "B) 50 hours", "C) 60 hours", "D) 70 hours"), 1));
        questions.add(new Question("If a company's market share increased from 20% to 25%, what is the percentage increase in market share?", createChoices("A) 15%", "B) 25%", "C) 30%", "D) 35%"), 1));
        questions.add(new Question("A team completes a project in 12 days working 5 hours a day. If the team increases their daily work hours to 6 hours, how many days will it take to complete the same project?", createChoices("A) 8 days", "B) 9 days", "C) 10 days", "D) 11 days"), 1));
        questions.add(new Question("If a company's net income is $120,000 and its total assets are $600,000, what is the return on assets (ROA) as a percentage?", createChoices("A. 15%", "B. 20%", "C. 25%", "D. 30%"), 1));
        questions.add(new Question("A company's expenses were $200,000, and its revenue was $400,000. What is the profit margin as a percentage?", createChoices("A. 30%", "B. 50%", "C. 60%", "D. 70%"), 1));
        questions.add(new Question("If a store sells a product for $60 with a 30% profit margin, what is the cost price of the product?", createChoices("A. $30", "B. $40", "C. $45", "D. $48"), 1));
        questions.add(new Question("A project requires a budget of $120,000. If 70% of the budget has been spent, how much money has been spent on the project?", createChoices("A. $50,000", "B. $84,000", "C. $90,000", "D. $100,000"), 1));
        questions.add(new Question("If a company's revenue is $500,000 and its expenses are $300,000, what is the profit margin as a percentage?", createChoices("A. 30%", "B. 40%", "C. 50%", "D. 60%"), 1));
        questions.add(new Question("A factory produces 500 units per day. If the production is increased by 20%, how many units are produced per day after the increase?", createChoices("A. 600", "B. 700", "C. 800", "D. 900"), 1));
        questions.add(new Question("If the price of an item is reduced by 25% and then increased by 20%, what is the net percentage change in the price?", createChoices("A. 5% decrease", "B. 5% increase", "C. 10% decrease", "D. 10% increase"), 1));
        questions.add(new Question("A company's revenue increased by 15% from last year to this year. If the revenue last year was $400,000, what is the revenue this year?", createChoices("A. $430,000", "B. $460,000", "C. $490,000", "D. $520,000"), 1));
        questions.add(new Question("If an item is initially priced at $100 and is then discounted by 20%, what is the final price of the item?", createChoices("A. $80", "B. $80", "C. $90", "D. $100"), 1));
        questions.add(new Question("In a survey, 60% of the respondents preferred Option A over Option B. If 300 people participated in the survey, how many people preferred Option A?", createChoices("A. 150", "B. 180", "C. 180", "D. 200"), 1));
        questions.add(new Question("If an investment grows by 6% annually, how many years will it take for the investment to double in value?", createChoices("A. 10 years", "B. 11 years", "C. 12 years", "D. 13 years"), 1));
        questions.add(new Question("A project requires 200 hours of work. If 4 team members work on the project, how many hours will it take to complete the project?", createChoices("A. 40 hours", "B. 50 hours", "C. 60 hours", "D. 70 hours"), 1));
        questions.add(new Question("If a company's market share increased from 15% to 20%, what is the percentage increase in market share?", createChoices("A. 15%", "B. 25%", "C. 30%", "D. 35%"), 1));
        questions.add(new Question("A team completes a project in 8 days working 5 hours a day. If the team increases their daily work hours to 6 hours, how many days will it take to complete the same project?", createChoices("A. 6 days", "B. 7 days", "C. 8 days", "D. 9 days"), 1));
        questions.add(new Question("If a company's net income is $100,000 and its total assets are $500,000, what is the return on assets (ROA) as a percentage?", createChoices("A. 10%", "B. 20%", "C. 25%", "D. 30%"), 1));
        questions.add(new Question("A company's sales increased by 20% from Quarter 2 to Quarter 3. If the sales in Quarter 2 were $300,000, what was the sales in Quarter 3?", createChoices("A. $330,000", "B. $360,000", "C. $390,000", "D. $420,000"), 1));
        questions.add(new Question("If an investment triples in value in 12 years, what is the annual growth rate of the investment?", createChoices("A. 10%", "B. 15%", "C. 20%", "D. 25%"), 1));
        questions.add(new Question("A project requires 250 hours of work. If 5 team members work on the project, how many hours will it take to complete the project?", createChoices("A. 40 hours", "B. 50 hours", "C. 60 hours", "D. 70 hours"), 1));
        questions.add(new Question("If a company's market share increased from 15% to 20%, what is the percentage increase in market share?", createChoices("A. 15%", "B. 25%", "C. 30%", "D. 35%"), 1));
        questions.add(new Question("A team completes a project in 12 days working 5 hours a day. If the team increases their daily work hours to 6 hours, how many days will it take to complete the same project?", createChoices("A. 8 days", "B. 9 days", "C. 10 days", "D. 11 days"), 1));
        questions.add(new Question("If a company's net income is $120,000 and its total assets are $600,000, what is the return on assets (ROA) as a percentage?", createChoices("A) 15%", "B) 20%", "C) 25%", "D) 30%"), 1));
        questions.add(new Question("A company's expenses were $200,000, and its revenue was $400,000. What is the profit margin as a percentage?", createChoices("A) 30%", "B) 50%", "C) 60%", "D) 70%"), 1));
        questions.add(new Question("If a store sells a product for $60 with a 30% profit margin, what is the cost price of the product?", createChoices("A) $30", "B) $40", "C) $45", "D) $48"), 1));
        questions.add(new Question("A project requires a budget of $120,000. If 70% of the budget has been spent, how much money has been spent on the project?", createChoices("A) $50,000", "B) $84,000", "C) $90,000", "D) $100,000"), 1));
        questions.add(new Question("If a company's revenue is $500,000 and its expenses are $300,000, what is the profit margin as a percentage?", createChoices("A) 30%", "B) 40%", "C) 50%", "D) 60%"), 1)); 
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
