package com.bootcamp.wellstudy.model;

import java.util.Date;

public class Score {
    private Student student;
    private String leson;
    private Integer mark;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Score(String leson, Integer mark, Date date) {
        this.leson = leson;
        this.mark = mark;
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getLeson() {
        return leson;
    }

    public void setLeson(String leson) {
        this.leson = leson;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}
