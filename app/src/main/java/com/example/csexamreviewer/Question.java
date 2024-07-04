package com.example.csexamreviewer;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> choices;
    private int correctAnswerIndex;

    public Question(String questionText, List<String> choices, int correctAnswerIndex) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

