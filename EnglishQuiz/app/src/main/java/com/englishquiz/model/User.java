package com.englishquiz.model;

public class User {
    private String id;
    private String username;
    private String mail;
    private String password;
    private String score_max;
    private String first_name;
    private String last_name;
    private String national;
    private String career;

    public User() {
    }

    public User(String id, String username, String mail, String password, String score_max, String first_name, String last_name, String national, String career) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.score_max = score_max;
        this.first_name = first_name;
        this.last_name = last_name;
        this.national = national;
        this.career = career;
    }

    public User(String username, String mail, String password, String score_max, String first_name, String last_name, String national, String career) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.score_max = score_max;
        this.first_name = first_name;
        this.last_name = last_name;
        this.national = national;
        this.career = career;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScore_max() {
        return score_max;
    }

    public void setScore_max(String score_max) {
        this.score_max = score_max;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", score_max='" + score_max + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", national='" + national + '\'' +
                ", career='" + career + '\'' +
                '}';
    }
}
