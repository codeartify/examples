package com.codeartify.examples.tdd_with_hexagonal_architecture;

public class UserSettings {
    private final double weight;
    private int userHeight;
    private String trainingMode;

    public UserSettings(double weight, int userHeight, String trainingMode) {
        this.weight = weight;
        this.userHeight = userHeight;
        this.trainingMode = trainingMode;
    }

    public double weight() {
        return weight;
    }

    public int userHeight() {
        return userHeight;
    }

    public String trainingMode() {
        return trainingMode;
    }
}
