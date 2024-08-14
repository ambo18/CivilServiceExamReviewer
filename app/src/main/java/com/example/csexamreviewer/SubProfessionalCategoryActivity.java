package com.example.csexamreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class SubProfessionalCategoryActivity extends AppCompatActivity {

    ListView subjectListView;
    String[] subjects = {"Verbal Ability", "Numerical Ability", "General Information", "Clerical Ability"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_professional_category);

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
                    Intent intent = new Intent(SubProfessionalCategoryActivity.this, SubVerbalDiscussionActivity.class);
                    startActivity(intent);
                } else if (selectedSubject.equals("Numerical Ability")) {
                    Intent intent = new Intent(SubProfessionalCategoryActivity.this, SubNumericalDiscussionActivity.class);
                    startActivity(intent);
                } else if (selectedSubject.equals("General Information")) {
                    Intent intent = new Intent(SubProfessionalCategoryActivity.this, SubGenInfoDiscussionActivity.class);
                    startActivity(intent);
                } else if (selectedSubject.equals("Clerical Ability")) {
                    Intent intent = new Intent(SubProfessionalCategoryActivity.this, SubClericalDiscussionActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

