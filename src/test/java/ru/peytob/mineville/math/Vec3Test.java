package ru.peytob.mineville.math;

import junit.framework.TestCase;

public class Vec3Test extends TestCase {

    public void testNegative() {
        assertEquals(new Vec3(-5.02f, 18.99f, -2.1f), new Vec3(5.02f, -18.99f, 2.1f).negative());
        assertEquals(new Vec3(5.123f, -18.55f, 2.2f), new Vec3(-5.123f, 18.55f, -2.2f).negative());
    }

    public void testPlus() {
        Vec3 left = new Vec3(5.0f, 0.0f, -1.01f);
        Vec3 right = new Vec3(0.25f, -1.0f, 102f);
        assertEquals(new Vec3(5.25f, -1.0f, 100.99f), left.plus(right));
        assertEquals(new Vec3(15.0f, 10.0f, 8.99f), left.plus(10.0f));
    }

    public void testMinus() {
        Vec3 left = new Vec3(9.25f, -10.1f, 5.01f);
        Vec3 right = new Vec3(-5.25f, -1.0f, -102f);
        assertEquals(new Vec3(14.5f, -9.1f, 107.01f), left.minus(right));
        assertEquals(new Vec3(-0.75f, -20.0f, 4.99f), left.minus(10.0f));
    }

    public void testMultiplication() {
        Vec3 left = new Vec3(5.0f, 25.25f, 10.75f);

        assertEquals(new Vec3(10.0f, 50.5f, 21.5f), left.multiplication(2.0f));
    }

    public void testScalarMultiplication() {
        Vec3 left = new Vec3(1.25f, -2.1f, 5.75f);
        Vec3 right = new Vec3(-5.25f, -1.0f, 0);

        assertEquals(-357f / 80f, left.scalarMultiplication(right));
    }

    public void testVectorMultiplication() {
        Vec3 left = new Vec3(1.25f, -2.1f, 5.75f);
        Vec3 right = new Vec3(-5.25f, -1.0f, 0);

        assertEquals(new Vec3(5.75f, -30.1875f, -12.275f), left.vectorMultiplication(right));
    }

    public void testLength() {
        assertEquals(0.5f, new Vec3(0.3f, 0.4f, 0.0f).length());
    }

    public void testNormalize() {
        assertEquals(new Vec3(0.3025907f, 0.34115618f, -0.8899726f), new Vec3(5.1f, 5.75f, -15f).normalize());
    }

    public void testToVec3i() {
        assertEquals(new Vec3i(1, -2, 3), new Vec3(1.25f, -2.52f, 3.33f).toVec3i());
    }
}