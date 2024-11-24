package com.codeartify.examples.refactoring_basics;


import java.util.stream.IntStream;

public class Circle extends Shape {
	private int radius;
	private Point center;
	private final Color color = new Color("Green");
	private int numberOfContainedPoints;

	public Circle(int radius, Point center) {
		if (radius <= 0) {
			throw new RuntimeException("Radius needs to be larger 0");
		}
		this.radius = radius;
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

        IntStream.range(0, xCords.length).forEach(i -> evaluate(xCords, yCords, i));

		return numberOfContainedPoints;
	}

	private void evaluate(int[] xCords, int[] yCords, int i) {
		var result = (xCords[i] - this.center.x()) * (xCords[i] - this.center.x()) + (yCords[i] - this.center.y()) * (yCords[i] - this.center.y()) <= radius * radius;

		if (result) {
			this.numberOfContainedPoints++;
		}
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
