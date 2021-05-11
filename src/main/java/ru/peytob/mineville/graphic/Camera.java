package ru.peytob.mineville.graphic;

import ru.peytob.mineville.math.Mat4;
import ru.peytob.mineville.math.Vec3;

public class Camera {
    private final float fov;

    private Vec3 position;

    private float yaw;

    private float pitch;

    private final float aspect;

    private Vec3 front;

    private Vec3 right;

    public Camera(Vec3 _position, float _pith, float _yaw, float _fov, float _aspect) {
        this.position = _position;
        this.yaw = _yaw;
        this.fov = _fov;
        this.aspect = _aspect;
        this.pitch = _pith;
    }

    public Mat4 computeProjection() {
        return Mat4.computePerspective(fov, aspect, 0.1f, 128.0f);
    }

    public Mat4 computeView() {
        float pitchCos = (float) Math.cos(pitch);

        front = new Vec3(
                (float) Math.cos(yaw) * pitchCos,
                (float) Math.sin(pitch),
                (float) Math.sin(yaw) * pitchCos
        ).normalize();

        Vec3 worldUp = new Vec3(0, 1, 0);
        right = front.vectorMultiplication(worldUp).normalize();

        Vec3 up = right.vectorMultiplication(front);
        up = up.normalize();

        return Mat4.computeLookAt(position, position.plus(front), up);
    }

    public void lookAround(float xOffset, float yOffset)
    {
        yaw += Math.toRadians(xOffset);
        pitch += Math.toRadians(yOffset);
        float borderAngle = (float) Math.toRadians(89.0f);

        if (pitch > borderAngle) {
            pitch = borderAngle;
        }

        else if (pitch < -borderAngle) {
            pitch = -borderAngle;
        }
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public Vec3 getFront() {
        return front;
    }

    public Vec3 getRight() {
        return right;
    }

    public void move(Vec3 _offset) {
        position = position.plus(_offset);
    }
}
