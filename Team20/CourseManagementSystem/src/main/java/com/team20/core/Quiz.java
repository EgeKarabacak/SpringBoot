package com.team20.core;

import java.util.ArrayList;

public class Quiz extends Deliverable {

    ArrayList<Character> submission;

    public Quiz(String req, String oD, String dl, int mG) {
        super(req, oD, dl, mG);
    }

    public String getType() {
        return "Quiz";
    }

}