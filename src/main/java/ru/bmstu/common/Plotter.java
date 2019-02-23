package ru.bmstu.common;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.util.List;


public class Plotter extends ApplicationFrame {
    public Plotter(
            String title,
            List<Double> xArray,
            List<Double> yArray,
            String xLabel,
            String yLabel
    ) {
        super(title);

        XYSeries series = new XYSeries(title, false, true);
        for (int i = 0; i < yArray.size(); i++) {
            series.add(xArray.get(i), yArray.get(i));
        }

        XYDataset data = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                xLabel,
                yLabel,
                data,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(640, 480));
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        chart.getXYPlot().setRenderer(renderer);
        setContentPane(panel);
    }

    public void draw() {
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }
}
