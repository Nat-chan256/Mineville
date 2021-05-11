package ru.peytob.mineville.graphic.texture;

import de.matthiasmann.twl.utils.PNGDecoder;
import ru.peytob.mineville.math.Vec2i;

import java.io.*;
import java.nio.ByteBuffer;

public class Image
{
    ByteBuffer m_buffer;
    Vec2i m_sizes;

    public Image(ByteBuffer _buffer, Vec2i _sizes)
    {
        m_buffer = _buffer;
        m_sizes = _sizes;
    }

    public Image(Vec2i _sizes)
    {
        m_buffer = ByteBuffer.allocateDirect(4 * _sizes.x * _sizes.y);
        for (int i = 0; i < m_buffer.remaining(); ++i)
            m_buffer.put(i, Byte.MAX_VALUE);

        m_sizes = _sizes;
    }

    Vec2i getSizes()
    {
        return m_sizes;
    }

    ByteBuffer getBuffer()
    {
        return m_buffer;
    }

    void insert(Vec2i _position, Image _image) throws ArrayIndexOutOfBoundsException
    {
        if (_position.x + _image.getSizes().x > getSizes().x || _position.y + _image.getSizes().y > getSizes().y)
            throw new ArrayIndexOutOfBoundsException("No enough space to place image.");

        final byte bytesPerPixel = 4;
        Vec2i dataPos = _position.multiplication(bytesPerPixel);
        Vec2i rightImageDataSizes = _image.getSizes().multiplication(bytesPerPixel);

        for (int tx = dataPos.x, fx = 0; fx < rightImageDataSizes.x; fx += bytesPerPixel, tx += bytesPerPixel)
            for (int ty = dataPos.y, fy = 0; fy < rightImageDataSizes.y; fy += bytesPerPixel, ty += bytesPerPixel)
            {
                int fromIndex = fx + fy * _image.getSizes().x;
                int toIndex = tx + ty * getSizes().x;
                m_buffer.put(toIndex, _image.m_buffer.get(fromIndex));
                m_buffer.put(toIndex + 1, _image.m_buffer.get(fromIndex + 1));
                m_buffer.put(toIndex + 2, _image.m_buffer.get(fromIndex + 2));
                m_buffer.put(toIndex + 3, _image.m_buffer.get(fromIndex + 3));
            }
    }

    public static Image loadFromPNGFile(String _filename) throws IOException
    {
        try (InputStream inStream = new FileInputStream(_filename))
        {
            PNGDecoder decoder = new PNGDecoder(inStream);

            ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, 4 * decoder.getWidth(), PNGDecoder.Format.RGBA);
            buf.flip();

            return new Image(buf, new Vec2i(decoder.getWidth(), decoder.getHeight()));
        }
    }
}