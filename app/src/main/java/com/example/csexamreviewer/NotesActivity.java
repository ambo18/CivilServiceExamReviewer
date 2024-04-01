package com.example.csexamreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class NotesActivity extends AppCompatActivity {

    ListView subjectListView;
    String[] subjects = {"Professional", "Sub Professional"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        subjectListView = findViewById(R.id.subjectListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjects);
        subjectListView.setAdapter(adapter);

        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle subject selection
                String selectedSubject = subjects[position];
                // Start activity to display notes for selected subject
                Intent intent = new Intent(NotesActivity.this, NotesDetailActivity.class);
                intent.putExtra("subject", selectedSubject);
                startActivity(intent);
            }
        });
    }
}

