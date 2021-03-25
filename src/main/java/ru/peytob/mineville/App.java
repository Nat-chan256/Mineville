package ru.peytob.mineville;

import org.lwjgl.glfw.GLFWErrorCallback;
import ru.peytob.mineville.system.Window;

import static org.lwjgl.glfw.GLFW.glfwInit;

class App {
    public static void main(String[] args) {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new RuntimeException("GLFW: Init error!");

        Window window = new Window("Mineville", 800, 600);

        while (!window.isClose()) {
            window.pollEvents();

            window.display();
        }
    }
}
