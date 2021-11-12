package com.englishquiz.model;

public class Ranking {
    private String id;
    private String user;
    private String position;
    private String score;

    public Ranking() {
    }

    public Ranking(String user, String position, String score) {
        this.user = user;
        this.position = position;
        this.score = score;
    }

    public Ranking(String id, String user, String position, String score) {
        this.id = id;
        this.user = user;
        this.position = position;
        this.score = score;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", position='" + position + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
