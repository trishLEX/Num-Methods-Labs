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
    
    @Test
    public void test20Points2() {
        points.add(new Point(85, 57));
        points.add(new Point(236, 10));
        points.add(new Point(152, 232));
        points.add(new Point(304, 236));
        points.add(new Point(83, 266));
        points.add(new Point(71, 401));
        points.add(new Point(61, 484));
        points.add(new Point(255, 457));
        points.add(new Point(451, 339));
        points.add(new Point(281, 427));
        points.add(new Point(317, 496));
        points.add(new Point(448, 460));
        points.add(new Point(716, 63));
        points.add(new Point(538, 225));
        points.add(new Point(581, 281));
        points.add(new Point(665, 321));
        points.add(new Point(28, 626));
        points.add(new Point(496, 610));
        points.add(new Point(366, 704));
        points.add(new Point(596, 648));
    }
    
    @Test
    public void test25Points1() {
        points.add(new Point(76, 67));
        points.add(new Point(227, 44));
        points.add(new Point(193, 158));
        points.add(new Point(309, 115));
        points.add(new Point(466, 16));
        points.add(new Point(338, 238));
        points.add(new Point(13, 273));
        points.add(new Point(135, 367));
        points.add(new Point(205, 344));
        points.add(new Point(107, 441));
        points.add(new Point(426, 333));
        points.add(new Point(265, 470));
        points.add(new Point(473, 402));
        points.add(new Point(410, 510));
        points.add(new Point(550, 198));
        points.add(new Point(649, 134));
        points.add(new Point(703, 203));
        points.add(new Point(684, 372));
        points.add(new Point(562, 452));
        points.add(new Point(66, 658));
        points.add(new Point(185, 667));
        points.add(new Point(349, 594));
        points.add(new Point(466, 658));
        points.add(new Point(585, 525));
        points.add(new Point(719, 608));
    }
    
    @Test
    public void test30Points1() {
        points.add(new Point(63, 5));
        points.add(new Point(223, 57));
        points.add(new Point(185, 124));
        points.add(new Point(248, 124));
        points.add(new Point(11, 168));
        points.add(new Point(314, 67));
        points.add(new Point(506, 11));
        points.add(new Point(487, 120));
        points.add(new Point(325, 251));
        points.add(new Point(400, 161));
        points.add(new Point(485, 249));
        points.add(new Point(67, 315));
        points.add(new Point(136, 341));
        points.add(new Point(5, 463));
        points.add(new Point(157, 437));
        points.add(new Point(428, 325));
        points.add(new Point(331, 452));
        points.add(new Point(363, 476));
        points.add(new Point(699, 150));
        points.add(new Point(670, 312));
        points.add(new Point(681, 378));
        points.add(new Point(564, 404));
        points.add(new Point(118, 577));
        points.add(new Point(184, 582));
        points.add(new Point(237, 683));
        points.add(new Point(327, 512));
        points.add(new Point(467, 538));
        points.add(new Point(400, 646));
        points.add(new Point(688, 545));
        points.add(new Point(689, 629));
    }

    @Test
    public void test30Points2() {
        points.add(new Point(80, 21));
        points.add(new Point(188, 22));
        points.add(new Point(154, 187));
        points.add(new Point(279, 39));
        points.add(new Point(419, 28));
        points.add(new Point(458, 127));
        points.add(new Point(328, 138));
        points.add(new Point(484, 143));
        points.add(new Point(24, 275));
        points.add(new Point(245, 345));
        points.add(new Point(201, 364));
        points.add(new Point(97, 466));
        points.add(new Point(64, 482));
        points.add(new Point(405, 261));
        points.add(new Point(367, 409));
        points.add(new Point(289, 495));
        points.add(new Point(578, 78));
        points.add(new Point(683, 18));
        points.add(new Point(699, 201));
        points.add(new Point(628, 291));
        points.add(new Point(551, 366));
        points.add(new Point(629, 362));
        points.add(new Point(121, 540));
        points.add(new Point(223, 615));
        points.add(new Point(302, 611));
        points.add(new Point(451, 531));
        points.add(new Point(408, 674));
        points.add(new Point(581, 542));
        points.add(new Point(610, 572));
        points.add(new Point(703, 626));
    }
    
    @Test
    public void test40Points() {
        points.add(new Point(5, 114));
        points.add(new Point(220, 15));
        points.add(new Point(200, 68));
        points.add(new Point(38, 248));
        points.add(new Point(198, 131));
        points.add(new Point(329, 1));
        points.add(new Point(429, 96));
        points.add(new Point(292, 237));
        points.add(new Point(340, 207));
        points.add(new Point(407, 182));
        points.add(new Point(467, 187));
        points.add(new Point(102, 280));
        points.add(new Point(237, 371));
        points.add(new Point(102, 388));
        points.add(new Point(99, 469));
        points.add(new Point(317, 323));
        points.add(new Point(459, 275));
        points.add(new Point(311, 463));
        points.add(new Point(491, 406));
        points.add(new Point(507, 509));
        points.add(new Point(528, 71));
        points.add(new Point(696, 39));
        points.add(new Point(552, 293));
        points.add(new Point(516, 375));
        points.add(new Point(644, 276));
        points.add(new Point(667, 348));
        points.add(new Point(545, 497));
        points.add(new Point(694, 494));
        points.add(new Point(5, 595));
        points.add(new Point(113, 595));
        points.add(new Point(226, 587));
        points.add(new Point(127, 667));
        points.add(new Point(242, 701));
        points.add(new Point(380, 600));
        points.add(new Point(356, 663));
        points.add(new Point(442, 687));
        points.add(new Point(578, 563));
        points.add(new Point(679, 609));
        points.add(new Point(597, 676));
        points.add(new Point(670, 709));
    }
    
    @Test
    public void test50Points() {
        points.add(new Point(22, 13));
        points.add(new Point(143, 33));
        points.add(new Point(214, 79));
        points.add(new Point(20, 166));
        points.add(new Point(0, 195));
        points.add(new Point(129, 144));
        points.add(new Point(232, 153));
        points.add(new Point(182, 222));
        points.add(new Point(144, 248));
        points.add(new Point(324, 28));
        points.add(new Point(297, 99));
        points.add(new Point(361, 188));
        points.add(new Point(323, 213));
        points.add(new Point(386, 146));
        points.add(new Point(112, 359));
        points.add(new Point(103, 379));
        points.add(new Point(202, 282));
        points.add(new Point(6, 441));
        points.add(new Point(136, 496));
        points.add(new Point(339, 316));
        points.add(new Point(322, 364));
        points.add(new Point(389, 343));
        points.add(new Point(487, 335));
        points.add(new Point(316, 495));
        points.add(new Point(367, 508));
        points.add(new Point(388, 408));
        points.add(new Point(563, 39));
        points.add(new Point(536, 126));
        points.add(new Point(710, 0));
        points.add(new Point(682, 108));
        points.add(new Point(548, 225));
        points.add(new Point(602, 301));
        points.add(new Point(581, 372));
        points.add(new Point(643, 262));
        points.add(new Point(696, 333));
        points.add(new Point(522, 417));
        points.add(new Point(584, 475));
        points.add(new Point(15, 537));
        points.add(new Point(79, 611));
        points.add(new Point(94, 652));
        points.add(new Point(254, 695));
        points.add(new Point(295, 551));
        points.add(new Point(431, 619));
        points.add(new Point(366, 650));
        points.add(new Point(309, 717));
        points.add(new Point(507, 668));
        points.add(new Point(533, 545));
        points.add(new Point(616, 567));
        points.add(new Point(553, 671));
        points.add(new Point(656, 642));
    }

    @Test
    public void test52Points() {
        points.add(new Point(287, 323).withZ(11724));
        points.add(new Point(225, 417).withZ(10346));
        points.add(new Point(287, 417).withZ(13976));
        points.add(new Point(382, 361).withZ(9791));
        points.add(new Point(371, 249).withZ(11957));
        points.add(new Point(319, 193).withZ(13549));
        points.add(new Point(235, 212).withZ(12906));
        points.add(new Point(205, 295).withZ(9034));
        points.add(new Point(172, 398).withZ(10735));
        points.add(new Point(20, 601).withZ(6534));
        points.add(new Point(57, 8).withZ(115));
        points.add(new Point(120, 119).withZ(648));
    }

    @Test
    public void test70Points() {
        points.add(new Point(157, 419));
        points.add(new Point(265, 485));
        points.add(new Point(340, 419));
        points.add(new Point(208, 426));
        points.add(new Point(257, 385));
        points.add(new Point(324, 502));
        points.add(new Point(222, 507));
        points.add(new Point(240, 402));
        points.add(new Point(324, 385));
        points.add(new Point(349, 452));
        points.add(new Point(42, 499));
        points.add(new Point(40, 485));
        points.add(new Point(268, 542));
        points.add(new Point(91, 599));
        points.add(new Point(274, 427));
        points.add(new Point(182, 647));
        points.add(new Point(68, 135));
        points.add(new Point(290, 335));
        points.add(new Point(340, 385));
        points.add(new Point(253, 653));
        points.add(new Point(276, 702));
        points.add(new Point(157, 652));
        points.add(new Point(157, 402));
        points.add(new Point(303, 352));
        points.add(new Point(277, 643));
        points.add(new Point(157, 485));
        points.add(new Point(340, 369));
        points.add(new Point(157, 435));
        points.add(new Point(199, 385));
        points.add(new Point(197, 460));
        points.add(new Point(340, 335));
        points.add(new Point(111, 423));
        points.add(new Point(77, 579));
        points.add(new Point(224, 670));
        points.add(new Point(248, 624));
        points.add(new Point(98, 641));
        points.add(new Point(98, 364));
        points.add(new Point(290, 352));
        points.add(new Point(170, 691));
        points.add(new Point(140, 435));
        points.add(new Point(232, 485));
        points.add(new Point(213, 252));
        points.add(new Point(432, 2));
        points.add(new Point(156, 605));
        points.add(new Point(190, 268));
        points.add(new Point(349, 227));
        points.add(new Point(322, 302));
        points.add(new Point(222, 527));
        points.add(new Point(357, 402));
        points.add(new Point(137, 369));
        points.add(new Point(340, 536));
        points.add(new Point(132, 602));
        points.add(new Point(285, 427));
        points.add(new Point(257, 447));
        points.add(new Point(110, 582));
        points.add(new Point(465, 36));
        points.add(new Point(282, 586));
        points.add(new Point(240, 469));
        points.add(new Point(303, 197));
        points.add(new Point(174, 235));
        points.add(new Point(390, 219));
        points.add(new Point(100, 506));
        points.add(new Point(132, 485));
        points.add(new Point(124, 502));
        points.add(new Point(124, 452));
        points.add(new Point(149, 769));
        points.add(new Point(190, 569));
        points.add(new Point(207, 623));
        points.add(new Point(197, 701));
        points.add(new Point(99, 194));
        points.add(new Point(124, 369));
        points.add(new Point(78, 541));
        points.add(new Point(62, 561));
        points.add(new Point(282, 461));
        points.add(new Point(317, 438));
        points.add(new Point(157, 302));
        points.add(new Point(207, 623));
        points.add(new Point(140, 563));
        points.add(new Point(282, 269));
        points.add(new Point(253, 561));
        points.add(new Point(224, 402));
        points.add(new Point(132, 335));
        points.add(new Point(149, 344));
        points.add(new Point(296, 485));
        points.add(new Point(174, 619));
        points.add(new Point(440, 69));
        points.add(new Point(267, 398));
        points.add(new Point(309, 481));
        points.add(new Point(24, 419));
        points.add(new Point(261, 625));
        points.add(new Point(340, 494));
        points.add(new Point(315, 260));
        points.add(new Point(390, 394));
        points.add(new Point(449, 2));
        points.add(new Point(262, 339));
        points.add(new Point(230, 567));
        points.add(new Point(252, 512));
        points.add(new Point(111, 237));
        points.add(new Point(440, 36));
        points.add(new Point(265, 235));
        points.add(new Point(198, 490));
        points.add(new Point(224, 435));
        points.add(new Point(166, 564));
        points.add(new Point(90, 319));
        points.add(new Point(290, 385));
        points.add(new Point(124, 302));
        points.add(new Point(206, 337));
        points.add(new Point(182, 310));
        points.add(new Point(190, 419));
        points.add(new Point(324, 335));
        points.add(new Point(234, 305));
        points.add(new Point(240, 369));
        points.add(new Point(90, 502));
        points.add(new Point(122, 396));
        points.add(new Point(90, 435));
        points.add(new Point(74, 435));
        points.add(new Point(57, 435));
        points.add(new Point(257, 302));
        points.add(new Point(3, 380));
        points.add(new Point(332, 469));
        points.add(new Point(301, 267));
        points.add(new Point(174, 502));
        points.add(new Point(190, 452));
        points.add(new Point(324, 536));
        points.add(new Point(74, 369));
    }

    @AfterEach
    public void after() {
        Main.showTriangulation(points);
    }
}
