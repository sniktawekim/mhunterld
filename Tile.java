/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mhunterld;



/**
 *
 * @author MWatkins
 */
public class Tile extends OnScreenObject {

    OnScreenObject onTile;
    int terrainCost;
    int xLoc;
    int yLoc;
    int id;


    //tile width is 120 and height is 60
    public Tile(int xLocation, int yLocation) {
        super(xLocation, yLocation, 120, 100, MHunterLD.frameWidth, -120, MHunterLD.frameHeight, -85);
        setGraphic("levels/tilepic/tile001.png");
        setHighGraphic("pics/highlights/tileHighlight.png");
        setHoverGraphic("pics/highlights/tileHover.png");
    }

    @Override
    public boolean isWithin(int xLoc, int yLoc) {
        int cFromLeft = xLoc - xmin;
        if (cFromLeft < 0) {
            return false;
        }
        int cFromRight = getXMax() - xLoc;
        if (cFromRight < 0) {
            return false;
        }

        //if left half of diamond
        if (xLoc <= xmin + 60 && xLoc > xmin) {
            //remember that less than means "above" because 0 is top of screen
            //we can calculate the y values of the diamond based on the x value
            //of where we clicked

            //(above bottom left side) %% (below top left side)
            if (yLoc < ymin + (cFromLeft / 2) + 50 && yLoc > ymin - (cFromLeft / 2) + 50) {
                return true;
            }

            //if right half of diamond
        } else if (xLoc > xmin + 60 && xLoc < xmin + 120) {

            //(above bottom right side %% below top right side)
            if (yLoc < ymin + (cFromRight / 2) + 50 && yLoc > ymin + 50 - (cFromRight / 2)) {
                return true;
            }
        }
        return false;
    }

    public Tile copy() {
        Tile copy = new Tile(getXMin(),getYMin());
        copy.setGraphic(graphPath);
        copy.terrainCost = terrainCost;
        return copy;
    }


    public String getLoc(){
        return xLoc + "," + yLoc;
    }
    public void setLoc(int x, int y){
        xLoc = x;
        yLoc = y;
    }
    public void setID(int x){
        id = x;
    }
    public int getID(){
        return id;
    }

    public void replaceWith(Tile newInfo){
        setGraphic(newInfo.getGraphPath());
        setID(newInfo.getID());
        terrainCost = newInfo.terrainCost;
    }

}
