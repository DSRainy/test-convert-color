/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.utils;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author RainWhileLoop
 */
public class MyCanvas extends Canvas {

    private Image image;
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    
}
