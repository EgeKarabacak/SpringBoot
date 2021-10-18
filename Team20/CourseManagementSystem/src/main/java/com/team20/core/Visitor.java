package com.team20.core;

public abstract class Visitor {
    
    public abstract void visit(Professor p);
    public abstract void visit(Student s);
    public abstract void visit(Administrator a);


}