package com.codeartify.examples.refactoring_basics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircleTest {

	@Test
	void should_always_have_a_radius_larger_0() {
		assertThrows(RuntimeException.class, () -> new Circle(0, new Point(0, 0)));
		assertThrows(RuntimeException.class, () -> new Circle(-1, new Point(0, 0)));
	}

	@Test
	void should_handle_coordinates_of_unequal_length() {
		Circle circle = new Circle(2, new Point(0, 0));

		int[] xCoordinates = new int[]{2, 3, 4, -12, -20};
		int[] yCoordinates = new int[]{8, 20, 15, -4,};

		assertThrows(RuntimeException.class, () -> circle.countContainedPoints(xCoordinates, yCoordinates));
	}

	@Test
	void should_handle_empty_x_coordinates() {
		Circle circle = new Circle(2, new Point(0, 0));
		int[] yCoordinates = new int[]{8, 20, 15, -4,};

		assertThrows(RuntimeException.class, () -> circle.countContainedPoints(null, yCoordinates));
		assertThrows(RuntimeException.class, () -> circle.countContainedPoints(new int[0], yCoordinates));
	}

	@Test
	void should_handle_empty_y_coordinates() {
		Circle circle = new Circle(2, new Point(0, 0));
		int[] xCoordinates = new int[]{8, 20, 15, -4,};

		assertThrows(RuntimeException.class, () -> circle.countContainedPoints(xCoordinates, null));
		assertThrows(RuntimeException.class, () -> circle.countContainedPoints(xCoordinates, new int[0]));
	}

	@Test
	void should_count_contained_points() {
		Circle circle = new Circle(10, new Point(5, -5));
		int[] xCoordinates = new int[]{2, 1, 3, 8, 4, -12, -20, -4};
		int[] yCoordinates = new int[]{8, 1, 20, -4, 15, -4, -20, -4};
		assertEquals(3, circle.countContainedPoints(xCoordinates, yCoordinates));
	}

	@Test
	void should_count_contained_points_when_moved_to_new_location() {
		Circle circle = new Circle(20, new Point(0, 0));
		int[] xCoordinates = new int[]{2, 3, 4, -12, -20};
		int[] yCoordinates = new int[]{8, 20, 15, -4, -20};
		assertEquals(3, circle.countContainedPoints(xCoordinates, yCoordinates));

		circle.moveTo(-30, -30);

		assertEquals(1, circle.countContainedPoints(xCoordinates, yCoordinates));
	}

	@Test
	void should_count_contained_points_when_resized_for_new_area() {
		Circle circle = new Circle(20, new Point(0, 0));
		int[] xCoordinates = new int[]{2, 3, 4, -12, -20};
		int[] yCoordinates = new int[]{8, 20, 15, -4, -20};
		assertEquals(3, circle.countContainedPoints(xCoordinates, yCoordinates));

		circle.resize(40);
		assertEquals(5, circle.countContainedPoints(xCoordinates, yCoordinates));

		circle.resize(1);
		assertEquals(0, circle.countContainedPoints(xCoordinates, yCoordinates));
	}

	@Test
	void should_format_location_and_radius_as_string() {
		Circle circle = new Circle(7, new Point(1, 4));
		assertEquals("""
				circle: {
				\tcenter: (1,4)\s
				\tradius: 7
				\tcolor: Green
				}""", circle.format());
	}
}
