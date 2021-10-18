package com.team20.core;

public class AttendanceSubmission extends Submission {
    
    boolean submission;

    public AttendanceSubmission(Student s, boolean sub) {
        stu = s;
        submission = sub;
    }

    public boolean getSubmission () {return submission;}

}