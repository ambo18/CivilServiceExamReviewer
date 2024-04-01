package com.example.csexamreviewer;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextView questionTextView, choice1TextView, choice2TextView; // Add more TextViews for additional choices
    private Button textToSpeechButton;
    private TextToSpeech textToSpeech;
    private String questionContent, choice1Content, choice2Content; // Add more variables for additional choices

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionTextView = findViewById(R.id.questionTextView);
        choice1TextView = findViewById(R.id.choice1TextView);
        choice2TextView = findViewById(R.id.choice2TextView);
        textToSpeechButton = findViewById(R.id.textToSpeechButton);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, this);

        // Set question and choices content
        // For demonstration, setting sample content
        questionContent = "What is the capital of France?";
        choice1Content = "Paris";
        choice2Content = "London";

        // Set question and choices text
        questionTextView.setText(questionContent);
        choice1TextView.setText(choice1Content);
        choice2TextView.setText(choice2Content);

        // Set click listener for the text to speech button
        textToSpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Speak the question and choices content
                String textToSpeak = questionContent + ". " + choice1Content + ". " + choice2Content;
                textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set language to default locale
            int result = textToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or not supported, handle accordingly
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown TextToSpeech when the activity is destroyed
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}

