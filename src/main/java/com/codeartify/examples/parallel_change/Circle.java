package com.codeartify.examples.parallel_change;


public class Circle {
    private Point center;
	private int radius;

    public Circle(int x, int y, int radius) {
        this.center = new Point(x, y);
		this.radius = radius;
    }

    public boolean contains(int x, int y) {
        return (x - this.center.x()) * (x - this.center.x())
               + (y - this.center.y()) * (y - this.center.y())
               <= radius * radius;
    }

    public void moveTo(int x, int y) {
        this.center = new Point(x, y);
    }

	public void resize(int radius) {
		this.radius = radius;
	}
 
	public String format() {
		return "circle: {" +
               "\n\tcenter: ("
               + this.center.x() + ","
               + this.center.y() + ") " +
			   "\n\tradius: " + this.radius
			   + "\n}";
	}
}
