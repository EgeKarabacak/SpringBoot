package com.team20.core;

public class AssignmnetSubmission extends Submission {
    
    String submission;

    public AssignmnetSubmission(Student s, String sub) {
        stu = s;
        submission = sub;
    }

    public String getSubmission () {return submission;}

}