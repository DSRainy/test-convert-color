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
        while (true) {
            BufferedImage bufferedImage = webcam.getImage();
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            canvas.setImage(image);
            canvas.setSize(width, height);
            canvas.paint(bufferedImage.getGraphics());
            frame.add(canvas);

            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        }
    }
}
