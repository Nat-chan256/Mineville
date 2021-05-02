package ru.peytob.mineville.math;

import java.util.Arrays;

/**
 * Implements storage and processing of a 4x4 float matrix.
 * The static methods of the class store methods for calculating transformation matrices.
 */
public final class Mat4 {
    /**
     * Stores matrix data.
     */
    private final float[] data;

    /**
     * Creates a zero matrix.
     */
    public Mat4() {
        data = new float[16];
    }

    /**
     * Creates a matrix and fill its from _data array.
     *
     * @param _data Data for filling in the matrix.
     * @throws NumberFormatException If there are not 16 elements in the array.
     */
    public Mat4(float[] _data) throws NumberFormatException {
        if (_data.length != 16) {
            throw new NumberFormatException("Sizes of _data array should be 16 (4 x 4).");
        }

        data = Arrays.copyOf(_data, _data.length);
    }

    /**
     * Copy constructor.
     *
     * @param _other Other matrix.
     */
    public Mat4(Mat4 _other) {
        data = Arrays.copyOf(_other.data, _other.data.length);
    }

    /**
     * Returns element by index.
     *
     * @param _index Index of element.
     * @return Element of matrix.
     * @throws IndexOutOfBoundsException If index is negative or more than 15.
     */
    public float get(int _index) throws IndexOutOfBoundsException {
        return data[_index];
    }

    /**
     * Returns element by row and column.
     *
     * @param _row    Row of element.
     * @param _column Column of element.
     * @return Element of matrix at specified row and column. Alias for get(_row * 4 + _column).
     * @throws IndexOutOfBoundsException If _row * 4 + _column is negative or more than 15.
     */
    public float get(int _row, int _column) throws IndexOutOfBoundsException {
        return get(_row * 4 + _column);
    }

    /**
     * Sets value of specified element.
     *
     * @param _index Index of element.
     * @param _data  New data of element.
     * @throws IndexOutOfBoundsException If index is negative or more than 15.
     */
    public void set(int _index, float _data) throws IndexOutOfBoundsException {
        data[_index] = _data;
    }

    /**
     * Sets value of specified element. Alias for set(_row * 4 + _column).
     *
     * @param _row    Row of element.
     * @param _column Column of element.
     * @param _data   New data of element.
     * @throws IndexOutOfBoundsException If index is negative or more than 15.
     */
    public void set(int _row, int _column, float _data) throws IndexOutOfBoundsException {
        set(_row * 4 + _column, _data);
    }

    /**
     * Returns sum of two matrices.
     *
     * @param _right Right matrix.
     * @return Sum of two matrices.
     */
    public Mat4 plus(Mat4 _right) {
        Mat4 result = new Mat4();
        for (int i = 0; i < data.length; ++i)
            result.set(i, get(i) + _right.get(i));

        return result;
    }

    /**
     * Returns subtraction of two matrices.
     *
     * @param _right Right matrix.
     * @return Subtraction of two matrices.
     */
    public Mat4 minus(Mat4 _right) {
        Mat4 result = new Mat4();
        for (int i = 0; i < data.length; ++i)
            result.set(i, get(i) - _right.get(i));

        return result;
    }

