/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.utils;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
