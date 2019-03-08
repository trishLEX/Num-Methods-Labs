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
    public void test15Points2() {
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
    
    @Test
    public void test15Points3() {
        points.add(new Point(213, 39));
        points.add(new Point(58, 129));
        points.add(new Point(509, 29));
        points.add(new Point(228, 361));
        points.add(new Point(71, 496));
        points.add(new Point(326, 283));
        points.add(new Point(426, 289));
        points.add(new Point(451, 441));
        points.add(new Point(561, 109));
        points.add(new Point(540, 235));
        points.add(new Point(617, 393));
        points.add(new Point(167, 614));
        points.add(new Point(202, 610));
        points.add(new Point(369, 607));
        points.add(new Point(575, 583));
    }
    
    @Test
    public void test20Points1() {
        points.add(new Point(53, 123));
        points.add(new Point(222, 77));
        points.add(new Point(329, 95));
        points.add(new Point(434, 6));
        points.add(new Point(359, 242));
        points.add(new Point(491, 166));
        points.add(new Point(163, 263));
        points.add(new Point(110, 450));
        points.add(new Point(184, 437));
        points.add(new Point(271, 314));
        points.add(new Point(366, 343));
        points.add(new Point(328, 480));
        points.add(new Point(689, 114));
        points.add(new Point(620, 283));
        points.add(new Point(129, 621));
        points.add(new Point(458, 557));
        points.add(new Point(286, 686));
        points.add(new Point(346, 657));
        points.add(new Point(492, 642));
        points.add(new Point(581, 640));
    }

    @AfterEach
    public void after() {
        Main.showTriangulation(points);
    }
}
