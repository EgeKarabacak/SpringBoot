package com.team20.core;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Deliverable {
    protected static int id = 0;

    protected String requirements;
    protected int did, maxGrade;
    protected LocalDateTime openDate, deadline;
    protected ArrayList<Grade> grades;
    protected ArrayList<Submission> submissions;

    public Deliverable(String req, String oD, String dl, int mG) {
        did = id++;

        requirements = req;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        openDate = LocalDateTime.parse(oD, formatter);
        deadline = LocalDateTime.parse(dl, formatter);
        maxGrade = mG;
        grades = new ArrayList<Grade>();
    }

    public void addGrade(Grade g) {grades.add(g);}

    public void updateMark(int stuId, int grd) {
        for(Grade g : grades) {
            if (g.getStudent().getId() == stuId) {
                g.setGrade(grd);
            }
        }
    }

    public void submit(Submission sub) {
        submissions.add(sub);
    }

    public ArrayList<Grade> getGrades() {return grades;}
    public void setGrades(ArrayList<Grade> g) {grades = g;}

    public String getRequirements() {
        return requirements;
    }

    public abstract String getType();

    public boolean isOpen() {
        return TimeController.currTime.compareTo(openDate) > 0;
    }

}
