package com.example.csexamreviewer;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class NotesDetailActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextView notesTextView;
    private Button textToSpeechButton;
    private TextToSpeech textToSpeech;
    private String notesContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        notesTextView = findViewById(R.id.notesTextView);
        textToSpeechButton = findViewById(R.id.textToSpeechButton);

        // Retrieve the subject from intent
        String subject = getIntent().getStringExtra("subject");

        // Set notes content based on the selected subject
        notesContent = getNotesContentForSubject(subject);
        notesTextView.setText(notesContent);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, this);

        // Set click listener for the text to speech button
        textToSpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Speak the notes content
                textToSpeech.speak(notesContent, TextToSpeech.QUEUE_FLUSH, null);
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

    // Method to retrieve notes content based on the selected subject
    private String getNotesContentForSubject(String subject) {
        // Implement logic to retrieve notes content for the selected subject
        // For demonstration, returning sample notes content
        if (subject.equals("Professional")) {
            return "The Professional level of the Civil Service Exam is designed for individuals aspiring to qualify for first- and second-level positions within the Philippine government. Here’s a more detailed discussion:\n\nEligibility and Purpose:\nPassing the Professional level exam grants you Civil Service Eligibility, which is essential for employment in various government offices.\nThis level assesses candidates’ knowledge, skills, and abilities relevant to higher-ranking roles.\n\nScope of Examination:\nVerbal Ability: This section evaluates language proficiency, including grammar, vocabulary, and comprehension.\nNumerical Ability: Tests mathematical skills, covering topics like arithmetic, algebra, and data interpretation.\nAnalytical Ability: Focuses on problem-solving, logical reasoning, and critical thinking.\n\nPassing Rate:\nTo pass the Professional level, candidates must achieve a score of at least 80%.";
        } else if (subject.equals("Sub Professional")) {
            return "The Sub-Professional level is suitable for individuals aiming for first-level jobs such as crafts, clerical, trades, and custodial service positions. Here’s an overview:\n\nEligibility and Purpose:\nPassing the Sub-Professional level exam also grants you Civil Service Eligibility.\nThis level focuses on assessing basic skills relevant to entry-level government roles.\n\nScope of Examination:\nVerbal Ability: Similar to the Professional level, this section evaluates language skills.\nNumerical Ability: Covers basic math concepts.\nClerical Ability: Focuses on clerical operations, spelling, and related tasks.\n\nPassing Rate:\nCandidates must achieve a score of 80% to pass the Sub-Professional level.";
        }
        return "";
    }
}
