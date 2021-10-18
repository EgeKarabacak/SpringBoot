package com.team20.core;

import java.util.ArrayList;

public class SampleByTerm extends SamplingStrategy{
    
    private int minTerm, maxTerm;
    private ArrayList<Grade> sample;

    public SampleByTerm(int minTerm, int maxTerm) {
        sample = new ArrayList<Grade>();
        this.minTerm = minTerm;
        this.maxTerm = maxTerm;
    }

    public ArrayList<Grade> sample(Professor p) {
        sample = new ArrayList<Grade>();
        for(Course c : p.getPastCourses()) {
            if(c.getTerm() <= maxTerm && c.getTerm() >= minTerm) {
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
