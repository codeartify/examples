package com.codeartify.examples.tdd_with_hexagonal_architecture;

public interface FetchMachine {
    Machine fetchById(String machineId);
}
