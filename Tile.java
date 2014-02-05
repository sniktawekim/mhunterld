/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mhunterld;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author MWatkins
 */
public class Tile extends OnScreenObject {

    OnScreenObject onTile;
    int terrainCost;
    private ImageIcon img;
    private Image displayImage;

    //tile width is 120 and height is 60
    public Tile(int xLocation, int yLocation) {
        super(xLocation, yLocation, 120, 100, xLocation + 120, xLocation, yLocation + 100, yLocation);
        setGraphic("tile001.png");
    }

    @Override
    public boolean isClicked(int xClicked, int yClicked) {
        int cFromLeft = xClicked - xmin;
        if (cFromLeft < 0) {
            return false;
        }
        int cFromRight = containerXMax - xClicked;
        if (cFromRight < 0) {
            return false;
        }

        //if left half of diamond
        if (xClicked <= xmin + 60 && xClicked > xmin) {
            //remember that less than means "above" because 0 is top of screen
            //we can calculate the y values of the diamond based on the x value
            //of where we clicked

            //(above bottom left side) %% (below top left side)
            if (yClicked < ymin + (cFromLeft / 2) + 50 && yClicked > ymin - (cFromLeft / 2) + 50) {
                return true;
            }

            //if right half of diamond
        } else if (xClicked > xmin + 60 && xClicked < xmin + 120) {

            //(above bottom right side %% below top right side)
            if (yClicked < ymin + (cFromRight / 2) + 50 && yClicked > ymin + 50 - (cFromRight / 2)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void checkEdge() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
