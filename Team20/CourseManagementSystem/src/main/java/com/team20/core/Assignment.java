package com.team20.core;


public class Assignment extends Deliverable {


    public Assignment(String req, String oD, String dl, int mG) {
        super(req, oD, dl, mG);
    }

    public String getType() {
        return "Assignment";
    }

}