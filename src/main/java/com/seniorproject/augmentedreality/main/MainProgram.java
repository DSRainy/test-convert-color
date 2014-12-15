package com.seniorproject.augmentedreality.main;

import com.seniorproject.augmentedreality.chart.ChartCreator;
import com.seniorproject.augmentedreality.algorithm.ColorConverter;
import com.seniorproject.augmentedreality.algorithm.CannyEdgeDetector;
import com.github.sarxos.webcam.Webcam;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFrame;

/**
 *
 * @author RainWhileLoop
 */
public class MainProgram {

    static BufferedImage originalImage = null;
    static File file;
    static Image imageRGB, imageHsv;

    public static void main(String[] args) {
        Webcam webcam = Webcam.getDefault();

        webcam.open();
        System.out.println("\n\n\n-----------" + webcam.getDevice().getName());
        JFrame frame = new JFrame("Hand Detector");

        MyCanvas hsvCanvas = new MyCanvas();
        hsvCanvas.setSize(320, 240);
        hsvCanvas.setWidth(320);

        MyCanvas canvas = new MyCanvas();
        canvas.setSize(320, 240);

        frame.setSize(640, 280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(canvas);
        frame.add(hsvCanvas);

        BufferedImage bufferedImage;
        Image image;
        ColorConverter converter;
        CannyEdgeDetector detector = new CannyEdgeDetector();
        detector.setLowThreshold(0.5f);
        detector.setHighThreshold(1f);

        BufferedImage bimage = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr;
        converter = new ColorConverter();
        do {
            bufferedImage = webcam.getImage();
            image = bufferedImage.getScaledInstance(320, 240, Image.SCALE_DEFAULT);

            converter.setSourceImage(bufferedImage);
            converter.process();

            /*Convert Image to BufferedImage*/
            bGr = bimage.createGraphics();
            bGr.drawImage(converter.getHsvImage(), 0, 0, null);
            bGr.dispose();

            detector.setSourceImage(bimage);
            detector.process();

            canvas.setImage(image);
            hsvCanvas.setImage(detector.getEdgesImage().getScaledInstance(320, 240, Image.SCALE_DEFAULT));
            canvas.repaint();
            hsvCanvas.repaint();

        } while (frame.isVisible());
        webcam.close();
    }
//    public static void main(String[] args) {
//
//        int imgWidth;
//        int imgHeight;
//
//        file = new File("E:\\blackhand.jpeg");
//        if (!file.isFile()) {
//            System.err.print("Error! Cannot read file");
//            System.exit(0);
//        }
//
//        try {
//            originalImage = ImageIO.read(new FileInputStream(file));
//            imgWidth = originalImage.getWidth();
//            imgHeight = originalImage.getHeight();
//            imageRGB = originalImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_DEFAULT);
//        } catch (Exception e) {
//            System.err.println("Error!!!");
//        }
//
//        ColorConverter converter = new ColorConverter(originalImage);
//        converter.process();
//
//        imageHsv = converter.getHsvImage();
//        showImage();
//        showChart(converter);
//
//    }

    private static void showImage() {
        JFrame imageFrame;
        MyCanvas originalCanvas, hsvCanvas;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        originalCanvas = new MyCanvas();
        originalCanvas.setImage(imageRGB);
        originalCanvas.setWidth(0);
        originalCanvas.setSize(width, height);
        originalCanvas.paint(originalImage.getGraphics());

        hsvCanvas = new MyCanvas();
        hsvCanvas.setImage(imageHsv);
        hsvCanvas.setWidth((width + 5));
        hsvCanvas.setSize(width, height);
        hsvCanvas.paint(originalImage.getGraphics());

        imageFrame = new JFrame();
        imageFrame.add(originalCanvas);
        imageFrame.add(hsvCanvas);
        imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imageFrame.setSize((width + 5) * 2, height);
        imageFrame.setLocation(0, 0);
        imageFrame.setVisible(true);
    }

    private static void showChart(ColorConverter converter) {
        String[] text = new String[4];
        text[0] = "RGB";
        text[1] = "Red";
        text[2] = "Green";
        text[3] = "Blue";
        ChartCreator rgbChart = new ChartCreator(converter.redPixel, converter.greenPixel, converter.bluePixel, text);
        Panel rgbPanel = rgbChart.drawChart();
        JFrame frame = new JFrame("RGB Graph");
        rgbPanel.setLocation(0, 0);
        frame.add(rgbPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(rgbPanel.getSize());
        frame.setVisible(true);

        text[0] = "HSB";
        text[1] = "Hue";
        text[2] = "Saturate";
        text[3] = "Brightness";
        ChartCreator hsvChart = new ChartCreator(converter.huePixel, converter.saturationPixel, converter.brightnessPixel, text);
        Panel hsvPanel = hsvChart.drawChart();

        JFrame hsvFrame = new JFrame("HSV Graph");
        hsvFrame.add(hsvPanel);
        hsvFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hsvFrame.setSize(hsvPanel.getSize());
        hsvFrame.setVisible(true);
    }
}
