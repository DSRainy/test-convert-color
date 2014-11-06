/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.utils;

import com.github.sarxos.webcam.Webcam;
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
        MyCanvas canvas = new MyCanvas();
        canvas.setSize(640, 480);

        frame.setSize(320, 240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(canvas);
        BufferedImage bufferedImage;
        Image image;
        bufferedImage = webcam.getImage();
        image = bufferedImage.getScaledInstance(320, 240, Image.SCALE_DEFAULT);
        canvas.setImage(image);
        canvas.paint(bufferedImage.getGraphics());
        do {
            bufferedImage = webcam.getImage();
            image = bufferedImage.getScaledInstance(320, 240, Image.SCALE_DEFAULT);
            canvas.setImage(image);
            canvas.repaint();
        } while (frame.isVisible());
        webcam.close();
    }
}
