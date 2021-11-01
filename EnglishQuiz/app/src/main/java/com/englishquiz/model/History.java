package com.englishquiz.model;

public class History {
    private String id;
    private String user;
    private String exercise;
    private String score;
    private String date;

    public History() {
    }

    public History(String id, String user, String exercise, String score, String date) {
        this.id = id;
        this.user = user;
        this.exercise = exercise;
        this.score = score;
        this.date = date;
    }

    public History(String user, String exercise, String score, String date) {
        this.user = user;
        this.exercise = exercise;
        this.score = score;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "History{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", exercise='" + exercise + '\'' +
                ", score='" + score + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
