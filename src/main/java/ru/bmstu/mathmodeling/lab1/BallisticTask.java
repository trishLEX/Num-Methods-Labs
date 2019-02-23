package ru.bmstu.mathmodeling.lab1;

import ru.bmstu.common.Drawer;

import java.util.ArrayList;
import java.util.List;

public class BallisticTask {
    private static final double V_0 = 50;
    private static final double G = 9.8;
    private static final double ALPHA = Math.PI / 4;
    private static final double RADIUS = 1;
    private static final double DT = 0.01;
    private static final double BETA = 0.5 * 0.15 * 4 * RADIUS * RADIUS * Math.PI * 1.29;
    private static final double DENSITY = 11340;

    public static void main(String[] args) {
        galileo();
        newton(BETA, DENSITY);
    }

    private static double x(double t) {
        return V_0 * Math.cos(ALPHA) * t;
    }

    private static double y(double t) {
        return V_0 * Math.sin(ALPHA) * t - G * t * t / 2;
    }

    private static void galileo() {
        double t = 0;
        double x = 0;
        double y = 0;
        double xLast;
        double yLast;
        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();
        do {
            t += DT;

            xLast = x;
            yLast = y;

            xs.add(xLast);
            ys.add(yLast);

            y = y(t);
            x = x(t);
        } while (y >= 0);

        if (y < 0) {
            x = interpolateY(yLast, y, xLast, x);
        }

        xs.add(x);
        ys.add(0.0);

        System.out.println("Galileo: " + x);

        Drawer drawer = new Drawer("galileo", xs, ys, "x", "y");
        drawer.draw();
    }

    private static double speedX(double beta, double m, double speedX, double speedY) {
        return - beta * speedX * Math.sqrt(Math.pow(speedX, 2) + Math.pow(speedY, 2)) / m;
    }

    private static double speedY(double beta, double m, double speedX, double speedY) {
        return (- G - beta * speedY * Math.sqrt(Math.pow(speedX, 2) + Math.pow(speedY, 2)) / m);
    }

    private static void newton(double beta, double density) {
        double x = 0;
        double y = 0;
        double speedX = V_0 * Math.cos(ALPHA);
        double speedY = V_0 * Math.sin(ALPHA);
        double m = 4.0 / 3.0 * Math.PI * Math.pow(RADIUS, 3) * density;

        double xLast = x;
        double yLast = y;

        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();

        while (y >= 0) {
            double dSpeedX1 = DT * speedX(beta, m, speedX, speedY);
            double dSpeedY1 = DT * speedY(beta, m, speedX, speedY);
            double dx1 = DT * speedX;
            double dy1 = DT * speedY;

            double dSpeedX2 = DT * speedX(beta, m, speedX + dSpeedX1 / 2.0, speedY + dSpeedY1 / 2.0);
            double dSpeedY2 = DT * speedY(beta, m, speedX + dSpeedX1 / 2.0, speedY + dSpeedY1 / 2.0);
            double dx2 = DT * (speedX + dSpeedX1 / 2.0);
            double dy2 = DT * (speedY + dSpeedY1 / 2.0);

            double dSpeedX3 = DT * speedX(beta, m, speedX + dSpeedX2 / 2.0, speedY + dSpeedY2 / 2.0);
            double dSpeedY3 = DT * speedY(beta, m, speedX + dSpeedX2 / 2.0, speedY + dSpeedY2 / 2.0);
            double dx3 = DT * (speedX + dSpeedX2 / 2.0);
            double dy3 = DT * (speedY + dSpeedY2 / 2.0);

            double dSpeedX4 = DT * speedX(beta, m, speedX + dSpeedX3, speedY + dSpeedY3);
            double dSpeedY4 = DT * speedY(beta, m, speedX + dSpeedX3, speedY + dSpeedY3);
            double dx4 = DT * (speedX + dSpeedX3);
            double dy4 = DT * (speedY + dSpeedY3);

            speedX += (dSpeedX1 + 2.0 * (dSpeedX2 + dSpeedX3) + dSpeedX4) / 6.0;
            speedY += (dSpeedY1 + 2.0 * (dSpeedY2 + dSpeedY3) + dSpeedY4) / 6.0;

            xLast = x;
            yLast = y;

            xs.add(xLast);
            ys.add(yLast);

            x += (dx1 + 2.0 * (dx2 + dx3) + dx4) / 6.0;
            y += (dy1 + 2.0 * (dy2 + dy3) + dy4) / 6.0;
        }

        if (y < 0) {
            x = interpolateY(yLast, y, xLast, x);
        }

        xs.add(x);
        ys.add(0.0);

        System.out.println("Newton:  " + x);

        Drawer drawer = new Drawer("newton", xs, ys, "x", "y");
        drawer.draw();
    }

    private static double interpolateY(double y1, double y2, double x1, double x2) {
        double k = y1 / (y1 - y2);
        return x1 + (x2 - x1) * k;
    }
}
