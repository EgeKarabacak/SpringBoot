package com.team20.core;

import java.util.List;


public class Tutorial extends Course {

    int tutNum;

    public Tutorial(Course cs, String tm, int cp, Professor pf) {
        super(cs.getName(), cs.getSection(), cs.getTerm(), tm, cp, pf);

        tutNum = cs.getTutNum();
    }

    //In the case of a tutorial, meeting the prereqs means being currently enrolled in the Lecture
    public boolean meetsPrereqs(Student s) { 
        List<Course> courses = s.getCurrent();

        for (Course c : courses) {
            if (name.equals(c.getName()) && section == c.getSection()) {
                return true;
            }
        }
        return false;
    }

    //In the case of a tutorial, being percluded means currently being in another tutorial of the same Lecture
    public boolean isPrecluded(Student s) {
        List<Course> courses = s.getCurrent();

        boolean temp = false;
        for (Course c : courses) {
            if (name.equals(c.getName()) && section == c.getSection()) {
                if (temp) {
                    return true;
                } else {
                    temp = true;
                }
            }
        }

        return false;
    }

    protected Deliverable deliverableFactory(String type, String req, String oD, String dl, int mG) {
        if(type.equals("Attendance")) {
            return new Attendance(req, oD, dl, mG);
        } else if (type.equals("Assignment")) {
            return new Assignment(req, oD, dl, mG);
        }

        return null;
    }

    public String getType()
    {
        return "Tutorial";
    }

    public String getCourseFullName() {
        return name + section + " Tutorial " + tutNum;
    }
}