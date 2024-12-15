package com.codeartify.examples.oneclassonetest;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AServiceBetterTest {

    @Test
    void test() {
        var aService = AService.create();

        String result = aService.invoke();

        assertEquals("Hello World", result);
    }

    @Test
    void test2() {
        var aService = AService.create();

        aService.invoke();
        String result = aService.invoke();

        assertEquals("World Hello", result);
    }
}
