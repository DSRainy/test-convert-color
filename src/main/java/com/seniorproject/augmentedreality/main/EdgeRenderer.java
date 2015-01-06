/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.main;

import com.seniorproject.augmentedreality.algorithm.ColorConverter;
import com.seniorproject.augmentedreality.algorithm.CannyEdgeDetector;
import com.seniorproject.augmentedreality.test.SkinColorDetection;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

/**
 *
 * @author RainWhileLoop
 */
public class EdgeRenderer extends JLabel {

    private BufferedImage image;
//    CannyEdgeDetector detector = new CannyEdgeDetector();
//    ColorConverter converter = new ColorConverter();
    SkinColorDetection skinDetection = new SkinColorDetection();
    public EdgeRenderer() {
//        detector.setLowThreshold(1f);
//        detector.setHighThreshold(8f);
        skinDetection.setLowThreshold(40, 0, 0);
        skinDetection.setHighThreshold(80, 100, 100);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (image != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(image, 0, 0, 320, 240, null);
        }
    }

    public void setImage(BufferedImage image) {
        skinDetection.setInput(image);
        skinDetection.process();
        this.image = skinDetection.getImgOutput();
        image.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
//        converter.setSourceImage(image);
//        converter.process();
//        detector.setSourceImage(converter.getHsvImageBufferedImage());
//        detector.process();
//        this.image = detector.getEdgesImage();
//        image.getScaledInstance(150, 140, Image.SCALE_DEFAULT);
    }

}
