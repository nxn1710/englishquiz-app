package com.englishquiz.model;

public class Pre_Exercise {
    private String id;
    private String exercise;
    private String user;
    private String score;

    public Pre_Exercise() {
    }

    public Pre_Exercise(String id, String exercise, String user, String score) {
        this.id = id;
        this.exercise = exercise;
        this.user = user;
        this.score = score;
    }

    public Pre_Exercise(String exercise, String user, String score) {
        this.exercise = exercise;
        this.user = user;
        this.score = score;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Pre_Exercise{" +
                "id='" + id + '\'' +
                ", exercise='" + exercise + '\'' +
                ", user='" + user + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
