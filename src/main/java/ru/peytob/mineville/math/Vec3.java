package ru.peytob.mineville.math;

import java.util.Objects;

/**
 * Implements storage and processing of a 3 float vector.
 */
public class Vec3 {

    /**
     * X vector component.
     */
    public float x;

    /**
     * Y vector component.
     */
    public float y;

    /**
     * Z vector component.
     */
    public float z;

    public Vec3(float _x, float _y, float _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    /**
     * Creates a zero vector.
     */
    public Vec3() {

    }

    /**
     * Computes negative vector.
     * @return Negative vector.
     */
    public Vec3 negative() {
        return new Vec3(-x, -y, -z);
    }

    /**
     * Computes sum of two vectors.
     * @param _right Right vector.
     * @return Sum of vectors.
     */
    public Vec3 plus(Vec3 _right) {
        return new Vec3(x + _right.x, y + _right.y, z + _right.z);
    }

    /**
     * Computes sum of this vector and scalar.
     * @param _right Right scalar.
     * @return Sum of vector and scalar.
     */
    public Vec3 plus(float _right) {
        return new Vec3(x + _right, y + _right, z + _right);
    }

    /**
     * Computes subtraction of two vectors.
     * @param _right Right scalar.
     * @return Subtraction of vector and scalar.
     */
    public Vec3 minus(Vec3 _right) {
        return new Vec3(x - _right.x, y - _right.y, z - _right.z);
    }

    /**
     * Computes subtraction of this vector and scalar.
     * @param _right Right scalar.
     * @return Subtraction of vector and scalar.
     */
    public Vec3 minus(float _right) {
        return new Vec3(x - _right, y - _right, z - _right);
    }

    /**
     * Returns multiplication of vector and scalar.
     * @param _right Right scalar.
     * @return Multiplication of vector and scalar.
     */
    public Vec3 multiplication(float _right) {
        return new Vec3(x * _right, y * _right, z * _right);
    }

    /**
     * Returns scalar multiplication of vectors.
     * @param _right Right vector.
     * @return Scalar multiplication of vectors.
     */
    public float scalarMultiplication(Vec3 _right) {
        return x * _right.x + y * _right.y + z * _right.z;
    }

    /**
     * Returns vector multiplication of vectors.
     * @param _right Right vector.
     * @return Vector multiplication of vectors.
     */
    public Vec3 vectorMultiplication(Vec3 _right) {
        return new Vec3(
                y * _right.z - z * _right.y,
                z * _right.x - x * _right.z,
                x * _right.y - y * _right.x
        );
    }

    /**
     * Computes length of vector.
     * @return Length of vector
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Computes normalized vector.
     * @return Normalized vector
     */
    public Vec3 normalize() {
        float invLength = 1.0f / this.length();
        return new Vec3(x * invLength, y * invLength, z * invLength);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public Vec3i toVec3i() {
        return new Vec3i((int) x, (int) y, (int) z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec3 vec3 = (Vec3) o;
        return Float.compare(vec3.x, x) == 0 && Float.compare(vec3.y, y) == 0 && Float.compare(vec3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
