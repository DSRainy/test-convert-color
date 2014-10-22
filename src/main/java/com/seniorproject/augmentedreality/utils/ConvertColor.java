/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.utils;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author RainWhileLoop
 */
public class ConvertColor {

    Integer width;
    Integer height;

    private float h = 0.0f;
    private float s = 0.0f;
    private float v = 0.0f;

    public static void main(String[] args) {
        new ConvertColor();
    }

    public ConvertColor() {

        File file = new File("E:\\dog.jpg");
        if (!file.isFile()) {
            System.err.print("Error");
        }
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
            BufferedImage image = ImageIO.read(stream);
            getSize(image);
        } catch (IOException ex) {
            Logger.getLogger(ConvertColor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(ConvertColor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        getGUI();
    }

    private void getSize(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        System.out.println("width = " + width);
        System.out.println("height = " + height);
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
        printPixelARGB(image.getRGB(377, 87));
//            }
//        }
    }

    private void printPixelARGB(Integer pixel) {
        System.out.println("bit pixel : " + pixel.toBinaryString(pixel));
        System.out.println("count bit pixel : " + pixel.toBinaryString(pixel).length());
        Integer alpha = (pixel >> 24) & 0xff;
        Integer red = (pixel >> 16) & 0xff;
        Integer green = (pixel >> 8) & 0xff;
        Integer blue = (pixel) & 0xff;
//        System.out.println("alpha : " + alpha);
        System.out.println("red : " + red.toBinaryString(red) + " (" + red + ")");
        System.out.println("green : " + green.toBinaryString(green) + " (" + green + ")");
        System.out.println("blue : " + blue.toBinaryString(blue) + " (" + blue + ")");
        RGBtoHSV(red, green, blue);
    }

    private void RGBtoHSV(int r, int g, int b) {

        Float fr = r / 255.0f;
        Float fg = g / 255.0f;
        Float fb = b / 255.0f;
        float max = Math.max(Math.max(fr, fg), fb);
        float min = Math.min(Math.min(fr, fg), fb);
        this.v = max;
        float delta = max - min;
        this.s = (max == 0.0f) ? 0.0f : delta / max;
        if (fr.equals(max)) {
            this.h = 60.0f * ((fg - fb) / delta + (fg < fb ? 6 : 0));
        } else if (fg.equals(max)) {
            this.h = 60.0f * ((fb - fr) / delta + 2);
        } else {
            this.h = 60.0f * ((fr - fg) / delta + 4);
        }

        System.out.println("Hue : " + this.h);
        System.out.println("Saturate : " + this.s);
        System.out.println("Value : " + this.v);
    }

    private void getGUI() {
        JFrame frame = new JFrame();
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.getHSBColor(this.h, this.s, this.v));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setSize(250, 250);
        frame.add(canvas);
        frame.setSize(800, 600);
        frame.setLocation(0, 0);
        frame.setTitle("Test Convert Color");
        frame.setVisible(true);
    }
}
