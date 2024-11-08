package com.codeartify.examples.oneclassonetest.oneclassonetest;

public class AService {
    private final BService bService;

    public AService(BService bService) {
        this.bService = bService;
    }

    public String invoke() {
        return this.bService.getHelloWorld();
    }
}
