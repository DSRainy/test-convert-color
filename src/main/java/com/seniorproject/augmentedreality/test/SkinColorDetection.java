package com.seniorproject.augmentedreality.test;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

/**
 *
 * @author RainWhileLoop
 */
public class SkinColorDetection {

    private BufferedImage imgInput;
    private BufferedImage imgOutput;
    private Image image;
    private int width, height;
    private int inputPixel[][];
    private int outputPixel[];
    private final int lowThreshold[];
    private final int highThreshold[];

    public SkinColorDetection() {
        this.lowThreshold = new int[3];
        this.highThreshold = new int[3];
        this.lowThreshold[1] = 0;
        this.lowThreshold[2] = 0;
        this.highThreshold[1] = 100;
        this.highThreshold[2] = 100;
    }

    public void process() {
        getPixel();
        int index = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float[] hsv = new float[3];
                int r = inputPixel[i][j] >> 16 & 0xFF;
                int g = inputPixel[i][j] >> 8 & 0xFF;
                int b = inputPixel[i][j] & 0xFF;
                hsv = Color.RGBtoHSB(Math.round(r), Math.round(g), Math.round(b), hsv);
                hsv[0] *= 360.0;
                if (this.lowThreshold[0] < hsv[0] && hsv[0] < this.highThreshold[0]) {
                    outputPixel[index] = 0x00000000;
                } else {
                    outputPixel[index] = 0xFFFFFFFF;
                }
                index++;
            }
        }

        image = new Frame().createImage(new MemoryImageSource(width, height, outputPixel, 0, width));
        imgOutput = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr;
        bGr = imgOutput.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

    }

    public void getPixel() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                inputPixel[i][j] = this.imgInput.getRGB(i, j);
            }
        }
    }

    public BufferedImage getImgInput() {
        return imgInput;
    }

    public void setInput(BufferedImage imgInput) {
        this.imgInput = imgInput;
//        this.imgInput.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        width = this.imgInput.getWidth();
        height = this.imgInput.getHeight();
        inputPixel = new int[width][height];
//        System.out.println(width +" " + height);
        outputPixel = new int[width * height];
    }

    public void setLowThreshold(int h, int s, int v) {
        this.lowThreshold[0] = h;
//        this.lowThreshold[1] = s;
//        this.lowThreshold[2] = v;
    }

    public void setHighThreshold(int h, int s, int v) {
        this.highThreshold[0] = h;
    }

    public BufferedImage getImgOutput() {
        return imgOutput;
    }

}
