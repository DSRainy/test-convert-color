package com.seniorproject.augmentedreality.test;

import com.seniorproject.augmentedreality.main.MyCanvas;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author RainWhileLoop
 */
public class ConvertColor extends Frame {

    Integer width;
    Integer height;
    List<Double> alpha = new ArrayList<>();
    List<Double> red = new ArrayList<>();
    List<Double> green = new ArrayList<>();
    List<Double> blue = new ArrayList<>();
    List<Double> pixels = new ArrayList<>();
    Image rgbImage;
    Image hImage;
    Image sImage;
    Image vImage;
    float renderR[];
    float renderG[];
    float renderB[];
    float hsv[];
    private float h = 0.0f;
    private float s = 0.0f;
    private float v = 0.0f;

    public static void main(String[] args) {

        final ConvertColor demo = new ConvertColor();
    }

    public ConvertColor() {
        File file = new File("E:\\blackhand.jpeg");
//        File file = new File("E:\\dog.jpg");
        String path = file.getAbsolutePath();
        BufferedImage image;
        if (!file.isFile()) {
            System.err.print("Error");
        }
        InputStream stream = null;
        JFrame frame = new JFrame("RGB");
        try {
            stream = new FileInputStream(file);
            image = ImageIO.read(stream);
            Image img = image.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT);
            MyCanvas canvasRGB = new MyCanvas();
            MyCanvas canvasH = new MyCanvas();
            MyCanvas canvasS = new MyCanvas();
            MyCanvas canvasV = new MyCanvas();

            convert(image);
            canvasRGB.setImage(img);
            canvasRGB.setLocation(0, 20);
            canvasRGB.setSize(image.getWidth(), image.getHeight());
            canvasRGB.paint(image.getGraphics());
            canvasH.setImage(this.hImage);
            canvasH.setSize(image.getWidth(), image.getHeight());
//            canvasH.setWidth(image.getWidth());
            canvasH.setLocation(image.getWidth() + 10, 20);

            canvasH.paint(image.getGraphics());
            frame.add(canvasRGB);
            frame.add(canvasH);

            final XYDataset dataset = createDataset(this.renderR, this.renderG, this.renderB);
            final JFreeChart chartR = createChart(dataset);
            final ChartPanel chartPanelR = new ChartPanel(chartR);
            chartPanelR.setPreferredSize(new java.awt.Dimension(1020, 770));
            frame.setContentPane(chartPanelR);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new java.awt.Dimension(image.getWidth() * 2, image.getHeight()));
            frame.setLocation(0, 0);
            frame.setVisible(true);

        } catch (IOException ex) {
            Logger.getLogger(ConvertColor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.err.println("Error : ");
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(ConvertColor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void convert(BufferedImage image) {
        int imageWidth = image.getWidth();
        int imageHight = image.getHeight();
        int size = imageWidth * imageHight;
        Integer imagePixel[] = new Integer[size];
        int i = 0;
        for (int y = 0; y < imageHight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                imagePixel[i] = image.getRGB(x, y);
                i++;
            }
        }
        System.out.println("Goto Convert Color from RGB -> HSV Color model");
        Integer a;
        Integer r;
        Integer g;
        Integer b;
        this.renderR = new float[size];
        this.renderG = new float[size];
        this.renderB = new float[size];
        int render[] = new int[size];
        int average;
        hsv = new float[3];
        Color newH, newS, newV;
        for (i = 0; i < size; i++) {
            a = (imagePixel[i] >> 24) & 0xff;
            r = (imagePixel[i] >> 16) & 0xff;
            g = (imagePixel[i] >> 8) & 0xff;
            b = imagePixel[i] & 0xff;
            hsv = RGBtoHSV(r, g, b);
//            System.out.println("h : " + this.h);
//            System.out.println("s : " + this.s);
//            System.out.println("v : " + this.v);
//            System.out.println("red : " + r);
//            System.out.println("green : " + g);
//            System.out.println("blue : " + b);
            newH = Color.getHSBColor(hsv[0] * 255, 1.0F, 1.0F);
            renderR[i] = newH.getRGB();
            System.out.println(renderR[i]);
//            System.out.println(hsv[0] * 255);
//            newS = Color.getHSBColor(this.h, this.s, 1.0F);
//            average = (newS.getRed() + newS.getGreen() + newS.getBlue()) / 3;
//            renderG[i] = new Color(average, average, average, a).getRGB();
//
//            newV = Color.getHSBColor(this.h, 1.0F, this.v);
//            average = (newV.getRed() + newV.getGreen() + newV.getBlue()) / 3;
//            renderB[i] = new Color(average, average, average, a).getRGB();
        }
        this.hImage = this.createImage(new MemoryImageSource(imageWidth, imageHight,render , 0, imageWidth));
//        this.sImage = this.createImage(new MemoryImageSource(imageWidth, imageHight, this.renderG, 0, imageWidth));
//        this.vImage = this.createImage(new MemoryImageSource(imageWidth, imageHight, this.renderB, 0, imageWidth));
    }

    private float[] RGBtoHSV(int r, int g, int b) {
//        Float fr = r / 255.0f;
//        Float fg = g / 255.0f;
//        Float fb = b / 255.0f;
//        float max = Math.max(Math.max(fr, fg), fb);
//        float min = Math.min(Math.min(fr, fg), fb);
//        this.v = max;
//        float delta = max - min;
//        this.s = (max == 0.0f) ? 0.0f : delta / max;
//        if (fr.equals(max)) {
//            this.h = 60.0f * ((fg - fb) / delta + (fg < fb ? 6 : 0));
//        } else if (fg.equals(max)) {
//            this.h = 60.0f * ((fb - fr) / delta + 2);
//        } else {
//            this.h = 60.0f * ((fr - fg) / delta + 4);
//        }
//        if (this.h < 0) {
//            this.h += 360.0;
//        }
        return Color.RGBtoHSB(r, g, b, new float[3]);
//        System.out.println("Hue : " + this.h);
        //        System.out.println("Saturate : " + this.s);
        //        System.out.println("Value : " + this.v);
//        {
//
//        }
    }

    private JFreeChart createChart(final XYDataset dataset) {

        // create the chart...
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

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);
        // get alpha reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));

        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }

    private XYDataset createDataset(float redPixel[], float greenPixel[], float bluePixel[]) {
        System.out.println("Create DataSet");
        final XYSeries seriesRed = new XYSeries("Red");
        Integer countPixelRed[] = new Integer[256];
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < redPixel.length; j++) {
                if (Math.round(redPixel[j]) == i) {
                    countPixelRed[i]++;
                }
            }
            seriesRed.add(i, countPixelRed[i]);
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesRed);

        return dataset;
    }

    private CategoryDataset createDataset(List<Double> pixel) {
        Double p[][] = new Double[1][pixel.size()];
        for (int i = 0; i < pixel.size(); i++) {
            p[0][i] = pixel.get(i);
            if (i % 50 == 0) {
                System.out.println("p[" + i + "][0] = " + p[0][i]);
            }
        }

        final CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
                "Series ", "", p
        );
        return dataset;
    }

    private CategoryDataset createDataset(Integer pixel[]) {
        int length = pixel.length;
        Double p[][] = new Double[1][length];
        for (int i = 0; i < length; i++) {
            p[0][i] = pixel[i].doubleValue();
            if (i % 50 == 0) {
                System.out.println("p[" + i + "][0] = " + p[0][i]);
            }
        }

        final CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
                "Series ", "", p
        );
        return dataset;
    }

    private JFreeChart createChart(final CategoryDataset dataset) {

        final JFreeChart chart = ChartFactory.createAreaChart(
                "Area Chart", // chart title
                "ความเข้มของสี R", // domain axis label
                "ค่า", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips
                false // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        // set the background color for the chart...
//        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setAnchor(StandardLegend.SOUTH);
        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = chart.getCategoryPlot();
//        plot.setForegroundAlpha(0.5f);

        //      plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.0);
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLabelAngle(0 * Math.PI / 2.0);
        rangeAxis.resizeRange(0.5);
        rangeAxis.setRangeWithMargins(0, 255);

        // OPTIONAL CUSTOMISATION COMPLETED.
        return chart;
    }

}
