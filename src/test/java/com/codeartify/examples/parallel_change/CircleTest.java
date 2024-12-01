package com.codeartify.examples.parallel_change;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

	@Test
	void should_check_point_containment_when_circle_is_moved() {
		Circle circle = new Circle(0, 0, 20);
        assertTrue(circle.contains(2, 8));

		circle.moveTo(5, 5);
		assertTrue(circle.contains(2, 8));

		circle.moveTo(20,20);
		assertFalse(circle.contains(2, 8));
	}

	@Test
	void should_check_point_containment_when_resized_for_new_area() {
		Circle circle = new Circle(0, 0, 20);
        assertTrue(circle.contains(2, 8));

		circle.resize(40);
        assertTrue(circle.contains(3, 20));

		circle.resize(1);
        assertFalse(circle.contains(2, 20));
	}

	@Test
	void should_format_location_and_radius_as_string() {
		Circle circle = new Circle(1, 4, 7);
		assertEquals("""
				circle: {
				\tcenter: (1,4)\s
				\tradius: 7 
				}""", circle.format());
	}
}
