/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mhunterld;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 *
 * @author MWatkins
 */
public class hudString {
int x = 0;
int y = 0;
String text = "";
protected hudString(String string, int xLoc, int yLoc){
    text = string;
    x=xLoc;
    y=yLoc;
}
public void paint(int xOffset, int yOffset, Graphics g, ImageObserver lulz){
    g.drawString(text,x+xOffset,y+yOffset);
}
    
}
