/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.chart;

import java.awt.Color;
import java.awt.Panel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
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
    int[] pixelR, pixelG, pixelB;
    String[] title;

    public ChartCreator(int[] pixelR, int[] pixelG, int[] pixelB, String[] title) {
        this.pixelR = pixelR;
        this.pixelG = pixelG;
        this.pixelB = pixelB;
        this.title = title;
    }

    public Panel drawChart() {
        dataset = createDataset();
        chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(640, 480));
        Panel panel = new Panel();
        panel.setSize(640, 480);
        panel.add(chartPanel);
        panel.setVisible(true);
        return panel;
    }

    protected XYDataset createDataset() {
//        System.out.println("Create Dataset of" + textX);
        final XYSeries seriesR = new XYSeries(title[1]);
        final XYSeries seriesG = new XYSeries(title[2]);
        final XYSeries seriesB = new XYSeries(title[3]);
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

        final JFreeChart chart1 = ChartFactory.createXYLineChart(
                this.title[0], // chart title
                "Intensity", // x axis label
                "number of pixel", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        chart1.setBackgroundPaint(Color.white);
        final XYPlot plot = chart1.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

//        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//        renderer.setSeriesLinesVisible(5, true);
//        renderer.setSeriesShapesVisible(1, false);
//        plot.setRenderer(renderer);
//        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return chart1;
    }
}
