package com.team20.core;

import java.util.ArrayList;

public class CompositeSample extends SamplingStrategy {
    private ArrayList<SamplingStrategy> componentStrategies;
    private ArrayList<Grade> sample;

    public CompositeSample(ArrayList<SamplingStrategy> strategies) {
        sample = new ArrayList<Grade>();
        componentStrategies = new ArrayList<SamplingStrategy>(strategies);
    }

    public ArrayList<Grade> sample(Professor p) {
        sample = new ArrayList<Grade>();
        for(SamplingStrategy s : componentStrategies) {
            sample.addAll(s.sample(p));
        }
        return sample;
    }
}