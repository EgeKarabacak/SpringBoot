package com.team20.core;

import java.util.ArrayList;
import java.util.List;


public class Lecture extends Course {

    private ArrayList<String> preclusions, prerequisites;  //Stores names of courses to preclude/prereq

    public Lecture() {}

    public Lecture(String nm, char sec, int tr, String tm, int cp, Professor pf) {
        super(nm, sec, tr, tm, cp, pf);
        
        preclusions = new ArrayList<String>();
        prerequisites = new ArrayList<String>();
        preclusions.add(nm);
    }

    public void setPrereqs(ArrayList<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setPreclusions(ArrayList<String> preclusions) {
        this.preclusions = preclusions;
    }
    
    public boolean meetsPrereqs(Student s) { 
        List<Course> courses = s.getCompleted();

        //Counts number of prerequisites met
        int prereqsMet = 0;
        for(String prereq : prerequisites) {
            for(Course c : courses) {
                if(c.getName().equals(prereq) && c.didPass(s)) {
                    prereqsMet++;
                    break;
                }
            }
        }
        
        //Returns true if all met
        if(prereqsMet == prerequisites.size()) {
            return true;
        }
        return false;
    }

    public boolean isPrecluded(Student s) {
        List<Course> coursesComp = s.getCompleted();
        List<Course> coursesCurr = s.getCurrent();

        for(String prec : preclusions) {
            for(Course c : coursesComp) {
                if (c.getName().equals(prec)) {return true;}
            }
            for(Course c : coursesCurr) {
                if (c.getName().equals(prec)) {return true;}
            }
        }

        return false;
    }

    protected Deliverable deliverableFactory(String type, String req, String oD, String dl, int mG) {
        if(type.equals("Quiz")) {
            return new Quiz(req, oD, dl, mG);
        } else if (type.equals("Assignment")) {
            return new Assignment(req, oD, dl, mG);
        }

        return null;
    }

    public String getType()
    {
        return "Lecture";
    }

    public String getCourseFullName()
    {
        return name + section;
    }
}
