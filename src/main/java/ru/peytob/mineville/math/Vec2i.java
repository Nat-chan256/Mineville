package ru.peytob.mineville.math;

/**
 * Used for two-dimensional vector representation and performance operations on the one.
 * The coordinates of the vector are of int type.
 */
public final class Vec2i
{
    /**
     * The vector's X coordinate.
     */
    public int x;

    /**
     * The vector's Y coordinate.
     */
    public int y;

    /**
     * Constructor which sets both vector's coordinates.
     *
     * @param _x The value of X coordinate
     * @param _y The value of Y coordinate
     */
    public Vec2i(int _x, int _y)
    {
        x = _x;
        y = _y;
    }

    /* Arithmetical operations. */

    /**
     * Retrieving a backward vector (-v).
     * @return A backward vector.
     */
    public Vec2i negative()
    {
        return new Vec2i(-x, -y);
    }

    /**
     * Vectors addition.
     * @param _right Right operand.
     * @return The result of two vectors addition.
     */
    public Vec2i plus(Vec2i _right)
    {
        return new Vec2i(x + _right.x, y + _right.y);
    }

    /**
     * Vector and scalar addition.
     * @param _right Right operand.
     * @return The result of vector and scalar addition.
     */
    public Vec2i plus(int _right)
    {
        return new Vec2i(x + _right, y + _right);
    }

    /**
     * Vectors subtraction.
     * @param _right Right operand.
     * @return Two vectors subtraction result.
     */
    public Vec2i minus(Vec2i _right)
    {
        return new Vec2i(x - _right.x, y - _right.y);
    }

    /**
     * Vector and scalar subtraction.
     * @param _right Right operand.
     * @return Vector and scalar subtraction result.
     */
    public Vec2i minus(int _right)
    {
        return new Vec2i(x - _right, y - _right);
    }

    /**
     * Multiplying the vector by a number.
     * @param _right Right operand.
     * @return Multiplying the vector by a number result.
     */
    public Vec2i multiplication(int _right)
    {
        return new Vec2i(x * _right, y * _right);
    }

    /**
     * Scalar multiplication of vectors.
     * @param _right Right operand.
     * @return Multiplication result.
     */
    public Vec2i scalarMultiplication(Vec2i _right)
    {
        return new Vec2i(x * _right.x, y * _right.y);
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
