package com.team20.core;

import java.util.ArrayList;
import java.util.Random;

public class RandomSample extends SamplingStrategy{
    
    private int n;
    private int i;
    private Random r;
    private ArrayList<Grade> sample;

    public RandomSample(int n) {
        sample = new ArrayList<Grade>();
        this.n = n;
        this.i = 0;
        r = new Random();
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
        for(int i = this.i; i < grades.size(); i += getRandomStep()) {
            sample.add(grades.get(i));
            this.i = grades.size() - i;
        }
    }

    public int getRandomStep() {
        int step = 0;
        while(r.nextInt(n) != 0) {
            step++;
        }
        return step;
    }
}