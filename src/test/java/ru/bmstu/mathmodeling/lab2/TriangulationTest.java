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
    public void test7Points4() {
        points.add(new Point(317, 219));
        points.add(new Point(93, 256));
        points.add(new Point(264, 343));
        points.add(new Point(294, 490));
        points.add(new Point(662, 235));
        points.add(new Point(667, 383));
        points.add(new Point(546, 576));
    }

    @Test
    public void test7Points5() {
        //TODO
        points.add(new Point(221, 77));
        points.add(new Point(100, 158));
        points.add(new Point(246, 253));
        points.add(new Point(466, 112));
        points.add(new Point(413, 226));
        points.add(new Point(121, 293));
        points.add(new Point(26, 477));
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
    
    @Test
    public void test15Points2() {
        points.add(new Point(168, 6));
        points.add(new Point(305, 146));
        points.add(new Point(286, 247));
        points.add(new Point(40, 317));
        points.add(new Point(90, 451));
        points.add(new Point(393, 338));
        points.add(new Point(349, 435));
        points.add(new Point(464, 385));
        points.add(new Point(523, 7));
        points.add(new Point(598, 112));
        points.add(new Point(656, 378));
        points.add(new Point(715, 334));
        points.add(new Point(50, 675));
        points.add(new Point(533, 574));
        points.add(new Point(687, 656));
    }
    
    @Test
    public void test15Points3() {
        points.add(new Point(221, 77));
        points.add(new Point(100, 158));
        points.add(new Point(246, 253));
        points.add(new Point(466, 112));
        points.add(new Point(413, 226));
        points.add(new Point(121, 293));
        points.add(new Point(26, 477));
        points.add(new Point(320, 411));
        points.add(new Point(406, 433));
        points.add(new Point(605, 226));
        points.add(new Point(591, 382));
        points.add(new Point(53, 592));
        points.add(new Point(484, 599));
        points.add(new Point(271, 668));
        points.add(new Point(695, 576));
    }

    @AfterEach
    public void after() {
        Main.showTriangulation(points);
    }
}
