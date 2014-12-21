/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.main;

import com.seniorproject.augmentedreality.algorithm.ColorConverter;
import com.seniorproject.augmentedreality.algorithm.CannyEdgeDetector;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

/**
 *
 * @author RainWhileLoop
 */
public class EdgeRenderer extends JLabel {

    private BufferedImage image;
    CannyEdgeDetector detector = new CannyEdgeDetector();
    ColorConverter converter = new ColorConverter();

    public EdgeRenderer() {
        detector.setLowThreshold(1f);
        detector.setHighThreshold(8f);
        
    }

    @Override
    public void paintComponent(Graphics g) {
        if (image != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(image, 0, 0, 320, 240, null);
        }
    }

    public void setImage(BufferedImage image) {
        converter.setSourceImage(image);
        converter.process();
        detector.setSourceImage(converter.getHsvImageBufferedImage());
        detector.process();
        this.image = detector.getEdgesImage();
    }

}
