package com.team20.core;

public abstract class GradeDecorator extends GradeComponent{
    protected GradeComponent decoratedGrade;

    public abstract boolean meetsRequirements();

    public int getGrade() {
        return decoratedGrade.getGrade();
    }

    /*public void setGrade(int i){
        decoratedGrade.setGrade(i);
    }*/


    public Student getStudent() {
        return decoratedGrade.getStudent();
    }
}