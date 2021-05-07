package ru.peytob.mineville.math;

public class CoordinatesUtils {
    static public int convert3dTo1d(int x, int y, int z, int w, int h) {
        return x + w * (y + h * z);
    }

    static public int convert3dTo1d(Vec3i _coordinates, int w, int h) {
        return convert3dTo1d(_coordinates.x, _coordinates.y, _coordinates.z, w, h);
    }


    static public int convert2dTo1d(int x, int y, int w) {
        return x + y * w;
    }
}
