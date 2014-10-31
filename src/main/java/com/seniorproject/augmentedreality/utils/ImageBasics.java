/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.utils;
/*File ImageBasics.java
 based on code from: 
 http://www.dickbaldwin.com/java/Java170.htm

 IAT455 - Workshop week 2
 Digital Representation of Visual Information.
   
 **********************************************************/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class ImageBasics extends Frame {  //controlling class

    Image testImage;  //reference to an Image object
    Image image0;

    Image redChannel; //red channel
    Image greenChannel; //green channel
    Image blueChannel; //blue channel
    Image restoredImg; //restored image

    Image redChannel_reduced; //red channel with reduced bit-depth
    Image greenChannel_reduced; //green channel with reduced bit-depth
    Image blueChannel_reduced; //blue channel with reduced bit-depth
    Image restoredImg_reduced; //restored image with reduced bit-depth

    Image hue_img; //red channel with reduced bit-depth
    Image saturation_img; //green channel with reduced bit-depth
    Image value_img; //blue channel with reduced bit-depth

    Image brightnessChannel; //
    Image invertChannel;

    Color newColor;
    Color newColor_h;
    Color newColor_s;
    Color newColor_v;

    Color newColor_b;

    ColorModel m_colorModel = null;

    int width0; //width of the image
    int height0; //height of the image

    int width; //width of the resized image
    int height; //height of the resized image

    public ImageBasics() { //constructor
        this.setTitle("RGB to HSV");

        //Get an image from the specified file in the current directory on the local hard disk.
        testImage = Toolkit.getDefaultToolkit().getImage("E:\\blackhand.jpeg");
        image0 = testImage.getScaledInstance(200, -1, Image.SCALE_SMOOTH);

        /*Use a MediaTracker object to block until images
         are loaded and scaled before attempting to process
         them. Instantiate the object and add the two
         images to the list*/
        MediaTracker tracker = new MediaTracker(this);
        //Add images to the tracker list
        //tracker.addImage(testImage,1);
        tracker.addImage(image0, 1);

        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        //Now test for errors 
        if (tracker.isErrorAny()) {
            System.out.println("Load error");
            System.exit(1);
        }//end if

        /*Make the Frame object visible.  Note that the image is not visible on the Frame object when it first
         appears on the screen.*/
        this.setVisible(true);

        width = image0.getWidth(this);
        height = image0.getHeight(this);

        this.setSize(width * 5 + 300, height * 3 + 150);

        System.out.println("bird.jpg");
        System.out.println("width image0: " + width);
        System.out.println("height image0: " + height);

        //Declare an array object of type int to receive the numeric representation of all the pixels in the image. 
        int[] pix = new int[width * height];
        int[] pix_r = new int[width * height]; //array to hold R channel
        int[] pix_g = new int[width * height]; //array to hold G channel
        int[] pix_b = new int[width * height]; //array to hold B channel
        int[] pix_restore = new int[width * height]; //array to hold restored pixels

        int[] pix_r_reduced = new int[width * height]; //array to hold R channel with reduced bit-depth
        int[] pix_g_reduced = new int[width * height]; //array to hold G channel with reduced bit-depth
        int[] pix_b_reduced = new int[width * height]; //array to hold B channel with reduced bit-depth
        int[] pix_restore_reduced = new int[width * height]; //array to hold restored pixels

        float[] hue = new float[width * height]; //array to hold the hues for all pixels
        float[] saturation = new float[width * height]; //array to hold the saturations for all pixels
        float[] value = new float[width * height]; //array to hold the values (of hsV) for all pixels

        float[] hsv = new float[3]; //array to hold the H, S, V for one pixel

        int[] pix_h = new int[width * height]; //array to hold hue info
        int[] pix_s = new int[width * height]; //array to hold  saturation info
        int[] pix_v = new int[width * height]; //array to hold  value info	 

        int[] red = new int[width * height]; //array to hold R color data
        int[] green = new int[width * height]; //array to hold G color data
        int[] blue = new int[width * height]; //array to hold B color data
        int[] alpha = new int[width * height]; //array to hold transparency value

        int[] average_s = new int[width * height]; //average calculated for grayscale display
        int[] average_v = new int[width * height];

        int[] pix_bright = new int[width * height]; //!!!!!!!!!!
        int[] pix_invert = new int[width * height];

        try {
            //Instantiate a PixelGrabber object specifying pix as the array in which to put the numeric pixel data
            PixelGrabber pgObj = new PixelGrabber(image0, 0, 0, width, height, pix, 0, width);

            if (pgObj.grabPixels() && ((pgObj.getStatus() & ImageObserver.ALLBITS) != 0)) {
                // pixel manipulation to extract the three channels: R, G, B
                m_colorModel = pgObj.getColorModel(); //to extract Color red, green, blue

                for (int cnt = 0; cnt < (width * height); cnt++) {

                    //extract  r G B
                    pix_r[cnt] = (pix[cnt] & 0x00FF0000) >> 16;  // red channel (keep the same transparency / red channel and mask out green, blue)
                    pix_g[cnt] = (pix[cnt] & 0x0000FF00) >> 8;  // green channel (keep the same transparency / green channel and mask out red, blue)
                    pix_b[cnt] = pix[cnt] & 0x000000FF;  // blue channel (keep the same transparency / blue channel and mask out green, red)

                    int r = (int) ((pix_r[cnt] - (0.33 * 255)) * 3);
                    int g = (int) ((pix_g[cnt] - (0.33 * 255)) * 3);
                    int b = (int) ((pix_b[cnt] - (0.33 * 255)) * 3);

                    if (r > 255) {
                        r = 255;
                    }
                    if (g > 255) {
                        g = 255;
                    }
                    if (b > 255) {
                        b = 255;
                    }

                    if (r < 0) {
                        r = 0;
                    }
                    if (g < 0) {
                        g = 0;
                    }
                    if (b < 0) {
                        b = 0;
                    }

                    pix_bright[cnt] = new Color(r, g, b, 255).getRGB();

                    //  H S V 
                    red[cnt] = m_colorModel.getRed(pix[cnt]);  //get red color data
                    green[cnt] = m_colorModel.getGreen(pix[cnt]); //get green color data
                    blue[cnt] = m_colorModel.getBlue(pix[cnt]);   //get blue color data

                    alpha[cnt] = m_colorModel.getAlpha(pix[cnt]);  //get transparency

                    hsv = Color.RGBtoHSB(red[cnt], green[cnt], blue[cnt], hsv); //transform the image from RGB model to HSV model hsv[H, S, V]

                    //extract for each pixel the H, S and V
                    hue[cnt] = hsv[0];
                    saturation[cnt] = hsv[1];
                    value[cnt] = hsv[2];

                    //for each pixel create new color corresponding to the hue of the pixel (hue = actual color with  max. saturation, max.brightness(brightenss=v));
                    newColor_h = Color.getHSBColor(hue[cnt], 1.0F, 1.0F);    //saturation s = max., value v = max  
                    pix_h[cnt] = newColor_h.getRGB(); //create pixel array for display - RGB display

                    //for each pixel create new color corresponding to the saturation of the pixel - will display in grayscale to better vizualize saturation of pixels 
                    newColor_s = Color.getHSBColor(hue[cnt], saturation[cnt], 1.0F);
                    average_s[cnt] = (newColor_s.getRed() + newColor_s.getGreen() + newColor_s.getGreen()) / 3; //for grayscale display
                    pix_s[cnt] = (new Color(average_s[cnt], average_s[cnt], average_s[cnt], alpha[cnt])).getRGB(); //grayscale: all three components = average

                    newColor_v = Color.getHSBColor(hue[cnt], 1.0F, value[cnt]);
                    average_v[cnt] = (newColor_v.getRed() + newColor_v.getGreen() + newColor_v.getGreen()) / 3;
                    pix_v[cnt] = (new Color(average_v[cnt], average_v[cnt], average_v[cnt], alpha[cnt])).getRGB();

                }//end for loop  

            }//end if statement
            else {
                System.out.println("Pixel grab not successful");
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }

//        //Use the createImage() method to create a new image corresponding to the R, G, B channels 
//        // from the array of pixel values.
//        redChannel = this.createImage(new MemoryImageSource(width, height, pix_r, 0, width));
//        greenChannel = this.createImage(new MemoryImageSource(width, height, pix_g, 0, width));
//        blueChannel = this.createImage(new MemoryImageSource(width, height, pix_b, 0, width));
//        restoredImg = this.createImage(new MemoryImageSource(width, height, pix_restore, 0, width));
//
//        brightnessChannel = this.createImage(new MemoryImageSource(width, height, pix_bright, 0, width));
//	    //invertChannel = this.createImage(new MemoryImageSource(width,height,pix_invert,0,width));
//
//        //Use the createImage() method to create a new image corresponding to the reduced R, G, B channels 
//        // from the array of pixel values.
//        redChannel_reduced = this.createImage(new MemoryImageSource(width, height, pix_r_reduced, 0, width));
//        greenChannel_reduced = this.createImage(new MemoryImageSource(width, height, pix_g_reduced, 0, width));
//        blueChannel_reduced = this.createImage(new MemoryImageSource(width, height, pix_b_reduced, 0, width));
//        restoredImg_reduced = this.createImage(new MemoryImageSource(width, height, pix_restore_reduced, 0, width));

        //Use the createImage() method to create a new image corresponding to the H, S, V info
        // from the array of pixel values.
        hue_img = this.createImage(new MemoryImageSource(width, height, pix_h, 0, width));
        saturation_img = this.createImage(new MemoryImageSource(width, height, pix_s, 0, width));
        value_img = this.createImage(new MemoryImageSource(width, height, pix_v, 0, width));
        
        this.addWindowListener(
                new WindowAdapter() {//anonymous class definition
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);//terminate the program
                    }//end windowClosing()
                }//end WindowAdapter
        );//end addWindowListener
    }//end constructor  

    //=======================================================//	
    @Override
    public void paint(Graphics g) {

//         original + R G B channels + restored
        g.drawImage(image0, 10, 50, this);
//        g.drawImage(redChannel, width + 20, 50, this);
//        g.drawImage(greenChannel, width * 2 + 30, 50, this);
//        g.drawImage(blueChannel, width * 3 + 40, 50, this);
//        g.drawImage(restoredImg, width * 4 + 50, 50, this);
//        g.drawImage(value_img, width + 20, 50 + height + 30, this);
//
//        g.drawImage(invertChannel, width * 2 + 30, 50 + height + 30, this);

        //add caption to the displayed images
        g.setColor(Color.BLACK);
        Font f1 = new Font("Verdana", Font.BOLD, 15);
        g.setFont(f1);
        g.drawString("Original image", 15, 45);
//        g.drawString("Red Channel", 15 + width + 20, 45);
//        g.drawString("Green Channel", 15 + width * 2 + 20, 45);
//        g.drawString("Blue Channel", 15 + width * 3 + 40, 45);
//        g.drawString("Restored Image", 15 + width * 4 + 50, 45);
//        g.drawString("R G B", 15 + width * 5 + 80, 45 + height / 2);

        // reduced R G B + restored
//        g.drawImage(redChannel_reduced, width + 20, 50 + height + 30, this);
//        g.drawImage(greenChannel_reduced, width * 2 + 30, 50 + height + 30, this);
//        g.drawImage(blueChannel_reduced, width * 3 + 40, 50 + height + 30, this);
//        g.drawImage(restoredImg_reduced, width * 4 + 50, 50 + height + 30, this);

//        g.drawString("Red Channel-reduced", 10 + width + 20, 45 + height + 30);
//        g.drawString("Green Channel-reduced", 10 + width * 2 + 20, 45 + height + 30);
//        g.drawString("Blue Channel-reduced", 10 + width * 3 + 35, 45 + height + 30);
//        g.drawString("Restored Image-reduced", 10 + width * 4 + 40, 45 + height + 30);

//        g.drawString("Reduced bit-depth", 20, 45 + height / 2 + height + 30);

        // H S V
        g.drawImage(hue_img, width + 20, 50 , this);
        g.drawImage(saturation_img, width * 2 + 30, 50, this);
        g.drawImage(value_img, width * 3 + 40, 50 , this);

        g.drawString("Hue component", 10 + width + 20, 45);
        g.drawString("Saturation component", 10 + width * 2 + 20,45);
        g.drawString("Value component", 10 + width * 3 + 35,45);

    }
    //=======================================================//

    public static void main(String[] args) {

        ImageBasics img = new ImageBasics();//instantiate this object
        JPanel container = new JPanel();
        JScrollPane scroll = new JScrollPane(container);
        
        img.add(scroll);
        img.setSize(850, 330);
//        img.set
//        img.setLocationRelativeTo(null);
        img.repaint();//render the image

    }//end main
}

//=======================================================//
