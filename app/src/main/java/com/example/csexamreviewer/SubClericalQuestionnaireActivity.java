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

import java.util.Locale;

public class SubClericalQuestionnaireActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    // Views
    private TextView questionTextView1, questionTextView2, questionTextView3, questionTextView4, 
    questionTextView5, questionTextView6, questionTextView7, questionTextView8, questionTextView9, 
    questionTextView10, questionTextView11, questionTextView12, questionTextView13, questionTextView14, questionTextView15, 
    questionTextView16, questionTextView17, questionTextView18, questionTextView19, questionTextView20;
    private RadioGroup choiceRadioGroup1, choiceRadioGroup2, choiceRadioGroup3, choiceRadioGroup4, choiceRadioGroup5,
    choiceRadioGroup6, choiceRadioGroup7, choiceRadioGroup8, choiceRadioGroup9, choiceRadioGroup10, choiceRadioGroup11,
    choiceRadioGroup12, choiceRadioGroup13, choiceRadioGroup14, choiceRadioGroup15, choiceRadioGroup16, choiceRadioGroup17,
    choiceRadioGroup18, choiceRadioGroup19, choiceRadioGroup20;
    private ImageView textToSpeechButton1, textToSpeechButton2, textToSpeechButton3, textToSpeechButton4, textToSpeechButton5, 
    textToSpeechButton6, textToSpeechButton7, textToSpeechButton8, textToSpeechButton9, textToSpeechButton10, textToSpeechButton11, textToSpeechButton12, textToSpeechButton13,
    textToSpeechButton14, textToSpeechButton15, textToSpeechButton16, textToSpeechButton17, textToSpeechButton18, textToSpeechButton19, textToSpeechButton20;
    private Button submitButton;
    // Text-to-speech
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_clerical);

        // Initialize views
        questionTextView1 = findViewById(R.id.questionTextView1);
        questionTextView2 = findViewById(R.id.questionTextView2);
        questionTextView3 = findViewById(R.id.questionTextView3);
        questionTextView4 = findViewById(R.id.questionTextView4);
        questionTextView5 = findViewById(R.id.questionTextView5);
        questionTextView6 = findViewById(R.id.questionTextView6);
        questionTextView7 = findViewById(R.id.questionTextView7);
        questionTextView8 = findViewById(R.id.questionTextView8);
        questionTextView9 = findViewById(R.id.questionTextView9);
        questionTextView10 = findViewById(R.id.questionTextView10);
        questionTextView11 = findViewById(R.id.questionTextView11);
        questionTextView12 = findViewById(R.id.questionTextView12);
        questionTextView13 = findViewById(R.id.questionTextView13);
        questionTextView14 = findViewById(R.id.questionTextView14);
        questionTextView15 = findViewById(R.id.questionTextView15);
        questionTextView16 = findViewById(R.id.questionTextView16);
        questionTextView17 = findViewById(R.id.questionTextView17);
        questionTextView18 = findViewById(R.id.questionTextView18);
        questionTextView19 = findViewById(R.id.questionTextView19);
        questionTextView20 = findViewById(R.id.questionTextView20);

        choiceRadioGroup1 = findViewById(R.id.choiceRadioGroup1);
        choiceRadioGroup2 = findViewById(R.id.choiceRadioGroup2);
        choiceRadioGroup3 = findViewById(R.id.choiceRadioGroup3);
        choiceRadioGroup4 = findViewById(R.id.choiceRadioGroup4);
        choiceRadioGroup5 = findViewById(R.id.choiceRadioGroup5);
        choiceRadioGroup6 = findViewById(R.id.choiceRadioGroup6);
        choiceRadioGroup7 = findViewById(R.id.choiceRadioGroup7);
        choiceRadioGroup8 = findViewById(R.id.choiceRadioGroup8);
        choiceRadioGroup9 = findViewById(R.id.choiceRadioGroup9);
        choiceRadioGroup10 = findViewById(R.id.choiceRadioGroup10);
        choiceRadioGroup11 = findViewById(R.id.choiceRadioGroup11);
        choiceRadioGroup12 = findViewById(R.id.choiceRadioGroup12);
        choiceRadioGroup13 = findViewById(R.id.choiceRadioGroup13);
        choiceRadioGroup14 = findViewById(R.id.choiceRadioGroup14);
        choiceRadioGroup15 = findViewById(R.id.choiceRadioGroup15);
        choiceRadioGroup16 = findViewById(R.id.choiceRadioGroup16);
        choiceRadioGroup17 = findViewById(R.id.choiceRadioGroup17);
        choiceRadioGroup18 = findViewById(R.id.choiceRadioGroup18);
        choiceRadioGroup19 = findViewById(R.id.choiceRadioGroup19);
        choiceRadioGroup20 = findViewById(R.id.choiceRadioGroup20);

        textToSpeechButton1 = findViewById(R.id.textToSpeechButton1);
        textToSpeechButton2 = findViewById(R.id.textToSpeechButton2);
        textToSpeechButton3 = findViewById(R.id.textToSpeechButton3);
        textToSpeechButton4 = findViewById(R.id.textToSpeechButton4);
        textToSpeechButton5 = findViewById(R.id.textToSpeechButton5);
        textToSpeechButton6 = findViewById(R.id.textToSpeechButton6);
        textToSpeechButton7 = findViewById(R.id.textToSpeechButton7);
        textToSpeechButton8 = findViewById(R.id.textToSpeechButton8);
        textToSpeechButton9 = findViewById(R.id.textToSpeechButton9);
        textToSpeechButton10 = findViewById(R.id.textToSpeechButton10);
        textToSpeechButton11 = findViewById(R.id.textToSpeechButton11);
        textToSpeechButton12 = findViewById(R.id.textToSpeechButton12);
        textToSpeechButton13 = findViewById(R.id.textToSpeechButton13);
        textToSpeechButton14 = findViewById(R.id.textToSpeechButton14);
        textToSpeechButton15 = findViewById(R.id.textToSpeechButton15);
        textToSpeechButton16 = findViewById(R.id.textToSpeechButton16);
        textToSpeechButton17 = findViewById(R.id.textToSpeechButton17);
        textToSpeechButton18 = findViewById(R.id.textToSpeechButton18);
        textToSpeechButton19 = findViewById(R.id.textToSpeechButton19);
        textToSpeechButton20 = findViewById(R.id.textToSpeechButton20);

        submitButton = findViewById(R.id.submitButton);

        // Initialize text-to-speech engine
        textToSpeech = new TextToSpeech(this, this);

        // Set onClick listeners
        textToSpeechButton1.setOnClickListener(view -> speakText(questionTextView1.getText().toString() + ". " + getChoicesText(choiceRadioGroup1)));
        textToSpeechButton2.setOnClickListener(view -> speakText(questionTextView2.getText().toString() + ". " + getChoicesText(choiceRadioGroup2)));
        textToSpeechButton3.setOnClickListener(view -> speakText(questionTextView3.getText().toString() + ". " + getChoicesText(choiceRadioGroup3)));
        textToSpeechButton4.setOnClickListener(view -> speakText(questionTextView4.getText().toString() + ". " + getChoicesText(choiceRadioGroup4)));
        textToSpeechButton5.setOnClickListener(view -> speakText(questionTextView5.getText().toString() + ". " + getChoicesText(choiceRadioGroup5)));
        textToSpeechButton6.setOnClickListener(view -> speakText(questionTextView6.getText().toString() + ". " + getChoicesText(choiceRadioGroup6)));
        textToSpeechButton7.setOnClickListener(view -> speakText(questionTextView7.getText().toString() + ". " + getChoicesText(choiceRadioGroup7)));
        textToSpeechButton8.setOnClickListener(view -> speakText(questionTextView8.getText().toString() + ". " + getChoicesText(choiceRadioGroup8)));
        textToSpeechButton9.setOnClickListener(view -> speakText(questionTextView9.getText().toString() + ". " + getChoicesText(choiceRadioGroup9)));
        textToSpeechButton10.setOnClickListener(view -> speakText(questionTextView10.getText().toString() + ". " + getChoicesText(choiceRadioGroup10)));
        textToSpeechButton11.setOnClickListener(view -> speakText(questionTextView11.getText().toString() + ". " + getChoicesText(choiceRadioGroup11)));
        textToSpeechButton12.setOnClickListener(view -> speakText(questionTextView12.getText().toString() + ". " + getChoicesText(choiceRadioGroup12)));
        textToSpeechButton13.setOnClickListener(view -> speakText(questionTextView13.getText().toString() + ". " + getChoicesText(choiceRadioGroup13)));
        textToSpeechButton14.setOnClickListener(view -> speakText(questionTextView14.getText().toString() + ". " + getChoicesText(choiceRadioGroup14)));
        textToSpeechButton15.setOnClickListener(view -> speakText(questionTextView15.getText().toString() + ". " + getChoicesText(choiceRadioGroup15)));
        textToSpeechButton16.setOnClickListener(view -> speakText(questionTextView16.getText().toString() + ". " + getChoicesText(choiceRadioGroup16)));
        textToSpeechButton17.setOnClickListener(view -> speakText(questionTextView17.getText().toString() + ". " + getChoicesText(choiceRadioGroup17)));
        textToSpeechButton18.setOnClickListener(view -> speakText(questionTextView18.getText().toString() + ". " + getChoicesText(choiceRadioGroup18)));
        textToSpeechButton19.setOnClickListener(view -> speakText(questionTextView19.getText().toString() + ". " + getChoicesText(choiceRadioGroup19)));
        textToSpeechButton20.setOnClickListener(view -> speakText(questionTextView20.getText().toString() + ". " + getChoicesText(choiceRadioGroup20)));

        submitButton.setOnClickListener(view -> showScoreDialog());
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

    private void speakText(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private String getChoicesText(RadioGroup radioGroup) {
        StringBuilder choicesText = new StringBuilder();
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            choicesText.append(radioButton.getText()).append(". ");
        }
        return choicesText.toString();
    }

    private void showScoreDialog() {
        int correctAnswers = 0;
    
        StringBuilder correctAnswersText = new StringBuilder();
    
        if (checkAnswer(choiceRadioGroup1, R.id.choiceRadioButton1B)) {
            correctAnswers++;
            correctAnswersText.append("1. B\n");
        } else {
            correctAnswersText.append("1. (Correct Answer: B)\n");
        }
    
        if (checkAnswer(choiceRadioGroup2, R.id.choiceRadioButton2C)) {
            correctAnswers++;
            correctAnswersText.append("2. C\n");
        } else {
            correctAnswersText.append("2. (Correct Answer:C)\n");
        }
    
        if (checkAnswer(choiceRadioGroup3, R.id.choiceRadioButton3B)) {
            correctAnswers++;
            correctAnswersText.append("3. B\n");
        } else {
            correctAnswersText.append("3. (Correct Answer: B)\n");
        }
    
        if (checkAnswer(choiceRadioGroup4, R.id.choiceRadioButton4B)) {
            correctAnswers++;
            correctAnswersText.append("4. B\n");
        } else {
            correctAnswersText.append("4. (Correct Answer: B)\n");
        }
    
        if (checkAnswer(choiceRadioGroup5, R.id.choiceRadioButton5B)) {
            correctAnswers++;
            correctAnswersText.append("5. B\n");
        } else {
            correctAnswersText.append("5. (Correct Answer: B)\n");
        }

        if (checkAnswer(choiceRadioGroup6, R.id.choiceRadioButton6C)) {
            correctAnswers++;
            correctAnswersText.append("6. C\n");
        } else {
            correctAnswersText.append("6. (Correct Answer: C)\n");
        }
    
        if (checkAnswer(choiceRadioGroup7, R.id.choiceRadioButton7C)) {
            correctAnswers++;
            correctAnswersText.append("7. C\n");
        } else {
            correctAnswersText.append("7. (Correct Answer: C)\n");
        }
    
        if (checkAnswer(choiceRadioGroup8, R.id.choiceRadioButton8C)) {
            correctAnswers++;
            correctAnswersText.append("8. C\n");
        } else {
            correctAnswersText.append("8. (Correct Answer: C)\n");
        }
    
        if (checkAnswer(choiceRadioGroup9, R.id.choiceRadioButton9B)) {
            correctAnswers++;
            correctAnswersText.append("9. B\n");
        } else {
            correctAnswersText.append("9. (Correct Answer: B)\n");
        }
    
        if (checkAnswer(choiceRadioGroup10, R.id.choiceRadioButton10B)) {
            correctAnswers++;
            correctAnswersText.append("10. B\n");
        } else {
            correctAnswersText.append("10. (Correct Answer: B)\n");
        }

        if (checkAnswer(choiceRadioGroup11, R.id.choiceRadioButton11A)) {
            correctAnswers++;
            correctAnswersText.append("11. A\n");
        } else {
            correctAnswersText.append("11. (Correct Answer: A)\n");
        }
        
        if (checkAnswer(choiceRadioGroup12, R.id.choiceRadioButton12A)) {
            correctAnswers++;
            correctAnswersText.append("12. A\n");
        } else {
            correctAnswersText.append("12. (Correct Answer: A)\n");
        }
        
        if (checkAnswer(choiceRadioGroup13, R.id.choiceRadioButton13B)) {
            correctAnswers++;
            correctAnswersText.append("13. B\n");
        } else {
            correctAnswersText.append("13. (Correct Answer: B)\n");
        }
        
        if (checkAnswer(choiceRadioGroup14, R.id.choiceRadioButton14B)) {
            correctAnswers++;
            correctAnswersText.append("14. B\n");
        } else {
            correctAnswersText.append("14. (Correct Answer: B)\n");
        }
        
        if (checkAnswer(choiceRadioGroup15, R.id.choiceRadioButton15A)) {
            correctAnswers++;
            correctAnswersText.append("15. A\n");
        } else {
            correctAnswersText.append("15. (Correct Answer: A)\n");
        }
        
        if (checkAnswer(choiceRadioGroup16, R.id.choiceRadioButton16A)) {
            correctAnswers++;
            correctAnswersText.append("16. A\n");
        } else {
            correctAnswersText.append("16. (Correct Answer: A)\n");
        }
        
        if (checkAnswer(choiceRadioGroup17, R.id.choiceRadioButton17A)) {
            correctAnswers++;
            correctAnswersText.append("17. A\n");
        } else {
            correctAnswersText.append("17. (Correct Answer: A)\n");
        }
        
        if (checkAnswer(choiceRadioGroup18, R.id.choiceRadioButton18B)) {
            correctAnswers++;
            correctAnswersText.append("18. B\n");
        } else {
            correctAnswersText.append("18. (Correct Answer: B)\n");
        }
        
        if (checkAnswer(choiceRadioGroup19, R.id.choiceRadioButton19A)) {
            correctAnswers++;
            correctAnswersText.append("19. A\n");
        } else {
            correctAnswersText.append("19. (Correct Answer: A)\n");
        }
        
        if (checkAnswer(choiceRadioGroup20, R.id.choiceRadioButton20D)) {
            correctAnswers++;
            correctAnswersText.append("20. D\n");
        } else {
            correctAnswersText.append("20. (Correct Answer: D)\n");
        }
    
        // Build the dialog message
        String dialogMessage = "Your score is " + correctAnswers + "/20\n\n";
        dialogMessage += "Correct Answers:\n" + correctAnswersText.toString();
    
        // Create and show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(dialogMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Return to previous activity
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean checkAnswer(RadioGroup radioGroup, int correctChoiceId) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        return selectedId == correctChoiceId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
