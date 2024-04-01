package com.example.csexamreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class ExamActivity extends AppCompatActivity {

    ListView subjectListView;
    String[] subjects = {"Professional", "Sub Professional"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        subjectListView = findViewById(R.id.subjectListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjects);
        subjectListView.setAdapter(adapter);

        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle subject selection
                String selectedSubject = subjects[position];
                // Start corresponding activity based on selected subject
                if (selectedSubject.equals("Professional")) {
                    Intent intent = new Intent(ExamActivity.this, ProfessionalCategoryActivity.class);
                    startActivity(intent);
                } else if (selectedSubject.equals("Sub Professional")) {
                    Intent intent = new Intent(ExamActivity.this, SubProfessionalCategoryActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

