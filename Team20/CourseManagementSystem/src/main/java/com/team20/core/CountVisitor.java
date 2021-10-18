package com.team20.core;


public class CountVisitor extends Visitor {

    public int numProfessors;
    public int numStudents;

    public CountVisitor() {
        numProfessors = 0;
        numStudents = 0;
    }
    
    public void visit(Professor p) {
        numProfessors++;
    }

    public void visit(Student s) {
        numStudents++;
    }

    public void visit(Administrator a) {
    }
}
