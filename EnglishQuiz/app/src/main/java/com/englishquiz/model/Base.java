package com.englishquiz.model;

public class Base {
    private String id;
    private String score_min;
    private String score_max;
    private String certificate;
    private String type;
    private String ielts;

    public Base() {
    }

    public Base(String id, String score_min, String score_max, String certificate, String type) {
        this.id = id;
        this.score_min = score_min;
        this.score_max = score_max;
        this.certificate = certificate;
        this.type = type;
    }

    public Base(String id, String score_min, String score_max, String certificate, String type, String ielts) {
        this.id = id;
        this.score_min = score_min;
        this.score_max = score_max;
        this.certificate = certificate;
        this.type = type;
        this.ielts = ielts;
    }

    public String getScore_min() {
        return score_min;
    }

    public void setScore_min(String score_min) {
        this.score_min = score_min;
    }

    public String getScore_max() {
        return score_max;
    }

    public void setScore_max(String score_max) {
        this.score_max = score_max;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIelts() {
        return ielts;
    }

    public void setIelts(String ielts) {
        this.ielts = ielts;
    }

    @Override
    public String toString() {
        return "Base{" +
                "score_min='" + score_min + '\'' +
                ", score_max='" + score_max + '\'' +
                ", certificate='" + certificate + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
