package com.englishquiz.model;

import java.util.ArrayList;

public class Question {
    private String id;
    private String exercise;
    private String description;
    private ArrayList<Answer> listOfAnswer;
    private String time;

    public Question(String id, String exercise, String description, String time) {
        this.id = id;
        this.exercise = exercise;
        this.description = description;
        this.listOfAnswer = new ArrayList<>();
        this.time = time;
    }

    public Question(String id, String exercise, String description, ArrayList<Answer> listOfAnswer, String time) {
        this.id = id;
        this.exercise = exercise;
        this.description = description;
        this.listOfAnswer = listOfAnswer;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Answer> getListOfAnswer() {
        return listOfAnswer;
    }

    public void setListOfAnswer(ArrayList<Answer> listOfAnswer) {
        this.listOfAnswer = listOfAnswer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", exercise='" + exercise + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
