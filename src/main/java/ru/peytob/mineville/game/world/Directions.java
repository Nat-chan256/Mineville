package ru.peytob.mineville.game.world;

import ru.peytob.mineville.math.Vec3i;

public class Directions {
    static public final Vec3i north = new Vec3i(0, 0, 1);
    static public final Vec3i south = new Vec3i(0, 0, -1);
    static public final Vec3i west = new Vec3i(-1, 0, 0);
    static public final Vec3i east = new Vec3i(1, 0, 0);
    static public final Vec3i top = new Vec3i(0, 1, 0);
    static public final Vec3i bottom = new Vec3i(0, -1, 0);
}
