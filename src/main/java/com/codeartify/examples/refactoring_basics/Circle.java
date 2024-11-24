package com.codeartify.examples.refactoring_basics;


public class Circle extends Shape {
	private int x;
	private int y;
	private int r;
	private Color color = new Color("Green");
	private int numberOfContainedPoints;

	public Circle(int x, int y, int r) {
		if (r <= 0) {
			throw new RuntimeException("Radius needs to be larger 0");
		}
		this.x = x;
		this.y = y;
		this.r = r;
	}

	public int countContainedPoints(int[] xCords, int[] yCords) {
		this.numberOfContainedPoints = 0;
        if (xCords != null) {
            if (xCords.length > 0) {
                if (yCords != null) {
                    if (yCords.length > 0) {
                        if (xCords.length == yCords.length) {
                            for (int i = 0; i < xCords.length; ++i) {
                                contains(xCords, yCords, i);
                            }
                        } else {
                            throw new RuntimeException("Not every provided x coordinate has a matching y coordinate");
                        }
                    } else {
                        throw new RuntimeException("y coordinates are empty");
                    }
                } else {
                    throw new RuntimeException("y coordinates are empty");
                }
            } else {
                throw new RuntimeException("x coordinates are empty");
            }
        } else {
            throw new RuntimeException("x coordinates are empty");
        }
		return numberOfContainedPoints;
	}

	public boolean contains(int[] xCords, int[] yCords, int i) {
		var result = (xCords[i] - this.x) * (xCords[i] - this.x) + (yCords[i] - this.y) * (yCords[i] - this.y) <= r * r;
		if (result) {
			this.numberOfContainedPoints++;
		}
		return result;
	}

	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void resize(int r) {
		this.r = r;
	}

	@Override
	public String format() {
		return "circle: {" +
				"\n\tcenter: (" + this.x + "," + this.y + ") " +
				"\n\tradius: " + this.r +
				"\n\tcolor: " + this.color.getColorFormatted(false)
				+ "\n}";
	}

}
