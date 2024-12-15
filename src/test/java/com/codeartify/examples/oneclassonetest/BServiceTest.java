package com.codeartify.examples.oneclassonetest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BServiceTest {
    @Test
    void test1() {
        var bService = new BService();

        var helloWorld = bService.getHelloWorld();

        assertEquals("Hello World", helloWorld);
    }

    @Test
    void test2() {
        var bService = new BService();

        bService.getHelloWorld();
        var helloWorld = bService.getHelloWorld();

        assertEquals("World Hello", helloWorld);
    }
}
