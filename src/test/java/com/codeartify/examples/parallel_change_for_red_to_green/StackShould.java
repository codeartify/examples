package com.codeartify.examples.parallel_change_for_red_to_green;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackShould {

    @Test
    void be_empty_on_creation() {
        var stack = new Stack();

        var isEmpty = stack.isEmpty();

        assertTrue(isEmpty);
        assertEquals(0, stack.size());
    }

    @Test
    void not_be_empty_after_pushing_an_element() {
        var stack = new Stack();

        stack.push(0);

        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
    }

    @Test
    void be_empty_after_pushing_and_popping_an_element() {
        var stack = new Stack();

        stack.push(0);
        stack.pop();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void have_a_size_of_2_after_two_pushs() {
        var stack = new Stack();

        stack.push(0);
        stack.push(0);

        assertEquals(2, stack.size());
    }

    @Test
    void throw_an_exception_if_an_empty_stack_is_popped() {
        var stack = new Stack();

        assertThrows(EmptyStackPoppedException.class, stack::pop);
    }

    @Test
    void pops_the_last_element_pushed_to_the_stack() {
        var stack = new Stack();

        stack.push(99);
        assertEquals(99, stack.pop());

        stack.push(88);
        assertEquals(88, stack.pop());
    }

    @Test
    void pops_elements_in_the_reverse_order_of_how_they_were_pushed() {
        var stack = new Stack();

        stack.push(99);
        stack.push(88);

        assertEquals(88, stack.pop());
        assertEquals(99, stack.pop());
    }
}
