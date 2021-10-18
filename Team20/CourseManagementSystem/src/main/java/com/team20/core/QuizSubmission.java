package com.team20.core;

import java.util.ArrayList;

public class QuizSubmission extends Submission {
    
    ArrayList<Character> submission;

    public QuizSubmission(Student s, ArrayList<Character> sub) {
        stu = s;
        submission = sub;
    }

    public ArrayList<Character> getSubmission () {return submission;}

}