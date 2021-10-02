package com.englishquiz.model;

public class Answer {
    private String id;
    private String question;
    private String description;
    private String is_correct;

    public Answer(String id, String question, String description, String is_correct) {
        this.id = id;
        this.question = question;
        this.description = description;
        this.is_correct = is_correct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(String is_correct) {
        this.is_correct = is_correct;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", description='" + description + '\'' +
                ", is_correct='" + is_correct + '\'' +
                '}';
    }
}
