package com.example.csexamreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfessionalCategoryActivity extends AppCompatActivity {

    ListView subjectListView;
    String[] subjects = {"Verbal Ability", "Numerical Ability", "Analytical Ability", "General Information"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_category);

        subjectListView = findViewById(R.id.subjectListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_button, subjects);
        subjectListView.setAdapter(adapter);

        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle subject selection
                String selectedSubject = subjects[position];
                // Start corresponding activity based on selected subject
                if (selectedSubject.equals("Verbal Ability")) {
                    Intent intent = new Intent(ProfessionalCategoryActivity.this, VerbalDiscussionActivity.class);
                    startActivity(intent);
                } else if (selectedSubject.equals("Numerical Ability")) {
                    Intent intent = new Intent(ProfessionalCategoryActivity.this, NumericalDiscussionActivity.class);
                    startActivity(intent);
                } else if (selectedSubject.equals("Analytical Ability")) {
                    Intent intent = new Intent(ProfessionalCategoryActivity.this, AnalyticalDiscussionActivity.class);
                    startActivity(intent);
                } else if (selectedSubject.equals("General Information")) {
                    Intent intent = new Intent(ProfessionalCategoryActivity.this, ClericalDiscussionActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

