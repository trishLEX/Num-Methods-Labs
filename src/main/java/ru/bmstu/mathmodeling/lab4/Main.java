package ru.bmstu.mathmodeling.lab4;

import ru.bmstu.common.Drawer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*
        61.54, 2.17 //1993
        65.93, 1.73 //1994
        19.43, 1.59 //1995
        10.35, 1.24 //1996
        9.38,  1.00 //1997
        9.95,  0.99 //1998
        4.87,  0.89 //1999
        5.44,  0.37 //2000
        5.16,  0.73 //2001
        14.37, 0.78 //2002
        23.04, 0.61 //2003
        22.38, 0.53 //2004
        17.87, 0.55 //2005
        26.76, 0.51 //2006
        44.15, 0.57 //2007
        15.57, 0.46 //2008
        -26.63,0.42 //2009
        -9.26, 0.57 //2010
        19.69, 7.92 //2011
        -1.25, 1.38 //2012
        7.53,  1.19 //2013
        3.72,  1.11 //2014
        -14.02,0.82 //2015
        2.21,  0.95 //2016
        9.94,  0.50 //2017
        13.06, 0.80 //2018
         */

        List<Point> points = List.of(
                //    % изм-ия ВВП   % убывания населения
                new Point(61.54, 2.17,1993),
                new Point(65.93, 1.73,1994),
                new Point(19.43, 1.59,1995),
                new Point(10.35, 1.24,1996),
                new Point(9.38,  1.00,1997),
                new Point(9.95,  0.99,1998),
                new Point(4.87,  0.89,1999),
                new Point(5.44,  0.37,2000),
                new Point(5.16,  0.73,2001),
                new Point(14.37, 0.78,2002),
                new Point(23.04, 0.61,2003),
                new Point(22.38, 0.53,2004),
                new Point(17.87, 0.55,2005),
                new Point(26.76, 0.51,2006),
                new Point(44.15, 0.57,2007),
                new Point(15.57, 0.46,2008),
                new Point(-26.63,0.42,2009),
                new Point(-9.26, 0.57,2010),
                new Point(19.69, 7.92,2011),
                new Point(-1.25, 1.38,2012),
                new Point(7.53,  1.19,2013),
                new Point(3.72,  1.11,2014),
                new Point(-14.02,0.82,2015),
                new Point(2.21,  0.95,2016),
                new Point(9.94,  0.50,2017),
                new Point(13.06, 0.80, 2018)
        );

        Clusterization clusterization = new Clusterization(3);
        List<Cluster> clusters = clusterization.clusterize(points);

        for (Cluster cluster : clusters) {
            System.out.println(cluster);
        }

        ClusterizationUtils.normalizeCoordinates(clusters);
        Drawer drawer = new ClustersDrawer(clusters, 720);
        drawer.draw();
    }
}
