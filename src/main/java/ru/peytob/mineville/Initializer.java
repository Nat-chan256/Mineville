package ru.peytob.mineville;

import org.lwjgl.glfw.GLFWErrorCallback;
import ru.peytob.mineville.game.main.Application;

import javax.management.RuntimeErrorException;

import static org.lwjgl.glfw.GLFW.glfwInit;

class Initializer {
    public static void main(String[] args) {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new RuntimeErrorException(new Error("Critical error while initializing game: " +
                    "glfw library not initialized!"));
        }

        Application.initialize();
        Application.run();
        Application.destroy();
    }
}
