package ru.bmstu.mathmodeling.lab2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {
    @Test
    public void getDistanceToSegmentTest1() {
        Point point = new Point(1, -2);
        Point start = new Point(0, 0);
        Point end = new Point(2, 0);

        assertEquals(2.0, Utils.getDistanceToSegment(point, start, end));
    }

    @Test
    public void getDistanceToSegmentTest2() {
        Point point = new Point(1, -2);
        Point start = new Point(1, 0);
        Point end = new Point(2, 0);

        assertEquals(2.0, Utils.getDistanceToSegment(point, start, end));
    }

    @Test
    public void getDistanceToSegmentTest3() {
        Point point = new Point(1, -2);
        Point start = new Point(1, 0);
        Point end = new Point(2, 0);

        assertEquals(2.0, Utils.getDistanceToSegment(point, end, start));
    }

    @Test
    public void getDistanceToSegmentTest4() {
        Point point = new Point(0, -2);
        Point start = new Point(1, 0);
        Point end = new Point(2, 0);

        assertEquals(Math.sqrt(1 + 4), Utils.getDistanceToSegment(point, start, end));
    }

    @Test
    public void getCircumCenterTest1() {
        Point a = new Point(0, 0);
        Point b = new Point(2, 0);
        Point c = new Point(0, 2);

        assertArrayEquals(new double[]{1, 1}, Utils.getCircumcenter(a, b, c));
    }

    @Test
    public void getCircumCenterTest2() {
        Point a = new Point(38, 155);
        Point b = new Point(50, 345);
        Point c = new Point(505, 344);

        assertArrayEquals(new double[]{277.25992921745967, 235.26779394416045}, Utils.getCircumcenter(a, b, c));
    }
}
