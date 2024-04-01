package com.example.csexamreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class ClericalDiscussionActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerical_discussion);

        // Initialize views
        TextView discussionTextView = findViewById(R.id.discussionTextView);
        Button takeQuestionnaireButton = findViewById(R.id.takeQuestionnaireButton);
        ImageView textToSpeechButton = findViewById(R.id.textToSpeechButton);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, this);

        // Display discussion text
        String discussionText = getString(R.string.gen_info_discussion_text); // Fetch discussion text from resources
        discussionTextView.setText(discussionText);

        // Set click listener for the take questionnaire button
        takeQuestionnaireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the questionnaire activity
                Intent intent = new Intent(ClericalDiscussionActivity.this, ClericalQuestionnaireActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for the text to speech button
        textToSpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Speak the discussion text
                textToSpeech.speak(discussionText, TextToSpeech.QUEUE_FLUSH, null);
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
