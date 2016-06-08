package com.example.nebir.festimap.pin.util;

public class Vector {

    public float x;
    public float y;

    public Vector() {
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector add(Vector v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vector clone() {
        return new Vector(x, y);
    }

    public Vector divide(float divisor) {
        x /= divisor;
        y /= divisor;
        return this;
    }

    public Vector multiply(float multiplicator) {
        x *= multiplicator;
        y *= multiplicator;
        return this;
    }

    public Vector subtract(Vector v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    @Override
    public String toString() {
        return "{x=" + x + ",y=" + y + "}";
    }
}
