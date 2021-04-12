package ru.peytob.mineville.math;

import junit.framework.TestCase;

public class Vec3iTest extends TestCase {

    public void testNegative() {
        assertEquals(new Vec3i(5, -2, 3), new Vec3i(-5, 2, -3).negative());
    }

    public void testPlus() {
        Vec3i left = new Vec3i(12, 4, -1);
        Vec3i right = new Vec3i(-5, 1, 2);

        assertEquals(new Vec3i(7, 5, 1), left.plus(right));
    }

    public void testMinus() {
        Vec3i left = new Vec3i(0, -4, -1);
        Vec3i right = new Vec3i(7, 18, -2);

        assertEquals(new Vec3i(-7, -22, 1), left.minus(right));
    }

    public void testMultiplication() {
        Vec3i left = new Vec3i(5, -10, 12);
        assertEquals(new Vec3i(15, -30, 36), left.multiplication(3));
        assertEquals(new Vec3i(-10, 20, -24), left.multiplication(-2));
    }

    public void testScalarMultiplication() {
        Vec3i left = new Vec3i(9, 3, -3);
        Vec3i right = new Vec3i(-3, -1, 1);
        assertEquals(-33, left.scalarMultiplication(right));
    }

    public void testVectorMultiplication() {
        Vec3i left = new Vec3i(1, 2, 3);
        Vec3i right = new Vec3i(-2, 0, 4);
        assertEquals(new Vec3i(8, -10, 4), left.vectorMultiplication(right));
    }

    public void testToVec3() {
        assertEquals(new Vec3(5f, 10f, -25f), new Vec3i(5, 10, -25).toVec3());
    }

    public void testLength() {
        assertEquals(9.0f, new Vec3i(1, 4, 8).length());
    }
}