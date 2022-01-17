package com.example.runwith.domain;

public class StepCount {
    private static final StepCount instance = new StepCount();

    public static StepCount getInstance() {
        return instance;
    }

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void plusCount() {
        count++;
    }
}
