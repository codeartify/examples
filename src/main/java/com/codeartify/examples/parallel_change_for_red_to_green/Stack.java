package com.codeartify.examples.parallel_change_for_red_to_green;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    private final List<Integer> elements = new ArrayList<>();

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public void push(int element) {
        this.elements.add(element);
    }

    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackPoppedException();
        }
        return elements.removeLast();
    }

    public int size() {
        return elements.size();
    }
}
