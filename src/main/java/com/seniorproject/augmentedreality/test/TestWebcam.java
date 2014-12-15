/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.test;

import com.seniorproject.augmentedreality.main.MyCanvas;
import com.github.sarxos.webcam.Webcam;
import com.seniorproject.augmentedreality.algorithm.ColorConverter;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 *
 * @author RainWhileLoop
 */
public class TestWebcam {

    public static void main(String[] args) {
        Webcam webcam = Webcam.getDefault();
        
        webcam.open();
        System.out.println("\n\n\n-----------" + webcam.getDevice().getName());
        JFrame frame = new JFrame("TestWebcam");

        MyCanvas hsvCanvas = new MyCanvas();
        hsvCanvas.setSize(320, 240);
        hsvCanvas.setWidth(320);

//        MyCanvas edgeCanvas = new MyCanvas();
//        edgeCanvas.setSize(320, 240);
//        edgeCanvas.setWidth(640);

        MyCanvas canvas = new MyCanvas();
        canvas.setSize(320, 240);

        frame.setSize(640, 240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(canvas);
        frame.add(hsvCanvas);
//        frame.add(edgeCanvas);

        BufferedImage bufferedImage,edgeImage;
        Image image, hsv;
        ColorConverter converter;
//        CannyEdgeDetector detector = new CannyEdgeDetector();
//        detector.setLowThreshold(0.5f);
//        detector.setHighThreshold(1f);
        
        bufferedImage = webcam.getImage();
        image = bufferedImage.getScaledInstance(320, 240, Image.SCALE_DEFAULT);

        canvas.setImage(image);
        canvas.paint(bufferedImage.getGraphics());
        
//        detector.setSourceImage(image);
//        detector.process();
//        edgeImage = detector.getEdgesImage();

        do {
            converter = new ColorConverter();
            converter.setSourceImage(bufferedImage);
            converter.process();
            
            hsv = converter.getHsvImage();
            hsvCanvas.setImage(hsv);
            hsvCanvas.paint(bufferedImage.getGraphics());
            
            bufferedImage = webcam.getImage();
            image = bufferedImage.getScaledInstance(320, 240, Image.SCALE_DEFAULT);

            canvas.setImage(image);
            canvas.repaint();

            hsvCanvas.repaint();
        } while (frame.isVisible());
        webcam.close();
    }
}
