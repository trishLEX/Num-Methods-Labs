package ru.bmstu.mathmodeling.lab2;

import ru.bmstu.common.TriangleDrawer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final int N = 50;

    public static final int WINDOW_SIZE = 720;
    public static final Environment ENVIRONMENT = Environment.DEVELOPMENT;
    public static final boolean MAKE_STEPS = false;

    public static void main(String[] args) throws Exception {
//        int fieldsCount = 1;
//        for (int i = 1; fieldsCount < N; i++) {
//            fieldsCount = (int) Math.pow(i, 2);
//        }
//        boolean[] isFilledField = new boolean[fieldsCount];
//        for (int i = 0; i < fieldsCount; i++) {
//            isFilledField[i] = false;
//        }
//
//        int count = 0;
//        Random random = new Random();
//        List<Point> points = new ArrayList<>();
//        while (count < N) {
//            int i = random.nextInt(fieldsCount);
//            if (!isFilledField[i]) {
//                isFilledField[i] = true;
//
//                int size = (int) Math.sqrt(fieldsCount);
//                int xBound = i % size;
//                int yBound = i / size;
//
//                int xLowBound = WINDOW_SIZE / size * xBound;
//                int xHighBound = WINDOW_SIZE / size * (xBound + 1);
//
//                int yLowBound = WINDOW_SIZE / size * yBound;
//                int yHighBound = WINDOW_SIZE / size * (yBound + 1);
//
//                points.add(new Point(xLowBound + random.nextInt(xHighBound - xLowBound), yLowBound + random.nextInt(yHighBound - yLowBound)));
//                count++;
//            }
//        }
//
//        points.sort(Comparator.comparingLong(Point::getZCode));
//
//        showTriangulation(points);

        List<String> urls = Arrays.asList(
                "https://ru.wikipedia.org/wiki/%D0%A6%D0%B5%D0%BD%D1%82%D1%80%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9_%D0%B0%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B9_%D0%BE%D0%BA%D1%80%D1%83%D0%B3_(%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0)",
                "https://ru.wikipedia.org/wiki/%D0%A1%D0%B5%D0%B2%D0%B5%D1%80%D0%BD%D1%8B%D0%B9_%D0%B0%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B9_%D0%BE%D0%BA%D1%80%D1%83%D0%B3",
                "https://ru.wikipedia.org/wiki/%D0%A1%D0%B5%D0%B2%D0%B5%D1%80%D0%BE-%D0%92%D0%BE%D1%81%D1%82%D0%BE%D1%87%D0%BD%D1%8B%D0%B9_%D0%B0%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B9_%D0%BE%D0%BA%D1%80%D1%83%D0%B3",
                "https://ru.wikipedia.org/wiki/%D0%92%D0%BE%D1%81%D1%82%D0%BE%D1%87%D0%BD%D1%8B%D0%B9_%D0%B0%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B9_%D0%BE%D0%BA%D1%80%D1%83%D0%B3",
                "https://ru.wikipedia.org/wiki/%D0%AE%D0%B3%D0%BE-%D0%92%D0%BE%D1%81%D1%82%D0%BE%D1%87%D0%BD%D1%8B%D0%B9_%D0%B0%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B9_%D0%BE%D0%BA%D1%80%D1%83%D0%B3",
                "https://ru.wikipedia.org/wiki/%D0%AE%D0%B6%D0%BD%D1%8B%D0%B9_%D0%B0%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B9_%D0%BE%D0%BA%D1%80%D1%83%D0%B3",
                "https://ru.wikipedia.org/wiki/%D0%AE%D0%B3%D0%BE-%D0%97%D0%B0%D0%BF%D0%B0%D0%B4%D0%BD%D1%8B%D0%B9_%D0%B0%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B9_%D0%BE%D0%BA%D1%80%D1%83%D0%B3",
                "https://ru.wikipedia.org/wiki/%D0%97%D0%B0%D0%BF%D0%B0%D0%B4%D0%BD%D1%8B%D0%B9_%D0%B0%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B9_%D0%BE%D0%BA%D1%80%D1%83%D0%B3",
                "https://ru.wikipedia.org/wiki/%D0%A1%D0%B5%D0%B2%D0%B5%D1%80%D0%BE-%D0%97%D0%B0%D0%BF%D0%B0%D0%B4%D0%BD%D1%8B%D0%B9_%D0%B0%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B9_%D0%BE%D0%BA%D1%80%D1%83%D0%B3",
                "https://ru.wikipedia.org/wiki/%D0%97%D0%B5%D0%BB%D0%B5%D0%BD%D0%BE%D0%B3%D1%80%D0%B0%D0%B4",
                "https://ru.wikipedia.org/wiki/%D0%A2%D1%80%D0%BE%D0%B8%D1%86%D0%BA%D0%B8%D0%B9_%D0%B0%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B9_%D0%BE%D0%BA%D1%80%D1%83%D0%B3",
                "https://ru.wikipedia.org/wiki/%D0%9D%D0%BE%D0%B2%D0%BE%D0%BC%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%B8%D0%B9_%D0%B0%D0%B4%D0%BC%D0%B8%D0%BD%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D1%8B%D0%B9_%D0%BE%D0%BA%D1%80%D1%83%D0%B3"
        );

        for (String urlStr : urls) {
            URL url = new URL(urlStr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String regex = "\"wgCoordinates\":\\{\"lat\":(?<first>[0-9]+\\.?[0-9]*),\"lon\":(?<second>[0-9]+\\.?[0-9]*)\\}";
            Pattern pattern = Pattern.compile(regex);
            String name = bufferedReader.lines().skip(4).findFirst().get();
            String string = bufferedReader.lines().skip(1).findFirst().get();
            int index = string.indexOf("\"wgCoordinates");
            string = string.substring(index, string.indexOf("\"wgWikibaseItemId", index) - 1);
            Matcher matcher = pattern.matcher(string);
            while (matcher.find()) {
                //System.out.println(name);
                double phi = Math.toRadians(Double.parseDouble(matcher.group("first"))); //широта
                double lambda = Math.toRadians(Double.parseDouble(matcher.group("second"))); //долгота

                //System.out.println(phi + " " + lambda);

                //double x = Math.cos(Math.toRadians(phi)) * Math.sin(Math.toRadians(lambda));
                //double y = Math.sin(Math.toRadians(phi)) * Math.sin(Math.toRadians(lambda));

                double x = lambda;
                double y = Math.log(Math.tan(Math.PI / 4 + phi / 2));

                x = x - 0.656535 + 0.008;
                y = y - 1.177273 + 0.009;

                x *= 50 * 720;
                y *= 50 * 720;

                System.out.println(String.format("points.add(new Point(%d, %d));", (int) x, (int) y));
            }
        }
    }

    public static void showTriangulation(List<Point> points) {
        Triangulation triangulation = new Triangulation(points);

        TriangleDrawer drawer = new TriangleDrawer(WINDOW_SIZE, WINDOW_SIZE, triangulation, points);
        drawer.draw();

        System.out.println(triangulation.getCircles());
    }
}
