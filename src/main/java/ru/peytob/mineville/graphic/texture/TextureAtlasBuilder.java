package ru.peytob.mineville.graphic.texture;

import ru.peytob.mineville.math.Vec2i;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

public class TextureAtlasBuilder
{
    private final HashMap<String, Image> m_images = new HashMap<>();
    private final int m_tileSize;

    public TextureAtlasBuilder(int _tileSize)
    {
        m_tileSize = _tileSize;
    }

    public void addImage(String _identifier, Image _image) throws RuntimeException
    {
        if (m_images.containsKey(_identifier))
            throw new RuntimeException("Key " + _identifier + " is already in texture atlas.");

        if (_image.getSizes().x != _image.getSizes().y && _image.getSizes().x != m_tileSize)
            throw new IllegalArgumentException("Image is not tile. It's sizes should be "
                    + m_tileSize + " x " + m_tileSize + ".");

        m_images.put(_identifier, _image);
    }

    public TextureAtlas computeAtlas()
    {
        double sqrtValue = Math.sqrt(m_images.size());
        int sqrtValueInt = (int) sqrtValue;
        Vec2i imageSizeTiles;

        if (sqrtValue == sqrtValueInt)
            imageSizeTiles = new Vec2i(sqrtValueInt, sqrtValueInt);

        else
        {
            int w = sqrtValueInt + 1;
            int h = m_images.size() / w + 1;

            imageSizeTiles = new Vec2i(w, h);
        }

        Image image = new Image(imageSizeTiles.multiplication(m_tileSize));
        TreeMap<String, Vec2i> imagesPositions = new TreeMap<>();

        AtomicReference<Integer> counter = new AtomicReference<>(0);
        m_images.forEach((key, value) ->
        {
            Vec2i position = new Vec2i(counter.get() % imageSizeTiles.x, counter.get() / imageSizeTiles.x);
            image.insert(position.multiplication(m_tileSize), value);
            imagesPositions.put(key, position);
            counter.getAndSet(counter.get() + 1);
        });

        return new TextureAtlas(image, imagesPositions, m_tileSize);
    }
}