package ru.peytob.mineville.math;

import java.util.Objects;

/**
 * Implements storage and processing of a 2 float vector.
 */
public class Vec2 {
    /**
     * X vector component.
     */
    public float x;

    /**
     * Y vector component.
     */
    public float y;

    public Vec2(float _x, float _y) {
        x = _x;
        y = _y;
    }

    /**
     * Creates a zero vector.
     */
    public Vec2() {

    }

    /**
     * Computes negative vector.
     * @return Negative vector.
     */
    public Vec2 negative() {
        return new Vec2(-x, -y);
    }

    /**
     * Computes sum of two vectors.
     * @param _right Right vector.
     * @return Sum of vectors.
     */
    public Vec2 plus(Vec2 _right) {
        return new Vec2(x + _right.x, y + _right.y);
    }

    /**
     * Computes sum of this vector and scalar.
     * @param _right Right scalar.
     * @return Sum of vector and scalar.
     */
    public Vec2 plus(float _right) {
        return new Vec2(x + _right, y + _right);
    }

    /**
     * Computes subtraction of two vectors.
     * @param _right Right scalar.
     * @return Subtraction of vector and scalar.
     */
    public Vec2 minus(Vec2 _right) {
        return new Vec2(x - _right.x, y - _right.y);
    }

    /**
     * Computes subtraction of this vector and scalar.
     * @param _right Right scalar.
     * @return Subtraction of vector and scalar.
     */
    public Vec2 minus(float _right) {
        return new Vec2(x - _right, y - _right);
    }

    /**
     * Returns multiplication of vector and scalar.
     * @param _right Right scalar.
     * @return Multiplication of vector and scalar.
     */
    public Vec2 multiplication(float _right) {
        return new Vec2(x * _right, y * _right);
    }

    /**
     * Returns scalar multiplication of vectors.
     * @param _right Right vector.
     * @return Scalar multiplication of vectors.
     */
    public float scalarMultiplication(Vec2 _right) {
        return x * _right.x + y * _right.y;
    }

    /**
     * Computes length of vector.
     * @return Length of vector
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Computes normalized vector.
     * @return Normalized vector
     */
    public Vec2 normalize() {
        float invLength = 1.0f / this.length();
        return new Vec2(x * invLength, y * invLength);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2 vec3 = (Vec2) o;
        return Float.compare(vec3.x, x) == 0 && Float.compare(vec3.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
