package com.codeartify.examples.tdd_with_hexagonal_architecture;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogInUseCaseShould {
    @Test
    void log_in() {
        var fetchMachine = new TestMachineRepository();
        FetchUserSettings fetchUserSettings = (userID -> new UserSettings(50, 181, "adaptive"));

        var loginUseCase = new LogInUseCase(fetchMachine, fetchUserSettings, fetchMachine);
        var machineId = "machine-id";
        var userID = "user-id";

        loginUseCase.logIn(machineId, userID);

        assertTrue(fetchMachine.fetchById(machineId).isReady());
    }

    @Test
    void fetch_a_users_settings_and_apply_them_to_the_machine() {
        var testMachineRepo = new TestMachineRepository();
        FetchUserSettings fetchUserSettings = (userID -> new UserSettings(50, 181, "adaptive"));

        var loginUseCase = new LogInUseCase(testMachineRepo, fetchUserSettings, testMachineRepo);
        var machineId = "machine-id";
        var userID = "user-id";

        loginUseCase.logIn(machineId, userID);

        var machine = testMachineRepo.fetchById(machineId);

        assertEquals(50, machine.currentWeight());
        assertEquals(181, machine.currentHeight());
        assertEquals("adaptive", machine.currentTrainingMode());
    }

    private class TestMachineRepository implements FetchMachine, InitializeMachine {
        private Map<String, Machine> machines = new HashMap<>();

        @Override

        public Machine fetchById(String machineId) {
            return machines.getOrDefault(machineId, new Machine(machineId));
        }

        @Override
        public void initialize(Machine machine) {
            this.machines.put(machine.id(), machine);
        }
    }
}
