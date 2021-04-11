package ru.peytob.mineville.math;

import java.util.Arrays;

public final class Mat4 {
    private float[] data = new float[16];

    public Mat4() {

    }

    public Mat4(float[] _data) throws NumberFormatException {
        if (_data.length != 16)
            throw new NumberFormatException("Sizes of _data array should be 16 (4 x 4).");

        data = _data.clone();
    }

    public Mat4(Mat4 _other) {
        data = _other.data.clone();
    }

    public float get(int _index) throws IndexOutOfBoundsException {
        return data[_index];
    }

    public float get(int _row, int _column) throws IndexOutOfBoundsException {
        return get(_row * 4 + _column);
    }

    public void set(int _index, float _data) throws IndexOutOfBoundsException {
        data[_index] = _data;
    }

    public void set(int _row, int _column, float _data) throws IndexOutOfBoundsException {
        set(_row * 4 + _column, _data);
    }

    public Mat4 plus(Mat4 _right) {
        Mat4 result = new Mat4();
        for (int i = 0; i < data.length; ++i)
            result.set(i, get(i) + _right.get(i));

        return result;
    }

    public Mat4 minus(Mat4 _right) {
        Mat4 result = new Mat4();
        for (int i = 0; i < data.length; ++i)
            result.set(i, get(i) - _right.get(i));

        return result;
    }

    public Mat4 multiplication(Mat4 _right) {
        float[] resultArray = new float[]{
                _right.get(0, 0) * get(0, 0) + _right.get(1, 0) * get(0, 1) +
                        _right.get(2, 0) * get(0, 2) + _right.get(3, 0) * get(0, 3),
                _right.get(0, 1) * get(0, 0) + _right.get(1, 1) * get(0, 1) +
                        _right.get(2, 1) * get(0, 2) + _right.get(3, 1) * get(0, 3),
                _right.get(0, 2) * get(0, 0) + _right.get(1, 2) * get(0, 1) +
                        _right.get(2, 2) * get(0, 2) + _right.get(3, 2) * get(0, 3),
                _right.get(0, 3) * get(0, 0) + _right.get(1, 3) * get(0, 1) +
                        _right.get(2, 3) * get(0, 2) + _right.get(3, 3) * get(0, 3),
                _right.get(0, 0) * get(1, 0) + _right.get(1, 0) * get(1, 1) +
                        _right.get(2, 0) * get(1, 2) + _right.get(3, 0) * get(1, 3),
                _right.get(0, 1) * get(1, 0) + _right.get(1, 1) * get(1, 1) +
                        _right.get(2, 1) * get(1, 2) + _right.get(3, 1) * get(1, 3),
                _right.get(0, 2) * get(1, 0) + _right.get(1, 2) * get(1, 1) +
                        _right.get(2, 2) * get(1, 2) + _right.get(3, 2) * get(1, 3),
                _right.get(0, 3) * get(1, 0) + _right.get(1, 3) * get(1, 1) +
                        _right.get(2, 3) * get(1, 2) + _right.get(3, 3) * get(1, 3),
                _right.get(0, 0) * get(2, 0) + _right.get(1, 0) * get(2, 1) +
                        _right.get(2, 0) * get(2, 2) + _right.get(3, 0) * get(2, 3),
                _right.get(0, 1) * get(2, 0) + _right.get(1, 1) * get(2, 1) +
                        _right.get(2, 1) * get(2, 2) + _right.get(3, 1) * get(2, 3),
                _right.get(0, 2) * get(2, 0) + _right.get(1, 2) * get(2, 1) +
                        _right.get(2, 2) * get(2, 2) + _right.get(3, 2) * get(2, 3),
                _right.get(0, 3) * get(2, 0) + _right.get(1, 3) * get(2, 1) +
                        _right.get(2, 3) * get(2, 2) + _right.get(3, 3) * get(2, 3),
                _right.get(0, 0) * get(3, 0) + _right.get(1, 0) * get(3, 1) +
                        _right.get(2, 0) * get(3, 2) + _right.get(3, 0) * get(3, 3),
                _right.get(0, 1) * get(3, 0) + _right.get(1, 1) * get(3, 1) +
                        _right.get(2, 1) * get(3, 2) + _right.get(3, 1) * get(3, 3),
                _right.get(0, 2) * get(3, 0) + _right.get(1, 2) * get(3, 1) +
                        _right.get(2, 2) * get(3, 2) + _right.get(3, 2) * get(3, 3),
                _right.get(0, 3) * get(3, 0) + _right.get(1, 3) * get(3, 1) +
                        _right.get(2, 3) * get(3, 2) + _right.get(3, 3) * get(3, 3)
        };

        return new Mat4(resultArray);
    }

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
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                builder.append(get(i, j));
                builder.append(", ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
