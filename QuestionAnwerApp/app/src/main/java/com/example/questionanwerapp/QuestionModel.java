package com.example.questionanwerapp;

public class QuestionModel {
    private String question, answerA, answerB, answerC, answerD, answerText, genText, holiText, strText;

    public QuestionModel(){}

    public QuestionModel(String question, String answerA, String answerB, String answerC, String answerD) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }

    public QuestionModel(String question, String answerText) {
        this.question = question;
        this.answerText = answerText;
    }

    public QuestionModel(String question, String genText, String holiText, String strText) {
        this.question = question;
        this.genText = genText;
        this.holiText = holiText;
        this.strText = strText;
    }

    public String getGenText() {
        return genText;
    }

    public void setGenText(String genText) {
        this.genText = genText;
    }

    public String getHoliText() {
        return holiText;
    }

    public void setHoliText(String holiText) {
        this.holiText = holiText;
    }

    public String getStrText() {
        return strText;
    }

    public void setStrText(String strText) {
        this.strText = strText;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
