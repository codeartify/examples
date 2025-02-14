package com.codeartify.examples.tdd_with_hexagonal_architecture;

public class Machine {
    private String id;
    private UserSettings userSettings = new UserSettings(-1, -1, "undefined");

    public Machine(String id) {
        this.id = id;
    }

    public boolean isReady() {
        return true;
    }

    public void setCurrentSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    public String id() {
        return this.id;
    }

    String currentTrainingMode() {
        return userSettings.trainingMode();
    }

    int currentHeight() {
        return userSettings.userHeight();
    }

    double currentWeight() {
        return userSettings.weight();
    }
}
