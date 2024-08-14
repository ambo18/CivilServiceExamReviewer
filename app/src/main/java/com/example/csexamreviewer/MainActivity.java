package com.example.csexamreviewer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView subjectListView;
    String[] subjects = {"Professional", "Sub Professional"};
    Button sourcesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subjectListView = findViewById(R.id.subjectListView);
        sourcesButton = findViewById(R.id.sourcesButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjects);
        subjectListView.setAdapter(adapter);

        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubject = subjects[position];
                if (selectedSubject.equals("Professional")) {
                    Intent intent = new Intent(MainActivity.this, ProfessionalCategoryActivity.class);
                    startActivity(intent);
                } else if (selectedSubject.equals("Sub Professional")) {
                    Intent intent = new Intent(MainActivity.this, SubProfessionalCategoryActivity.class);
                    startActivity(intent);
                }
            }
        });

        // Set up button click to show the dialog with sources
        sourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSourcesDialog();
            }
        });
    }

    private void showSourcesDialog() {
        String[] sources = {
            "https://www.google.com.ph/books/edition/CSC_Exam_Secrets_Study_Guide/NhqvmQEACAAJ?hl=en",
            "https://play.google.com/store/apps/details?id=com.techcertapps.reviewer.csc",
            "https://play.google.com/store/apps/details?id=com.jsonmat.freecivilservicereviewer",
            "https://play.google.com/store/apps/details?id=com.adobodev.cscexamreviewer",
            "https://play.google.com/store/apps/details?id=com.iibfexam.quiz",
            "https://civil-service-exam-offline-reviewer.en.softonic.com/android",
            "https://www.teachpinas.com/civil-service-exam-cse-reviewers-with-answer-keys/#More_Civil_Service_Exam_%E2%80%93_CSE_Reviewers_2024",
            "https://www.teachpinas.com/download/cse-reviewer-170-items-with-answer-keys/",
            "https://www.teachpinas.com/tag/cse-reviewers/",
            "https://cse.unl.edu/~cbourkePDF",
            "https://cse.unl.edu",
            "https://www.freebookcentre.net/CompuScience/Free-Computer-Science-Books-Download.html",
            "https://www.teachpinas.com/civil-service-exam-cse-reviewers-with-answer-keys/",
            "https://filipiknow.net/civil-service-reviewer/",
            "https://www.csc.gov.ph/phocadownload/userupload/erpo/ppt/2016/Advisory/Examineesguide201607loosepage.pdf",
            "https://www.academia.edu/36335499/CIVIL_SERVICE_EXAMS_3_rd_Edition",
            "https://www.scribd.com/document/421172721/Civil-Service-Exam-by-Alimilsilongan",
            "https://pdfcoffee.com/download/civil-service-exam-complete-reviewer-philippines-pdf-free.html"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Question Sources");

        // Convert links into clickable format
        builder.setItems(sources, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle link clicks, you might want to open a browser or something similar
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
