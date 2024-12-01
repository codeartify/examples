package com.codeartify.examples.parallel_change;


public class Circle {
    private int y;
    private Point center;
	private int radius;

    public Circle(int x, int y, int radius) {
        this.y = y;
        this.center = new Point(x, y);
		this.radius = radius;
    }

    public boolean contains(int x, int y) {
        return (x - this.center.x()) * (x - this.center.x()) + (y - this.y) * (y - this.y) <= radius * radius;
    }

    public void moveTo(int x, int y) {
        this.y = y;
        this.center = new Point(x, y);
    }

	public void resize(int radius) {
		this.radius = radius;
	}
 
	public String format() {
		return "circle: {" +
               "\n\tcenter: (" + this.center.x() + "," + this.y + ") " +
			   "\n\tradius: " + this.radius
			   + "\n}";
	}
}
