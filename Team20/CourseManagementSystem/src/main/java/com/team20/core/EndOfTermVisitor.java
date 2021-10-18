package com.team20.core;

import java.util.ArrayList;
/*
interface Visitor {
    public void visit(Professor p);
    public void visit(Student s);
    public void visit(Administrator a);
}
*/

public class EndOfTermVisitor extends Visitor{
    
    public void visit(Professor p) {
        ArrayList<Course> courses = new ArrayList<Course>(p.getPastCourses());
        courses.addAll(p.getCoursesTaught());
        p.setCoursesTaught(new ArrayList<Course>());
        p.setPastCourses(courses);
    }

    public void visit(Student s) {
        ArrayList<Course> courses = new ArrayList<Course>(s.getCompleted());
        courses.addAll(s.getCurrent());
        s.setCurrentCourse(new ArrayList<Course>());
        s.setCompletedCourse(courses);
    }

    public void visit(Administrator a) {
    }
}