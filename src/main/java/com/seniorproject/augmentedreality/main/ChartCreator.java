/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.main;

import java.awt.Color;
import java.awt.Panel;
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
    int[] pixelR, pixelG, pixelB;

    public ChartCreator(int[] pixelR, int[] pixelG, int[] pixelB) {
        this.pixelR = pixelR;
        this.pixelG = pixelG;
        this.pixelB = pixelB;
        frame = new JFrame("RGB Chart");
    }

    public Panel drawChart() {
        dataset = createDataset();

        chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(640, 480));
//        MyCanvas canvas = new MyCanvas();
        Panel panel = new Panel();
        panel.add(chartPanel);
        panel.setSize(320, 240);
        panel.setVisible(true);
        return panel;
    }

    private XYDataset createDataset() {
//        System.out.println("Create Dataset of" + textX);
        final XYSeries seriesR = new XYSeries("Red");
        final XYSeries seriesG = new XYSeries("Green");
        final XYSeries seriesB = new XYSeries("Blue");
        Integer countPixel[] = new Integer[256];
        for (int i = 0; i < 256; i++) {
            countPixel[i] = 0;
            for (int j = 0; j < this.pixelR.length; j++) {
                if (this.pixelR[j] == i) {
                    countPixel[i]++;
                }

            }
            seriesR.add(i, countPixel[i] / (this.pixelR.length * 1.0d));
        }
        for (int i = 0; i < 256; i++) {
            countPixel[i] = 0;
            for (int j = 0; j < this.pixelG.length; j++) {
                if (this.pixelG[j] == i) {
                    countPixel[i]++;
                }
            }
            seriesG.add(i, countPixel[i] / (this.pixelG.length * 1.0d));
        }
        for (int i = 0; i < 256; i++) {
            countPixel[i] = 0;
            for (int j = 0; j < this.pixelB.length; j++) {
                if (this.pixelB[j] == i) {
                    countPixel[i]++;
                }
            }
            seriesB.add(i, countPixel[i] / (this.pixelB.length * 1.0d));
        }
        final XYSeriesCollection dataSeries = new XYSeriesCollection();
        dataSeries.addSeries(seriesR);
        dataSeries.addSeries(seriesB);
        dataSeries.addSeries(seriesG);

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

//        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//        renderer.setSeriesLinesVisible(5, true);
//        renderer.setSeriesShapesVisible(1, false);
//        plot.setRenderer(renderer);
//        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return chart;
    }
}