    /**
     * Returns multiplication of two matrices.
     *
     * @param _right Right matrix.
     * @return Multiplication of two matrices.
     */
    public Mat4 multiplication(Mat4 _right) {
        float[] resultArray = new float[]{
                get(0, 0) * _right.get(0, 0) + get(1, 0) * _right.get(0, 1) +
                        get(2, 0) * _right.get(0, 2) + get(3, 0) * _right.get(0, 3),
                get(0, 1) * _right.get(0, 0) + get(1, 1) * _right.get(0, 1) +
                        get(2, 1) * _right.get(0, 2) + get(3, 1) * _right.get(0, 3),
                get(0, 2) * _right.get(0, 0) + get(1, 2) * _right.get(0, 1) +
                        get(2, 2) * _right.get(0, 2) + get(3, 2) * _right.get(0, 3),
                get(0, 3) * _right.get(0, 0) + get(1, 3) * _right.get(0, 1) +
                        get(2, 3) * _right.get(0, 2) + get(3, 3) * _right.get(0, 3),
                get(0, 0) * _right.get(1, 0) + get(1, 0) * _right.get(1, 1) +
                        get(2, 0) * _right.get(1, 2) + get(3, 0) * _right.get(1, 3),
                get(0, 1) * _right.get(1, 0) + get(1, 1) * _right.get(1, 1) +
                        get(2, 1) * _right.get(1, 2) + get(3, 1) * _right.get(1, 3),
                get(0, 2) * _right.get(1, 0) + get(1, 2) * _right.get(1, 1) +
                        get(2, 2) * _right.get(1, 2) + get(3, 2) * _right.get(1, 3),
                get(0, 3) * _right.get(1, 0) + get(1, 3) * _right.get(1, 1) +
                        get(2, 3) * _right.get(1, 2) + get(3, 3) * _right.get(1, 3),
                get(0, 0) * _right.get(2, 0) + get(1, 0) * _right.get(2, 1) +
                        get(2, 0) * _right.get(2, 2) + get(3, 0) * _right.get(2, 3),
                get(0, 1) * _right.get(2, 0) + get(1, 1) * _right.get(2, 1) +
                        get(2, 1) * _right.get(2, 2) + get(3, 1) * _right.get(2, 3),
                get(0, 2) * _right.get(2, 0) + get(1, 2) * _right.get(2, 1) +
                        get(2, 2) * _right.get(2, 2) + get(3, 2) * _right.get(2, 3),
                get(0, 3) * _right.get(2, 0) + get(1, 3) * _right.get(2, 1) +
                        get(2, 3) * _right.get(2, 2) + get(3, 3) * _right.get(2, 3),
                get(0, 0) * _right.get(3, 0) + get(1, 0) * _right.get(3, 1) +
                        get(2, 0) * _right.get(3, 2) + get(3, 0) * _right.get(3, 3),
                get(0, 1) * _right.get(3, 0) + get(1, 1) * _right.get(3, 1) +
                        get(2, 1) * _right.get(3, 2) + get(3, 1) * _right.get(3, 3),
                get(0, 2) * _right.get(3, 0) + get(1, 2) * _right.get(3, 1) +
                        get(2, 2) * _right.get(3, 2) + get(3, 2) * _right.get(3, 3),
                get(0, 3) * _right.get(3, 0) + get(1, 3) * _right.get(3, 1) +
                        get(2, 3) * _right.get(3, 2) + get(3, 3) * _right.get(3, 3)
        };

        return new Mat4(resultArray);
    }

