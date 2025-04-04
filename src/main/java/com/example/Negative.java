package com.example;
public abstract class Negative extends Direction2D {
    private static final double DIRECTION = -1.0;
    public Negative(int deltaX, int deltaY) {
        super(deltaX, deltaY, DIRECTION);
    }
}
