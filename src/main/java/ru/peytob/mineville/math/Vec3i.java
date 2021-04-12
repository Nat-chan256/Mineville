package ru.peytob.mineville.math;

import java.util.Objects;

public final class Vec3i {
    public int x;
    public int y;
    public int z;

    public Vec3i(int _x, int _y, int _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    public Vec3i negative() {
        return new Vec3i(-x, -y, -z);
    }

    public Vec3i plus(Vec3i _right) {
        return new Vec3i(x + _right.x, y + _right.y, z + _right.z);
    }

    public Vec3i plus(int _right) {
        return new Vec3i(x + _right, y + _right, z - _right);
    }

    public Vec3i minus(Vec3i _right) {
        return new Vec3i(x - _right.x, y - _right.y, z - _right.z);
    }

    public Vec3i minus(int _right) {
        return new Vec3i(x - _right, y - _right, z - _right);
    }

    public Vec3i multiplication(int _right) {
        return new Vec3i(x * _right, y * _right, z * _right);
    }

    public int scalarMultiplication(Vec3i _right) {
        return x * _right.x + y * _right.y + z * _right.z;
    }

    public Vec3i vectorMultiplication(Vec3i _right) {
        return new Vec3i(
                y * _right.z - z * _right.y,
                z * _right.x - x * _right.z,
                x * _right.y - y * _right.x
        );
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