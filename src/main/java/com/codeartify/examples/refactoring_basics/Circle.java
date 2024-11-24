package com.codeartify.examples.refactoring_basics;


import java.util.stream.IntStream;

public class Circle extends Shape {
	private int radius;
	private Point center;
	private final Color color = new Color("Green");
	private int numberOfContainedPoints;

	public Circle(int r, Point center) {
		if (r <= 0) {
			throw new RuntimeException("Radius needs to be larger 0");
		}
		this.radius = r;
		this.center = center;
	}

	public int countContainedPoints(int[] xCords, int[] yCords) {
		this.numberOfContainedPoints = 0;
		if (xCords == null || xCords.length <= 0) {
            throw new RuntimeException("x coordinates are empty");
		}
		if (yCords == null || yCords.length <= 0) {
			throw new RuntimeException("y coordinates are empty");
		}
		if (xCords.length != yCords.length) {
			throw new RuntimeException("Not every provided x coordinate has a matching y coordinate");
		}

        IntStream.range(0, xCords.length)
				.mapToObj(i -> new Point(xCords[i], yCords[i]))
				.filter(this::contains)
				.forEach(point -> this.numberOfContainedPoints++);

        return numberOfContainedPoints;
	}

	private boolean contains(Point point) {
		var deltaX = point.x() - this.center.x();
		var deltaY = point.y() - this.center.y();
        return square(deltaX) + square(deltaY) <= square(radius);
	}

	private static int square(int deltaX) {
		return deltaX * deltaX;
	}

	public void moveTo(int x, int y) {
		this.center = new Point(x, y);
	}

	public void resize(int r) {
		this.radius = r;
	}

	@Override
	public String format() {
		return "circle: {" +
				"\n\tcenter: (" + this.center.x() + "," + this.center.y() + ") " +
				"\n\tradius: " + this.radius +
				"\n\tcolor: " + this.color.getColorFormatted(false)
				+ "\n}";
	}

}
