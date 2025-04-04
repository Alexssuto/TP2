package com.example;
public abstract class Positive extends Direction2D {
    private static final double DIRECTION = 1.0;
    public Positive(int deltaX, int deltaY) {
        super(deltaX, deltaY, DIRECTION);
    }
}
