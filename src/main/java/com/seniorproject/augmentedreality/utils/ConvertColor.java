/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author RainWhileLoop
 */
public class ConvertColor {

    Integer width;
    Integer height;
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
    }

    private void getSize(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        System.out.println("width = " + width);
        System.out.println("height = " + height);
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
        printPixelARGB(image.getRGB(121, 320));
//            }
//        }
    }

    public void printPixelARGB(Integer pixel) {
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
    }
}
