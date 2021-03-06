package ru.peytob.mineville.system;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long pointer;

    public Window(String _title, int width, int height) throws RuntimeException {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        pointer = glfwCreateWindow(width, height, _title, NULL, NULL);
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

    /**
     * Polls all events.
     */
    public void pollEvents() {
        glfwPollEvents();
    }

    public long getPointer() {
        return pointer;
    }
}
