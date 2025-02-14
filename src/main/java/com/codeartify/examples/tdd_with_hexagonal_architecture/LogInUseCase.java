package com.codeartify.examples.tdd_with_hexagonal_architecture;

public class LogInUseCase {
    private FetchMachine fetchMachine;
    private FetchUserSettings fetchUserSettings;
    private InitializeMachine initializeMachine;

    public LogInUseCase(FetchMachine fetchMachine, FetchUserSettings fetchUserSettings, InitializeMachine initializeMachine) {
        this.fetchMachine = fetchMachine;
        this.fetchUserSettings = fetchUserSettings;
        this.initializeMachine = initializeMachine;
    }

    public void logIn(String machineId, String userID) {
        logIn2(machineId, userID);
    }

    private void logIn2(String machineId, String userID) {
        Machine machine = this.fetchMachine.fetchById(machineId);
        UserSettings userSettings = this.fetchUserSettings.forUserId(userID);

        machine.setCurrentSettings(userSettings);
        this.initializeMachine.initialize(machine);
    }
}
