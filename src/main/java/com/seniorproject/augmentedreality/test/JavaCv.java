/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.test;

import com.seniorproject.augmentedreality.algorithm.CannyEdgeDetector;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author TOY
 */
public class JavaCv extends Canvas{

    /**
     * @param args the command line arguments
     */
    private static Image originalImage = null;
    private static Image imageI;
    public static void main(String[] args) {

        File file = new File("E:\\blackhand.jpeg");
        if (!file.isFile()) {
            System.err.print("Error");
        }
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaCv.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(stream);
        } catch (IOException ex) {
            Logger.getLogger(JavaCv.class.getName()).log(Level.SEVERE, null, ex);
        }

        //create the detector
        CannyEdgeDetector detector = new CannyEdgeDetector();
        //adjust its parameters as desired
        detector.setLowThreshold(0.5f);
        detector.setHighThreshold(1f);
        //apply it to an image
        detector.setSourceImage(image);
        detector.process();
        BufferedImage bufferedImage = detector.getEdgesImage();
        try {
//            bufferedImage
            originalImage = image.getScaledInstance(image.getWidth()/2, image.getHeight()/2, Image.SCALE_DEFAULT);
            imageI = bufferedImage.getScaledInstance(image.getWidth()/2, image.getHeight()/2, Image.SCALE_DEFAULT);
            
            JFrame frame = new JFrame();
            frame.add(new JavaCv());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(0, 0);
            frame.setMinimumSize(new Dimension(image.getWidth()*2, image.getHeight()));
            
            frame.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(JavaCv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void paint(Graphics g){
        g.drawImage(imageI, 0, 0, null);
        g.drawImage(originalImage, 300, 0, null);
    }
}
