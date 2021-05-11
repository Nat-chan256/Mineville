package ru.peytob.mineville.graphic.texture;

import org.lwjgl.opengl.GL33;

public class Texture
{
    protected int m_id;

    public Texture(Image _image)
    {
        m_id = GL33.glGenTextures();

        GL33.glBindTexture(GL33.GL_TEXTURE_2D, m_id);
        GL33.glPixelStorei(GL33.GL_UNPACK_ALIGNMENT, 1);

        GL33.glTexParameterf(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MIN_FILTER, GL33.GL_NEAREST);
        GL33.glTexParameterf(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MAG_FILTER, GL33.GL_NEAREST);

        GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGBA, _image.getSizes().x, _image.getSizes().y,
                0, GL33.GL_RGBA, GL33.GL_UNSIGNED_BYTE, _image.getBuffer());

        GL33.glGenerateMipmap(GL33.GL_TEXTURE_2D);
    }

    public void destroy()
    {
        GL33.glDeleteTextures(m_id);
    }

    public int getId()
    {
        return m_id;
    }
}