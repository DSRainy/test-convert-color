/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.main;

import java.awt.Color;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author RainWhileLoop
 */
public class ChartCreator {

    XYDataset dataset;
    JFreeChart chart;
    ChartPanel chartPanel;
    JFrame frame;
    String textX,textY;
    int[] pixel;

    public ChartCreator(int[] pixel , String textX ) {
        this.pixel = pixel;
        this.textX = textX;
        frame = new JFrame("RGB Chart");
    }

    public void create() {
        dataset = createDataset();
        chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1020, 770));
        frame.setContentPane(chartPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private XYDataset createDataset() {
        System.out.println("Create DataSet");
        final XYSeries series = new XYSeries(textX);
        Integer countPixel[] = new Integer[256];
        for (int i = 0; i < 256; i++) {
            countPixel[i] = 0;
            for (int j = 0; j < this.pixel.length; j++) {
                if (this.pixel[j] == i) {
                    countPixel[i]++;
                }
            }
            series.add(i, countPixel[i] / (this.pixel.length * 1.0d));
        }
        final XYSeriesCollection dataSeries = new XYSeriesCollection();
        dataSeries.addSeries(series);

        return dataSeries;
    }

    private JFreeChart createChart(final XYDataset dataset) {

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "RGB", // chart title
                "Intensity", // x axis label
                "number of pixel", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        chart.setBackgroundPaint(Color.white);
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
    }
}
