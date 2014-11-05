/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.main;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

/**
 *
 * @author RainWhileLoop
 */
public class ColorConverter {

    private int[] huePixel, saturationPixel, brightnessPixel,hsvPixel;
    private int[] pixel, alphaPixel, redPixel, greenPixel, bluePixel;
    private int imageWidth, imageHeight, imageSize;

    private BufferedImage bufferedImage;
    private Image hueImage, saturationImage, brightnessImage, hsvImage;

    public ColorConverter(BufferedImage img) {
        if (img == null) {
            System.err.println("Error : image is null");
            System.exit(0);
        }
        bufferedImage = img;
        imageWidth = bufferedImage.getWidth();
        imageHeight = bufferedImage.getHeight();
        imageSize = imageWidth * imageHeight;
        pixel = new int[imageSize];
        alphaPixel = new int[imageSize];
        redPixel = new int[imageSize];
        greenPixel = new int[imageSize];
        bluePixel = new int[imageSize];
        huePixel = new int[imageSize];
        saturationPixel = new int[imageSize];
        brightnessPixel = new int[imageSize];
        hsvPixel = new int[imageSize];
    }
    
    public void process(){
        getPixel();
        System.out.println("ColorConverter.getPixel() : Complete");
        separateColor();
        System.out.println("ColorConverter.separateColor() : Complete");
        convertRGBtoHSV();
        System.out.println("ColorConverter.convertRGBtoHSV() : Complete");
        createHSVImage();
        System.out.println("ColorConverter.createHSVImage() : Complete");
        System.out.println("converter.process() : Process Complete");
    }

    private void createHSVImage() {
        Frame frame = new Frame();
        hueImage = frame.createImage(new MemoryImageSource(imageWidth, imageHeight, huePixel, 0, imageWidth));
        saturationImage = frame.createImage(new MemoryImageSource(imageWidth, imageHeight, saturationPixel, 0, imageWidth));
        brightnessImage = frame.createImage(new MemoryImageSource(imageWidth, imageHeight, brightnessPixel, 0, imageWidth));
        hsvImage = frame.createImage(new MemoryImageSource(imageWidth, imageHeight, hsvPixel, 0, imageWidth));
    }

    private void convertRGBtoHSV() {
        float r;
        float g;
        float b;
        float max, min;
        float hsv[] = new float[3];
        float delta;
        for (int i = 0; i < imageSize; i++) {
            r = redPixel[i];
            g = greenPixel[i];
            b = bluePixel[i];
            r /= 255.0f;
            g /= 255.0f;
            b /= 255.0f;
            max = Math.max(Math.max(r, g), b);
            min = Math.min(Math.min(r, g), b);
            delta = max - min;
            hsv[2] = max;
            hsv[1] = (max == 0.0f) ? 0.0f : delta / max;

            if (r == max) {
                hsv[0] = 60.0f * (g - b) / delta + (g < b ? 6 : 0);
            } else if (g == max) {
                hsv[0] = 60.0f * (b - r) / delta + 2;
            } else {
                hsv[0] = 60.0f * (r - g) / delta + 4;
            }
            if (hsv[0] < 0.0f) {
                hsv[0] += 360.0f;
            }

//            hsv = Color.RGBtoHSB(Math.round(r), Math.round(g), Math.round(b), hsv);
            huePixel[i] = Color.getHSBColor(hsv[0], 1.0f, 1.0f).getRGB();
            saturationPixel[i] = Color.getHSBColor(hsv[0], hsv[1], 1.0f).getRGB();
            brightnessPixel[i] = Color.getHSBColor(hsv[0], 1.0f, hsv[2]).getRGB();
            hsvPixel[i] = Color.getHSBColor(hsv[0], hsv[1], hsv[2]).getRGB();
        }
    }

    private void getPixel() {
        int i = 0;
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                pixel[i] = bufferedImage.getRGB(x, y);
                i++;
            }
        }
    }

    private void separateColor() {
        for (int i = 0; i < imageSize; i++) {
            alphaPixel[i] = pixel[i] >> 24 & 0xFF;
            redPixel[i] = pixel[i] >> 16 & 0xFF;
            greenPixel[i] = pixel[i] >> 8 & 0xFF;
            bluePixel[i] = pixel[i] & 0xFF;
        }
    }

    public Image getHueImage() {
        return hueImage;
    }

    public Image getSaturationImage() {
        return saturationImage;
    }

    public Image getBrightnessImage() {
        return brightnessImage;
    }

    public Image getHsvImage() {
        return hsvImage;
    }

}
