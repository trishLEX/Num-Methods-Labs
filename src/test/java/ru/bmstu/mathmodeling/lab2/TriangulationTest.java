package ru.bmstu.mathmodeling.lab2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TriangulationTest {
    private List<Point> points;

    @BeforeEach
    public void before() {
        points = new ArrayList<>();
    }

    @Test
    public void test5Points1() {
        points.add(new Point(185, 72));
        points.add(new Point(52, 482));
        points.add(new Point(560, 424));
        points.add(new Point(476, 631));
        points.add(new Point(600, 702));
    }

    @Test
    public void test5Points2() {
        points.add(new Point(339, 16));
        points.add(new Point(503, 145));
        points.add(new Point(494, 472));
        points.add(new Point(228, 545));
        points.add(new Point(334, 660));
    }

    @Test
    public void test5Points3() {
        points.add(new Point(332, 32));
        points.add(new Point(109, 279));
        points.add(new Point(394, 293));
        points.add(new Point(618, 484));
        points.add(new Point(243, 715));
    }

    @Test
    public void test7Points1() {
        points.add(new Point(185, 48));
        points.add(new Point(505, 79));
        points.add(new Point(310, 148));
        points.add(new Point(226, 348));
        points.add(new Point(346, 374));
        points.add(new Point(641, 298));
        points.add(new Point(702, 589));
    }

    @Test
    public void test7Points2() {
        points.add(new Point(364, 17));
        points.add(new Point(233, 330));
        points.add(new Point(446, 275));
        points.add(new Point(594, 204));
        points.add(new Point(670, 376));
        points.add(new Point(46, 648));
        points.add(new Point(580, 563));
    }
    
    @Test
    public void test7Points3() {
        points.add(new Point(3, 125));
        points.add(new Point(392, 292));
        points.add(new Point(719, 32));
        points.add(new Point(579, 264));
        points.add(new Point(649, 489));
        points.add(new Point(115, 620));
        points.add(new Point(430, 701));
    }

    @Test
    public void test10Points1() {
        points.add(new Point(39, 122));
        points.add(new Point(505, 49));
        points.add(new Point(294, 163));
        points.add(new Point(97, 362));
        points.add(new Point(187, 464));
        points.add(new Point(486, 266));
        points.add(new Point(577, 105));
        points.add(new Point(548, 289));
        points.add(new Point(45, 670));
        points.add(new Point(408, 629));
    }
    
    @Test
    public void test15Points1() {
        points.add(new Point(128, 60));
        points.add(new Point(57, 233));
        points.add(new Point(451, 48));
        points.add(new Point(332, 148));
        points.add(new Point(458, 227));
        points.add(new Point(176, 364));
        points.add(new Point(242, 387));
        points.add(new Point(325, 356));
        points.add(new Point(379, 429));
        points.add(new Point(677, 95));
        points.add(new Point(679, 204));
        points.add(new Point(657, 391));
        points.add(new Point(136, 680));
        points.add(new Point(523, 571));
        points.add(new Point(585, 656));
    }

    @AfterEach
    public void after() {
        Main.showTriangulation(points);
    }
}
