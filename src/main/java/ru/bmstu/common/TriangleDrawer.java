package ru.bmstu.common;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import ru.bmstu.mathmodeling.lab2.Circle;
import ru.bmstu.mathmodeling.lab2.Point;
import ru.bmstu.mathmodeling.lab2.Triangle;
import ru.bmstu.mathmodeling.lab2.Triangulation;

import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static ru.bmstu.mathmodeling.lab2.Main.WINDOW_SIZE;

public class TriangleDrawer extends Drawer {
    private static final int NUMBER_OF_SIDES = 50;
    private static final double POINT_SIZE = 0.01;
    private static final double SIZE = WINDOW_SIZE / 2.0;

    private List<Point> points;
    private Triangulation triangulation;
    private Thread triangulationThread;

    private boolean toDrawCircles;
    private boolean toDrawRedCircles;
    private boolean toDrawPoints;

    public TriangleDrawer(int width, int height, Triangulation triangulation, List<Point> points) {
        super(width, height, "triangles");

        this.triangulation = triangulation;
        this.points = points;

        toDrawCircles = false;
        toDrawRedCircles = false;
        toDrawPoints = false;

        glfwSetKeyCallback(window, GLFWKeyCallback.create(((window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_C && action == GLFW_PRESS) {
                toDrawCircles = !toDrawCircles;
            } else if (key == GLFW_KEY_R && action == GLFW_PRESS) {
                toDrawRedCircles = !toDrawRedCircles;
            } else if (key == GLFW_KEY_P && action == GLFW_PRESS) {
                toDrawPoints = !toDrawPoints;
            } else if (key == GLFW_KEY_SPACE && action == GLFW_PRESS) {
                synchronized (triangulation) {
                    triangulation.notify();
                }
            }
        })));
    }

    @Override
    public void draw() {
        triangulationThread = new Thread(() -> triangulation.triangulate());
        triangulationThread.start();

        while (!GLFW.glfwWindowShouldClose(this.window)) {
            background();

            drawTriangles();

            if (toDrawPoints) {
                drawPoints();
            }

            if (toDrawCircles) {
                drawCircles();
            }

            GLFW.glfwSwapBuffers(this.window);
            GLFW.glfwPollEvents();
        }

        triangulationThread.interrupt();
    }

    private void drawTriangles() {
        glColor3dv(BLACK);
        glLineWidth(2);
        for (Triangle triangle : triangulation.getTriangles()) {
            glBegin(GL_LINE_LOOP);
            glVertex2d((triangle.getFirst().getX() - SIZE) / SIZE, (triangle.getFirst().getY() - SIZE) / SIZE);
            glVertex2d((triangle.getSecond().getX() - SIZE) / SIZE, (triangle.getSecond().getY() - SIZE) / SIZE);
            glVertex2d((triangle.getThird().getX() - SIZE) / SIZE, (triangle.getThird().getY() - SIZE) / SIZE);
            glEnd();
        }
    }

    private void drawCircles() {
        glLineWidth(1);
        for (Circle circle : triangulation.getCircles()) {
            if (!(!toDrawRedCircles && Arrays.equals(circle.getColor(), RED))) {
                glColor3dv(circle.getColor());
                glBegin(GL_LINE_LOOP);
                for (int i = 0; i < NUMBER_OF_SIDES; i++) {
                    glVertex2d(
                            (circle.getCenter()[0] - SIZE) / SIZE + circle.getRadius() / SIZE * Math.cos(i * Math.PI * 2 / NUMBER_OF_SIDES),
                            (circle.getCenter()[1] - SIZE) / SIZE + circle.getRadius() / SIZE * Math.sin(i * Math.PI * 2 / NUMBER_OF_SIDES)
                    );
                }
                glEnd();
            }
        }
    }

    private void drawPoints() {
        glColor3dv(RED);
        for (Point point : points) {
            double x = (point.getX() - 360) / SIZE;
            double y = (point.getY() - 360) / SIZE;
            glBegin(GL_POLYGON);
            glVertex2d(x + POINT_SIZE, y + POINT_SIZE);
            glVertex2d(x - POINT_SIZE, y + POINT_SIZE);
            glVertex2d(x - POINT_SIZE, y - POINT_SIZE);
            glVertex2d(x + POINT_SIZE, y - POINT_SIZE);
            glEnd();
        }
    }
}
