package com.codeartify.examples.oneclassonetest;

import com.codeartify.examples.oneclassonetest.oneclassonetest.AService;
import com.codeartify.examples.oneclassonetest.oneclassonetest.BService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AServiceTest {
    @Test
    void test1() {
        var mock = mock(BService.class);
        when(mock.getHelloWorld()).thenReturn("Hello World");
        var aService = new AService(mock);

        String result = aService.invoke();

        assertEquals("Hello World", result);
    }

    @Test
    void test2() {
        var mock = mock(BService.class);
        when(mock.getHelloWorld()).thenReturn("World Hello");
        var aService = new AService(mock);

        aService.invoke();
        String result = aService.invoke();

        assertEquals("World Hello", result);
    }
}
