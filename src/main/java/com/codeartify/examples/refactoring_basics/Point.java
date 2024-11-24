package com.codeartify.examples.refactoring_basics;

public record Point(int x, int y) {
    public double distanceTo(Point point2) {
        var deltaX = x() - point2.x();
        var deltaY = y() - point2.y();
        return Math.sqrt((int) Math.pow(deltaX, 2) + (int) Math.pow(deltaY, 2));
    }
}
