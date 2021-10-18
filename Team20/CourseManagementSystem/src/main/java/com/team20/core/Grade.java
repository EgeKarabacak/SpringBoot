package com.team20.core;

public class Grade extends GradeComponent{
    
    private Student student;

    /*
    Grades: 
    [0, max] = Listed mark
    -1 = not Graded
    -2 = DNF
    */
    private int grade;

    public Grade (Student stu) {
        student  = stu;
        grade = -1;
    }

    public int getGrade() {return grade;}
    public void setGrade(int i) {grade = i;}
    public Student getStudent() {return student;}
}