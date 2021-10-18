package com.team20.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

interface Observer {
    public void update(String message);
}

@Entity
public class Student extends User implements Observer{
    
    private String firstName, lastName;
    @Column
    @OneToMany(mappedBy = "students")
    private List<Course> currentCourses, completedCourses;
    @Column(length = 100000)
    private ArrayList<String> notifications;
    private LocalDate birthday;
    
    public Student() {super();}

    public Student(String un, String pw, String fn, String ln, String bd, boolean en) {
        super(un,pw, en);
        firstName = fn;
        lastName = ln;
        birthday = LocalDate.parse(bd);
        currentCourses = new ArrayList<Course>();
        completedCourses = new ArrayList<Course>();
        notifications = new ArrayList<String>();
        roles = Arrays.asList("ROLE_STUDENT");
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthday() { return birthday; }
    public List<Course> getCompleted() {return completedCourses; }
    public List<Course> getCurrent() {return currentCourses; }
    public ArrayList<String> getNotifications() {return notifications;}

    public void setFirstName(String fName){
        this.firstName = fName;
    }
    public void setLastName(String lName){
        this.lastName = lName;
    }
    public void setBirthday(LocalDate birth){
        this.birthday = birth;
    }
    public void setCurrentCourse(ArrayList<Course> current){
        this.currentCourses = current;
    }
    public void setCompletedCourse(ArrayList<Course> complete){
        this.completedCourses = complete;
    }

    public void addCompletedCourses(Course c) { completedCourses.add(c); }

    public void enroll(Course c) {
        currentCourses.add(c);
    }

    public void drop(Course c) {
        currentCourses.remove(c);
    }

    public void update(String message) {
        notifications.add(message);
    }
    
    public void accept(Visitor v) {v.visit(this);}

    public String getType()
    {
        return "Student";
    }
}
