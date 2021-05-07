package ru.peytob.mineville.system;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryStack;
import ru.peytob.mineville.math.Vec2;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    /**
     * 64 bits pointer to window object in memory.
     */
    private final long pointer;

    public Window(String _title, int _width, int _height) throws RuntimeException {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        pointer = glfwCreateWindow(_width, _height, _title, NULL, NULL);
        if (pointer == NULL) {
            throw new RuntimeException("Window is not created.");
        }

        glfwMakeContextCurrent(pointer);
        glfwShowWindow(pointer);

        glfwSetInputMode(pointer, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        GL.createCapabilities();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer w = stack.mallocInt(1);
            final IntBuffer h = stack.mallocInt(1);
            glfwGetFramebufferSize(pointer, w, h);
            int contentWidth = w.get();
            int contentHeight = h.get();
            GL33.glViewport(0, 0, contentWidth, contentHeight);
        }
    }

    /**
     * Makes window context current.
     */
    public void makeCurrent() {
        glfwMakeContextCurrent(pointer);
    }

    /**
     * Destroys window and free all resources.
     */
    public void destroy() {
        glfwFreeCallbacks(pointer);
        glfwDestroyWindow(pointer);
    }

    /**
     * Swaps screen buffers and display some stuff.
     */
    public void display() {
        glfwSwapBuffers(pointer);
    }

    /**
     * Checks if the window is open.
     * @return True, if window should close.
     */
    public boolean isClose() {
        return glfwWindowShouldClose(pointer);
    }

    public void close() {
        glfwSetWindowShouldClose(pointer, true);
    }

    /**
     * Polls all events.
     */
    public void pollEvents() {
        glfwPollEvents();
    }

    /**
     * Returns pointer to window.
     * @return Pointer to window
     */
    public long getPointer() {
        return pointer;
    }

    /**
     * Returns cursor position relative to the content area.
     * @return Cursor position relative to the content area.
     */
    public Vec2 getCursorPosition() {
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            final DoubleBuffer width = stack.mallocDouble(1);
            final DoubleBuffer height = stack.mallocDouble(1);
            glfwGetCursorPos(pointer, width, height);
            return new Vec2((float) width.get(0), (float) height.get(0));
        }
    }
}
