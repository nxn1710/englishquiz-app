package com.englishquiz.model;

public class Answer_done {
    private String id;
    private String pre_exercise;
    private String user_ans;
    private String question;

    public Answer_done() {
    }

    public Answer_done(String id, String pre_exercise, String user_ans, String question) {
        this.id = id;
        this.pre_exercise = pre_exercise;
        this.user_ans = user_ans;
        this.question = question;
    }

    public Answer_done(String pre_exercise, String user_ans, String question) {
        this.pre_exercise = pre_exercise;
        this.user_ans = user_ans;
        this.question = question;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPre_exercise() {
        return pre_exercise;
    }

    public void setPre_exercise(String pre_exercise) {
        this.pre_exercise = pre_exercise;
    }

    public String getUser_ans() {
        return user_ans;
    }

    public void setUser_ans(String user_ans) {
        this.user_ans = user_ans;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    @Override
    public String toString() {
        return "Answer_done{" +
                "id='" + id + '\'' +
                ", pre_exercise='" + pre_exercise + '\'' +
                ", user_ans='" + user_ans + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}
