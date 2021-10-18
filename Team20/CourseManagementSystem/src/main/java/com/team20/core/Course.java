package com.team20.core;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public abstract class Course extends Subject {
    protected static int id = 0;

    protected String name, location;
    protected char section;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected int cid;
    protected int term, cap;
    @ManyToOne
    protected Professor prof;
    protected LocalTime time;
    protected ArrayList<Observer> observers;
    @Column
    @ElementCollection
    protected List<Student> students;
//    private ArrayList<String> preclusions, prerequisites;  //Stores names of courses to preclude/prereq
    protected ArrayList<Deliverable> deliverables;
    protected ArrayList<Grade> finalGrades;
    protected int numTutorials = 1;

    public Course() {}

    public Course(String nm, char sec, int tr, String tm, int cp, Professor pf) {
        cid = id++;

        name = nm;
        section = sec;
        term = tr;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        time = LocalTime.parse(tm, formatter);
        cap = cp;
        prof = pf;
        observers = new ArrayList<Observer>();
        students = new ArrayList<Student>();
//        preclusions = new ArrayList<String>();
//        prerequisites = new ArrayList<String>();
        deliverables = new ArrayList<Deliverable>();
        finalGrades = new ArrayList<Grade>();
    }

    public Course(String nm) {
        name = nm;
    }

    public String getName() { return name; }
    public char getSection() { return section; }
    public int getId() { return cid; }
    public LocalTime getTime() { return time; }
    public int getTerm() { return term; }

    public void setName(String name){
        this.name = name;
    }
    public void setSection(char sect){
        this.section = sect;
    }
    public void setTerm(int trm){
        this.term = trm;
    }
    public void setCapacity(int capacity){
        this.cap = capacity;
    }
    public void setProf(Professor jp){
        this.prof = jp;
    }
    public Professor getProf(){
        return prof;
    }
    public void setFinalGrades(ArrayList<Grade> g) {finalGrades = g;}

    public ArrayList<Grade> getFinalGrades() {
        return new ArrayList<Grade> (this.finalGrades);
    }
    
    public abstract boolean meetsPrereqs(Student s);

    public abstract boolean isPrecluded(Student s);

    public boolean enroll(Student s) {
        if(meetsPrereqs(s) && !isPrecluded(s) && students.size() < cap) {
            s.enroll(this);
            students.add(s);
            registerObserver(s);

            boolean flag = false;
            for(Grade g : finalGrades) {
                if (g.getStudent() == s) {
                    g.setGrade(-1);
                    flag = true;
                }
            }
            if (!flag) {
                finalGrades.add(new Grade(s));
            }
            return true;
        }
        return false;
    }

    public void drop(Student s) {
        s.drop(this);
        students.remove(s);
        removeObserver(s);

        //Later could modify to mark DNF instead of removing grade entirely
        for(int i = 0; i < finalGrades.size(); i++) {
            if (s == finalGrades.get(i).getStudent()) {
                finalGrades.get(i).setGrade(-2);
            }
        }
    }

    //Removes this course from all student's and prof's current courses lists
    public void removeFromAll() {
        for(Student s : students) {
            s.drop(this);
        }
        getProf().removeCourse(this);
    }
    
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers(String message) {
        for(int i = 0; i < observers.size(); i++) {
            observers.get(i).update(message);
        }
    }

    public void notify(Observer o, String message) {
        o.update(message);
    }

    public void createDeliverable(String type, String req, String oD, String dl, int mG) {
        deliverables.add(deliverableFactory(type, req, oD, dl, mG));

        //Populate the deliverable Grades arraylist
        for(Student s : students) {
            deliverables.get(deliverables.size() - 1).addGrade(new Grade(s));
        }

        notifyObservers("A new " + type + " has been added with open date: " + oD + " and deadline: " + dl);
    }


    public void updateMark(int stuId, int grd) {
        for(Grade g : finalGrades) {
            if (g.getStudent().getId() == stuId) {
                g.setGrade(grd);
                notify(g.getStudent(), "Your final grade has been updated.");
            }
        }
    }

    public void tmpAddDeliverable(Deliverable d) {
        deliverables.add(d);
    }

    public ArrayList<Deliverable> getDeliverables() {
        return deliverables;
    }

    protected abstract Deliverable deliverableFactory(String type, String req, String oD, String dl, int mG);

    public boolean didPass(Student stu) {
        for (Grade g : finalGrades) {
            if (g.getStudent() == stu) {
                return g.getGrade() >= 50;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Course)) {
            return false;
        }

        Course c = (Course) o;

        return (c.getId() == this.getId());
    }

    public boolean isFull()
    {
        return students.size() >= cap;
    }

    public abstract String getType();

    public ArrayList<Deliverable> getActiveDeliverables() {
        ArrayList<Deliverable> activeList = new ArrayList<Deliverable>();
        for (Deliverable d : deliverables) {
            if (d.isOpen()) activeList.add(d);
        }
        return activeList;
    }

    public abstract String getCourseFullName();

    public int getTutNum() {
        return numTutorials++;
    }
}
