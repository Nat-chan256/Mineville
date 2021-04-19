package ru.peytob.mineville.system;

import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static  org.lwjgl.glfw.GLFW.*;

public class WindowCallbackSet {
    private GLFWCharCallbackI charCallback;
    private GLFWMouseButtonCallbackI mouseButtonCallback;
    private GLFWCursorPosCallbackI mouseMovingCallback;
    private GLFWKeyCallbackI keyCallback;

    public WindowCallbackSet() {
    }

    public void use(Window _window) {
        glfwFreeCallbacks(_window.getPointer());
        glfwSetCharCallback(_window.getPointer(), charCallback);
        glfwSetMouseButtonCallback(_window.getPointer(), mouseButtonCallback);
        glfwSetCursorPosCallback(_window.getPointer(), mouseMovingCallback);
        glfwSetKeyCallback(_window.getPointer(), keyCallback);
    }

    public void setCharCallback(GLFWCharCallbackI charCallback) {
        this.charCallback = charCallback;
    }

    public void setMouseButtonCallback(GLFWMouseButtonCallbackI mouseButtonCallback) {
        this.mouseButtonCallback = mouseButtonCallback;
    }

    public void setMouseMovingCallback(GLFWCursorPosCallbackI mouseMovingCallback) {
        this.mouseMovingCallback = mouseMovingCallback;
    }

    public void setKeyCallback(GLFWKeyCallbackI keyCallback) {
        this.keyCallback = keyCallback;
    }
}
