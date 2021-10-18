package com.team20.core;

import java.util.ArrayList;

public class SkipSample extends SamplingStrategy{
    
    private int n;
    private int i;
    private ArrayList<Grade> sample;

    public SkipSample(int n) {
        sample = new ArrayList<Grade>();
        this.n = n;
        this.i = 0;
    }

    public ArrayList<Grade> sample(Professor p) {
        sample = new ArrayList<Grade>();
        for(Course c : p.getPastCourses()) {
            sampleFromCourse(c);
        }
        return sample;
    }

    public void sampleFromCourse(Course c) {
        ArrayList<Grade> grades = c.getFinalGrades();
        for(int i = this.i; i < grades.size(); i += n) {
            sample.add(grades.get(i));
            this.i = grades.size() - i;
        }
    }
}