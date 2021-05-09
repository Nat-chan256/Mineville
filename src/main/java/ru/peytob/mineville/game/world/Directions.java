package ru.peytob.mineville.game.world;

import ru.peytob.mineville.math.Vec3;

public class Directions {
    public final Vec3 north = new Vec3(0.0f, 0.0f, 1.0f);
    public final Vec3 south = new Vec3(0.0f, 0.0f, -1.0f);
    public final Vec3 west = new Vec3(-1.0f, 0.0f, 0.0f);
    public final Vec3 east = new Vec3(1.0f, 0.0f, 0.0f);
    public final Vec3 top = new Vec3(0.0f, 1.0f, 0.0f);
    public final Vec3 bottom = new Vec3(0.0f, -1.0f, 0.0f);
}
