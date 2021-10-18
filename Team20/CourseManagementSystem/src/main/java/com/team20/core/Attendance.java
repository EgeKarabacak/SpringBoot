package com.team20.core;


public class Attendance extends Deliverable {

    boolean submission;

    public Attendance(String req, String oD, String dl, int mG) {
        super(req, oD, dl, mG);
    }

    public String getType() {
        return "Attendance";
    }

}