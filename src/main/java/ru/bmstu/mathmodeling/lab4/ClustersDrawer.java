package ru.bmstu.mathmodeling.lab4;

import ru.bmstu.common.Drawer;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class ClustersDrawer extends Drawer {
    private static final double POINT_SIZE = 0.01;
    private List<double[]> colors;
    private List<Cluster> clusters;

    public ClustersDrawer(List<Cluster> clusters, int size) {
        super(size, size, "clusters");

        this.clusters = clusters;

        if (clusters.size() == 1) {
            colors = List.of(RED);
        } else if (clusters.size() == 2) {
            colors = List.of(RED, BLUE);
        } else if (clusters.size() == 3) {
            colors = List.of(RED, GREEN, BLUE);
        } else if (clusters.size() == 4) {
            colors = List.of(RED, GREEN, BLUE, YELLOW);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    protected void drawBody() {
        for (int i = 0; i < clusters.size(); i++) {
            glColor3dv(colors.get(i));
            for (Point point : clusters.get(i).getPoints()) {
                double x = point.getX();
                double y = point.getY();

                drawPoint(x, y);
            }

            glColor3dv(BLACK);
            drawPoint(clusters.get(i).getCenter());
        }
    }

    private void drawPoint(Point point) {
        drawPoint(point.getX(), point.getY());
    }

    private void drawPoint(double x, double y) {
        glBegin(GL_POLYGON);
        glVertex2d(x + POINT_SIZE, y + POINT_SIZE);
        glVertex2d(x - POINT_SIZE, y + POINT_SIZE);
        glVertex2d(x - POINT_SIZE, y - POINT_SIZE);
        glVertex2d(x + POINT_SIZE, y - POINT_SIZE);
        glEnd();
    }
}
