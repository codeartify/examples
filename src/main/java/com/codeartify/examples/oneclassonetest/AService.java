package com.codeartify.examples.oneclassonetest;

public class AService {
    public static AService create() {
        return new AService(new BService());
    }

    private final BService bService;

    public AService(BService bService) {
        this.bService = bService;
    }

    public String invoke() {
        return this.bService.getHelloWorld();
    }
}
