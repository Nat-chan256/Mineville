package ru.peytob.mineville.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpecialMatricesTest {

    @Test
    public void identityTest() {
        float[] expectedData = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        };

        Mat4 expected = new Mat4(expectedData);

        assertEquals(expected, Mat4.computeIdentity());
    }

    @Test
    public void scaleTest() {
        float[] expectedData = new float[]{
                53.2f, 0, 0, 0,
                0, 65.0f, 0, 0,
                0, 0, -12.11f, 0,
                0, 0, 0, 1
        };

        Mat4 expected = new Mat4(expectedData);

        assertEquals(expected, Mat4.computeScaleMatrix(53.2f, 65.0f, -12.11f));
    }

    @Test
    public void translationTest() {
        float[] expectedData = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                -53.2f, 12.66f, 76f, 1
        };

        Mat4 expected = new Mat4(expectedData);

        assertEquals(expected, Mat4.computeTranslation(-53.2f, 12.66f, 76));
    }

    @Test
    public void rotateXTest() {
        float[] expectedData = new float[]{
                1, 0, 0, 0,
                0, 0.4999999f, -0.86602545f, 0,
                0, 0.86602545f, 0.4999999f, 0,
                0, 0, 0, 1
        };

        Mat4 expected = new Mat4(expectedData);

        float angle = (float) Math.PI / 3.0f * 5.0f;
        assertEquals(expected, Mat4.computeRotationX(angle));
    }

    @Test
    public void rotateYTest() {
        float[] expectedData = new float[]{
                0.4999999f, 0, 0.86602545f, 0,
                0, 1, 0, 0,
                -0.86602545f, 0, 0.4999999f, 0,
                0, 0, 0, 1
        };

        Mat4 expected = new Mat4(expectedData);

        float angle = (float) Math.PI / 3.0f * 5.0f;
        assertEquals(expected, Mat4.computeRotationY(angle));
    }

    @Test
    public void rotateZTest() {
        float[] expectedData = new float[]{
                0.4999999f, -0.86602545f, 0, 0,
                0.86602545f, 0.4999999f, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        };

        Mat4 expected = new Mat4(expectedData);

        float angle = (float) Math.PI / 3.0f * 5.0f;
        assertEquals(expected, Mat4.computeRotationZ(angle));
    }

    @Test
    public void rotateTest() {
        float[] expectedData = new float[]{
                0.719122f, -0.55907035f, 0.41218716f, 0,
                0.6914704f, 0.5199999f, -0.5011088f, 0,
                0.065776974f, 0.6455088f, 0.76064193f, 0,
                0, 0, 0, 1
        };

        Mat4 expected = new Mat4(expectedData);

        float angle = (float) Math.PI / 3.0f * 5.0f;
        assertEquals(expected, Mat4.computeRotation(angle, 0.662f, 0.2f, 0.722f));
    }
}
