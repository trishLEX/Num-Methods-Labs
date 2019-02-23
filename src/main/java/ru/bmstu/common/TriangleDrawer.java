package ru.bmstu.common;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import ru.bmstu.mathmodeling.lab2.Circle;
import ru.bmstu.mathmodeling.lab2.Triangle;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TriangleDrawer extends Drawer {
    private static final int NUMBER_OF_SIDES = 50;

    private List<Triangle> triangles;
    private List<Circle> circles;

    private boolean toDrawCircles;

    public TriangleDrawer(List<Triangle> triangles, List<Circle> circles) {
        super(720, 720, "triangles");

        this.triangles = triangles;
        this.circles = circles;

        toDrawCircles = false;

        glfwSetKeyCallback(window, GLFWKeyCallback.create(((window1, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_C && action == GLFW_PRESS) {
                toDrawCircles = !toDrawCircles;
            }
        })));
    }

    @Override
    public void draw() {
        while (!GLFW.glfwWindowShouldClose(this.window)) {
            background();

            drawTriangles();

            if (toDrawCircles) {
                drawCircles();
            }

            GLFW.glfwSwapBuffers(this.window);
            GLFW.glfwPollEvents();
        }
    }

    private void drawTriangles() {
        glColor3dv(BLACK);
        glLineWidth(2);
        for (Triangle triangle : triangles) {
            glBegin(GL_LINE_LOOP);
            glVertex2d((double) (triangle.getFirst().getX() - 360) / 720, (double) (triangle.getFirst().getY() - 360) / 720);
            glVertex2d((double) (triangle.getSecond().getX() - 360) / 720, (double) (triangle.getSecond().getY() - 360) / 720);
            glVertex2d((double) (triangle.getThird().getX() - 360) / 720, (double) (triangle.getThird().getY() - 360) / 720);
            glEnd();
        }
    }

    private void drawCircles() {
        glColor3dv(GRAY);
        glLineWidth(1);
        for (Circle circle : circles) {
            glBegin(GL_LINE_LOOP);
            for (int i = 0; i < NUMBER_OF_SIDES; i++) {
                glVertex2d(
                        (circle.getCenter()[0] - 360) / 720 + circle.getRadius() / 720 * Math.cos(i * Math.PI * 2 / NUMBER_OF_SIDES),
                        (circle.getCenter()[1] - 360) / 720 + circle.getRadius() / 720 * Math.sin(i * Math.PI * 2 / NUMBER_OF_SIDES)
                );
            }
            glEnd();
        }
    }
}
