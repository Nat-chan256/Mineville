package ru.peytob.mineville.graphic.texture;

import ru.peytob.mineville.math.Vec2;

public class Material
{
    private final Vec2 m_frontTexture;
    private final Vec2 m_rightTexture;
    private final Vec2 m_leftTexture;
    private final Vec2 m_backTexture;
    private final Vec2 m_topTexture;
    private final Vec2 m_bottomTexture;

    public Material(Vec2 _frontTile, Vec2 _rightTile, Vec2 _leftTile, Vec2 _backTile, Vec2 _topTile, Vec2 _bottomTile)
    {
        m_frontTexture = _frontTile;
        m_rightTexture = _rightTile;
        m_leftTexture = _leftTile;
        m_backTexture = _backTile;
        m_topTexture = _topTile;
        m_bottomTexture = _bottomTile;
    }

    public Vec2 getFrontTexture()
    {
        return m_frontTexture;
    }

    public Vec2 getRightTexture()
    {
        return m_rightTexture;
    }

    public Vec2 getLeftTexture()
    {
        return m_leftTexture;
    }

    public Vec2 getBackTexture()
    {
        return m_backTexture;
    }

    public Vec2 getTopTexture()
    {
        return m_topTexture;
    }

    public Vec2 getBottomTexture()
    {
        return m_bottomTexture;
    }

}