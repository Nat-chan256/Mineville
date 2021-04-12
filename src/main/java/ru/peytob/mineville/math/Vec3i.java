package ru.peytob.mineville.math;

import java.util.Objects;

/**
 * Implements storage and processing of a 3 integer vector.
 */
public final class Vec3i {
    /**
     * X vector component.
     */
    public int x;

    /**
     * Y vector component.
     */
    public int y;

    /**
     * Z vector component.
     */
    public int z;

    public Vec3i(int _x, int _y, int _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    /**
     * Creates a zero vector.
     */
    public Vec3i() {

    }

    /**
     * Computes negative vector.
     * @return Negative vector.
     */
    public Vec3i negative() {
        return new Vec3i(-x, -y, -z);
    }

    /**
     * Computes sum of two vectors.
     * @param _right Right vector.
     * @return Sum of vectors.
     */
    public Vec3i plus(Vec3i _right) {
        return new Vec3i(x + _right.x, y + _right.y, z + _right.z);
    }

    /**
     * Computes sum of this vector and scalar.
     * @param _right Right scalar.
     * @return Sum of vector and scalar.
     */
    public Vec3i plus(int _right) {
        return new Vec3i(x + _right, y + _right, z - _right);
    }

    /**
     * Computes subtraction of two vectors.
     * @param _right Right scalar.
     * @return Subtraction of vector and scalar.
     */
    public Vec3i minus(Vec3i _right) {
        return new Vec3i(x - _right.x, y - _right.y, z - _right.z);
    }

    /**
     * Computes subtraction of this vector and scalar.
     * @param _right Right scalar.
     * @return Subtraction of vector and scalar.
     */
    public Vec3i minus(int _right) {
        return new Vec3i(x - _right, y - _right, z - _right);
    }

    /**
     * Returns multiplication of vector and scalar.
     * @param _right Right scalar.
     * @return Multiplication of vector and scalar.
     */
    public Vec3i multiplication(int _right) {
        return new Vec3i(x * _right, y * _right, z * _right);
    }

    /**
     * Returns scalar multiplication of vectors.
     * @param _right Right vector.
     * @return Scalar multiplication of vectors.
     */
    public int scalarMultiplication(Vec3i _right) {
        return x * _right.x + y * _right.y + z * _right.z;
    }

    /**
     * Returns vector multiplication of vectors.
     * @param _right Right vector.
     * @return Vector multiplication of vectors.
     */
    public Vec3i vectorMultiplication(Vec3i _right) {
        return new Vec3i(
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

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec3i vec3i = (Vec3i) o;
        return x == vec3i.x && y == vec3i.y && z == vec3i.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}