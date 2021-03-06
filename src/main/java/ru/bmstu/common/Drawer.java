package ru.bmstu.common;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public abstract class Drawer {
    public static final double[] GRAY  = {0.8, 0.8, 0.8};
    public static final double[] BLACK = {0.0, 0.0, 0.0};
    public static final double[] RED = {1.0, 0.0, 0.0};
    public static final double[] GREEN = {0.0, 1.0, 0.0};
    public static final double[] BLUE = {0.0, 0.0, 1.0};
    public static final double[] YELLOW = {1.0, 1.0, 0.0};

    protected long window;

    protected void background() {
        glClearColor(1, 1, 1, 0);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_DEPTH_TEST);
    }

    protected void drawBody() {
        //do nothing
    }

    public Drawer(int width, int height, String name) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        this.window = GLFW.glfwCreateWindow(width, height, name, 0, 0);

        if (window == 0) {
            throw new IllegalStateException("Failed to create window");
        }

        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();
    }

    public void draw(){
        while (!GLFW.glfwWindowShouldClose(this.window)) {
            background();
            drawBody();

            GLFW.glfwSwapBuffers(this.window);
            GLFW.glfwPollEvents();
        }
    }
}
