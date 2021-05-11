package ru.peytob.mineville.graphic.texture;

import ru.peytob.mineville.math.Vec2;
import ru.peytob.mineville.math.Vec2i;

import java.util.TreeMap;

public final class TextureAtlas extends Texture
{
    private final TreeMap<String, Vec2i> m_map;
    private final int m_tileSize;
    private final Vec2 m_tileSizesAbs;

    protected TextureAtlas(Image _image, TreeMap<String, Vec2i> _map, int _tileSize)
    {
        super(_image);

        m_tileSize = _tileSize;
        m_map = _map;
        m_tileSizesAbs = new Vec2((float) _tileSize / _image.getSizes().x, (float) _tileSize / _image.getSizes().y);
    }

    public Vec2i getTileIndex(String _tileName) throws IllegalArgumentException
    {
        Vec2i position = m_map.get(_tileName);

        if (position == null)
            throw new IllegalArgumentException("Tile " + _tileName + " is not found in atlas.");

        return position;
    }

    public Vec2 getTilePosition(String _tileName) throws IllegalArgumentException
    {
        return positionToIndex(getTileIndex(_tileName));
    }

    public Vec2 positionToIndex(Vec2i _index)
    {
        return new Vec2(_index.x * m_tileSizesAbs.x, 1.0f - _index.y * m_tileSizesAbs.y);
    }

    public int getTileSizePx()
    {
        return m_tileSize;
    }

    public Vec2 getTileSizeAbs()
    {
        return m_tileSizesAbs;
    }
}
