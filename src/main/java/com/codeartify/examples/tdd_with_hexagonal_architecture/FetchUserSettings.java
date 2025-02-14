package com.codeartify.examples.tdd_with_hexagonal_architecture;

public interface FetchUserSettings {
    UserSettings forUserId(String userID);
}
