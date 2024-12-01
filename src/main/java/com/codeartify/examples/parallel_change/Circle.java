package com.codeartify.examples.parallel_change;


public class Circle {
    private int x;
    private int y;
	private int radius;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
		this.radius = radius;
    }

    public boolean contains(int x, int y) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) <= radius * radius;
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

	public void resize(int radius) {
		this.radius = radius;
	}
 
	public String format() {
		return "circle: {" +
			   "\n\tcenter: (" + this.x + "," + this.y + ") " +
			   "\n\tradius: " + this.radius
			   + "\n}";
	}
}
