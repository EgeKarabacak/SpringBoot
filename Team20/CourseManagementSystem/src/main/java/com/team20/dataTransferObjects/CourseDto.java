package com.team20.dataTransferObjects;

import javax.validation.constraints.NotEmpty;

public class CourseDto {
    @NotEmpty(message = "Name cannot be Empty!")
    private String name;
    private char section;
    private int term;
    private int cap;
    private String prof;
    private String time;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSection() {
        return this.section;
    }

    public void setSection(char section) {
        this.section = section;
    }

    public int getTerm() {
        return this.term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getCap() {
        return this.cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getProf() {
        return this.prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