    /**
     * Returns the transposed matrix.
     *
     * @return The transposed matrix.
     */
    public Mat4 transpose() {
        Mat4 transposed = new Mat4(this);

        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                float temp = transposed.get(i, j);
                transposed.set(i, j, get(j, i));
                transposed.set(j, i, temp);
            }
        }

        return transposed;
    }

    /**
     * Returns a raw data of matrix.
     *
     * @return Raw data of array.
     */
    public float[] toFloatArray() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mat4 mat4 = (Mat4) o;
        return Arrays.equals(data, mat4.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                builder.append(get(i, j));
                builder.append(", ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    /**
     * Computes identity matrix.
     *
     * @return New instance of identity matrix.
     */
    static public Mat4 computeIdentity() {
        float[] data = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        };

        return new Mat4(data);
    }

    /**
     * Computes scale matrix.
     *
     * @param _x X axis scale.
     * @param _y Y axis scale.
     * @param _z Z axis scale.
     * @return New instance of scale matrix.
     */
    static public Mat4 computeScaleMatrix(float _x, float _y, float _z) {
        float[] data = new float[]{
                _x, 0, 0, 0,
                0, _y, 0, 0,
                0, 0, _z, 0,
                0, 0, 0, 1
        };

        return new Mat4(data);
    }

    /**
     * Alias for Mat4.computeScaleMatrix(vec3.x, vec3.y, vec3.z).
     *
     * @param _vec3 Scale vector.
     * @return New instance of scale matrix.
     */
    static public Mat4 computeScaleMatrix(Vec3 _vec3) {
        return computeScaleMatrix(_vec3.x, _vec3.y, _vec3.z);
    }

    /**
     * Computes transposed translate matrix.
     *
     * @param _x X axis translate.
     * @param _y Y axis translate.
     * @param _z Z axis translate.
     * @return New instance of transposed translation matrix.
     */
    static public Mat4 computeTranslation(float _x, float _y, float _z) {
        float[] data = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                _x, _y, _z, 1
        };

        return new Mat4(data);
    }

    /**
     * Alias for Mat4.computeTranslation(vec3.x, vec3.y, vec3.z).
     *
     * @param _vec3 Translation vector.
     * @return New instance of transposed translation matrix.
     */
    static public Mat4 computeTranslation(Vec3 _vec3) {
        return computeTranslation(_vec3.x, _vec3.y, _vec3.z);
    }

    /**
     * Returns transposed rotation matrix around (1, 0, 0) vector.
     *
     * @param _angle Rotate angle in radians.
     * @return New instance of transposed rotation matrix around X axis ((1, 0, 0) vector).
     */
    static public Mat4 computeRotationX(float _angle) {
        float cos = (float) Math.cos(_angle);
        float sin = (float) Math.sin(_angle);

        float[] data = new float[]{
                1, 0, 0, 0,
                0, cos, sin, 0,
                0, -sin, cos, 0,
                0, 0, 0, 1
        };

        return new Mat4(data);
    }

    /**
     * Returns transposed rotation matrix around (0, 1, 0) vector.
     *
     * @param _angle Rotate angle in radians.
     * @return New instance of transposed rotation matrix around Y axis ((0, 1, 0) vector).
     */
    static public Mat4 computeRotationY(float _angle) {
        float cos = (float) Math.cos(_angle);
        float sin = (float) Math.sin(_angle);

        float[] data = new float[]{
                cos, 0, -sin, 0,
                0, 1, 0, 0,
                sin, 0, cos, 0,
                0, 0, 0, 1
        };

        return new Mat4(data);
    }

    /**
     * Returns transposed rotation matrix around (0, 0, 1) vector.
     *
     * @param _angle Rotate angle in radians.
     * @return New instance of transposed rotation matrix around X axis ((0, 0, 1) vector).
     */
    static public Mat4 computeRotationZ(float _angle) {
        float cos = (float) Math.cos(_angle);
        float sin = (float) Math.sin(_angle);

        float[] data = new float[]{
                cos, sin, 0, 0,
                -sin, cos, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        };

        return new Mat4(data);
    }

    /**
     * Returns transposed rotation matrix around (x, y, z) vector.
     *
     * @param _angle Rotate angle in radians.
     * @return New instance of transposed rotation matrix around (x, y, z) vector.
     */
    static public Mat4 computeRotation(float _angle, float _x, float _y, float _z) {
        float cos = (float) Math.cos(_angle);
        float sin = (float) Math.sin(_angle);

        float[] data = new float[]{
                cos + _x * _x * (1 - cos), _y * _x * (1 - cos) + _z * sin, _z * _x * (1 - cos) - _y * sin, 0,
                _x * _y * (1 - cos) - _z * sin, cos + _y * _y * (1 - cos), _z * _y * (1 - cos) + _x * sin, 0,
                _x * _z * (1 - cos) + _y * sin, _y * _z * (1 - cos) - _x * sin, cos + _z * _z * (1 - cos), 0,
                0, 0, 0, 1
        };

        return new Mat4(data);
    }

    /**
     * Alias for Mat4.computeRotation(angle, vec3.x, vec3.y, vec3.z).
     *
     * @param _vec3 Rotation vector.
     * @return New instance of transposed rotation matrix around (x, y, z) vector.
     */
    static public Mat4 computeRotation(float _angle, Vec3 _vec3) {
        return computeRotation(_angle, _vec3.x, _vec3.y, _vec3.z);
    }
}
