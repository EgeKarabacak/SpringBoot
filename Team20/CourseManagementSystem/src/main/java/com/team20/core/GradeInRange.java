package com.team20.core;

public class GradeInRange extends GradeDecorator{

    private int min, max;

    public GradeInRange(GradeComponent decoratedGrade, int min, int max) {
        this.decoratedGrade = decoratedGrade;
        this.min = min;
        this.max = max;
    }

    public boolean meetsRequirements() {
        int g = decoratedGrade.getGrade();
        return g <= max && g >= min;
    }
}