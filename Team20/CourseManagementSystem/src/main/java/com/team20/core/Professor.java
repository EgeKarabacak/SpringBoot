package com.team20.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Professor extends User {
    
    private String firstName, lastName;

    @Column
    @OneToMany(mappedBy = "students")
    private List<Course> coursesTaught;
    @Column
    @OneToMany(mappedBy = "students")
    private List<Course> pastCourses;

    public Professor () {super();}

    public Professor (String un, String pw, String fn, String ln, boolean en) {
        super(un,pw,en);

        firstName = fn;
        lastName = ln;
        coursesTaught = new ArrayList<Course>();
        pastCourses = new ArrayList<Course>();
        roles = Arrays.asList("ROLE_PROFESSOR");
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public List<Course> getCoursesTaught() {return coursesTaught;}
    public List<Course> getPastCourses() {return pastCourses;}
    
    public void setFirstName(String fName){
        this.firstName = fName;
    }
    public void setLastName(String lName){
        this.lastName = lName;
    }
    public void setCoursesTaught(List<Course> courses){
        this.coursesTaught = courses;
    }
    public void setPastCourses(List<Course> courses){
        this.pastCourses = courses;
    }
    
    public void removeCourse(Course c) {
        this.coursesTaught.remove(c);
    }

    
    public void addCoursesTaught(Course c) {coursesTaught.add(c);}
    public void addPastCourses(Course c) {pastCourses.add(c);}

    public void accept(Visitor v) {v.visit(this);}

    public String toString()
    {
        return firstName+" " +lastName;
    }

    public String getType()
    {
        return "Professor";
    }
}
