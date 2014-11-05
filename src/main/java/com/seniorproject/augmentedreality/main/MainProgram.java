package com.seniorproject.augmentedreality.main;

import com.seniorproject.augmentedreality.utils.MyCanvas;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author RainWhileLoop
 */
public class MainProgram {

    static BufferedImage originalImage = null;
    static File file;
    static Image imageRGB, imageHue, imageSaturation, imageBrightness, imageHsv;

    public static void main(String[] args) {

        int imgWidth;
        int imgHeight;

        file = new File("E:\\blackhand.jpeg");
        if (!file.isFile()) {
            System.err.print("Error! Cannot read file");
            System.exit(0);
        }

        try {
            originalImage = ImageIO.read(new FileInputStream(file));
            imgWidth = originalImage.getWidth();
            imgHeight = originalImage.getHeight();
            imageRGB = originalImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            System.err.println("Error!!!");
        }

        ColorConverter converter = new ColorConverter(originalImage);
        converter.process();

        imageHue = converter.getHueImage();
        imageSaturation = converter.getSaturationImage();
        imageBrightness = converter.getBrightnessImage();
        imageHsv = converter.getHsvImage();
        showImage();
        System.out.println("Show image Complete");
    }

    private static void showImage() {
        JFrame imageFrame;
        MyCanvas originalCanvas, hueCanvas, saturationCanvas, brightnessCanvas, hsvCanvas;

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
        
//        hueCanvas = new MyCanvas();
//        hueCanvas.setImage(imageHue);
//        hueCanvas.setWidth(width + 5);
//        hueCanvas.setSize(width, height);
//        hueCanvas.paint(originalImage.getGraphics());
//
//        saturationCanvas = new MyCanvas();
//        saturationCanvas.setImage(imageSaturation);
//        saturationCanvas.setWidth((width + 5) * 2);
//        saturationCanvas.setSize(width, height);
//        saturationCanvas.paint(originalImage.getGraphics());
//
//        brightnessCanvas = new MyCanvas();
//        brightnessCanvas.setImage(imageBrightness);
//        brightnessCanvas.setWidth((width + 5) * 3);
//        brightnessCanvas.setSize(width, height);
//        brightnessCanvas.paint(originalImage.getGraphics());

        imageFrame = new JFrame();
        imageFrame.add(originalCanvas);
        imageFrame.add(hsvCanvas);
//        imageFrame.add(hueCanvas);
//        imageFrame.add(saturationCanvas);
//        imageFrame.add(brightnessCanvas);
        imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imageFrame.setSize((width + 5) * 4, height);
        imageFrame.setLocation(0, 0);
        imageFrame.setVisible(true);
    }

    private static void showChart() {

    }
}