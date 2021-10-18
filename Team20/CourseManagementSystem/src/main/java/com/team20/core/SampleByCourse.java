package com.team20.core;

import java.util.ArrayList;

public class SampleByCourse extends SamplingStrategy{
    
    private String courseName;
    private ArrayList<Grade> sample;

    public SampleByCourse(String courseName) {
        sample = new ArrayList<Grade>();
        this.courseName = courseName;
    }

    public ArrayList<Grade> sample(Professor p) {
        sample = new ArrayList<Grade>();
        for(Course c : p.getPastCourses()) {
            if(c.getName().equals(courseName)) {
                sampleFromCourse(c);
            }
        }
        return sample;
    }

    public void sampleFromCourse(Course c) {
        ArrayList<Grade> grades = c.getFinalGrades();
        for(int i = 0; i < grades.size(); i++) {
            sample.add(grades.get(i));
        }
    }

}
