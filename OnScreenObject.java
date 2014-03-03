package mhunterld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * This class defines what every object that appears on the screen will need.
 *
 * @author MWatkins
 */
public abstract class OnScreenObject {

    private ImageIcon graphic;
    private ImageIcon highGraph;
    private ImageIcon hoverGraph;
    Image g;
    Image h;
    Image o;
    protected int xmin;//left bound of object
    protected int ymin;//right bound of object
    protected int xsize;//horizontal size of object
    protected int ysize;//vertical size of object
    protected int rise;//vertical movement per redraw
    protected int run;//horizontal movement per redraw
    protected int containerYMax;//object cannot vertically go beyond
    protected int containerYMin;//object cannot vertically go under
    protected int containerXMax;//object cannot horizontally go beyond
    protected int containerXMin;//object cannot horizontally go under
    protected String graphPath;
    protected String highPath;

    protected boolean visible;//toggles existence of object

    protected Color color;//color of object, if not a graphic
    private boolean highlight = false;
    private String hoverPath;
    private boolean hovered = false;

    OnScreenObject(int x, int y, int sizeX, int sizeY) {
        xmin = x;
        ymin = y;
        xsize = sizeX;
        ysize = sizeY;
        containerXMax = MHunterLD.frameWidth;
        containerXMin = -sizeX;
        containerYMax = MHunterLD.frameHeight;
        containerYMin = -sizeY;

        //default the object to no movement
        rise = 0;
        run = 0;

        visible = true;
    }

    public int getXMin() {
        return xmin;
    }

    public int getYMin() {
        return ymin;
    }

    public int getXMax() {
        return xmin + xsize;
    }

    public int getYMax() {
        return ymin + ysize;
    }

    public int getXSize() {
        return xsize;
    }

    public int getYSize() {
        return ysize;
    }

    public Color getColor() {
        return color;
    }

    public boolean getVisible() {
        return visible;
    }

    public int getRun() {
        return run;
    }

    public Image getGraphic() {
        return g;
    }

    public Image getHigh() {
        return h;
    }

    public Image getHover() {
        return o;
    }

    /**
     *
     * @param x x coords of point
     * @param y y coords of point
     * @return true if the parameter coords are within this object.
     */
    public boolean isWithin(int x, int y) {
        return x >= xmin && x <= getXMax() && y >= ymin && y <= getYMax();
    }

    public void setXMin(int x) {
        xmin = x;
    }

    public void setYMin(int y) {
        ymin = y;
    }

    public void setXSize(int x) {
        xsize = x;
    }

    public void setYSize(int y) {
        ysize = y;
    }

    /**
     * Defines a picture for this object to display as
     *
     * @param setto is the path of the image to use
     */
    public void setGraphic(String setto) {
        graphPath = setto;
        try {
            graphic = new ImageIcon(this.getClass().getResource(setto));
            g = graphic.getImage();
        } catch (Exception e) {
            System.out.print("OnScreenObject setGraphic caught: ");
            System.out.print(e);
            System.out.println(" " + graphPath);
        }
    }

    public void setHighGraphic(String setto) {
        highPath = setto;
        try {
            highGraph = new ImageIcon(this.getClass().getResource(setto));
            h = highGraph.getImage();
        } catch (Exception e) {
            System.out.print("OnScreenObject setGraphic caught: ");
            System.out.print(e);
            System.out.println(" " + highPath);
        }

    }

    public void setHoverGraphic(String setto) {
        hoverPath = setto;
        try {
            hoverGraph = new ImageIcon(this.getClass().getResource(setto));
            o = hoverGraph.getImage();
        } catch (Exception e) {
            System.out.print("OnScreenObject setGraphic caught: ");
            System.out.print(e);
            System.out.println(" " + hoverPath);
        }

    }

    public String getGraphPath() {
        return graphPath;
    }

    public String getHoverPath() {
        return hoverPath;
    }

    /**
     * This method changes the movement of the object
     *
     * @param riseM set the vertical change of the object
     * @param runM set the horizontal change of the object
     */
    public void setMovement(int riseM, int runM) {
        rise = riseM * -1;//rise means to go up, and negative will update it up
        run = runM;
    }

    /**
     * This method accelerates the object by the parameters
     *
     * @param horizontal if the acceleration should be horizontal or vertical
     * @param amount quantity to increment acceleration by
     */
    public void nudge(boolean horizontal, int amount) {
        if (horizontal) {
            run += amount;
        } else {
            rise += amount;
        }
    }

    public void setVisible(boolean vis) {
        visible = vis;
    }

    public void setColor(Color c) {
        color = c;
    }

    /**
     * used to process how the object should change with every screen refresh
     */
    public void update() {
        xmin += run;
        ymin += rise;
    }

    int getRise() {
        return rise;
    }

    public void setRise(int amount) {
        rise = amount;
    }

    public void setRun(int amount) {
        run = amount;
    }

    public void setHighlight(boolean setto) {
        highlight = setto;
    }

    public boolean getHighlight() {
        return highlight;
    }

    private void setHovered(boolean setto) {
        hovered = setto;
    }

    public boolean getHovered() {
        return hovered;
    }

    public void paint(int xOffset, int yOffset, Graphics g, ImageObserver lulz, IClick mouse) {
        int xpos = getXMin() + xOffset;
        int ypos = getYMin() + yOffset;
        if (isOnScreen(xpos, ypos)) {
            g.drawImage(getGraphic(), xpos, ypos, lulz);
            if (highlight) {
                g.drawImage(getHigh(), xpos, ypos, lulz);
            }
            if (calcHoveredStatus(xOffset, yOffset, mouse)) {
                g.drawImage(getHover(), xpos, ypos, lulz);
                setHovered(true);
                calcHighlightStatus(mouse);
            } else {
                setHovered(false);
            }
        }
    }

    public boolean isOnScreen(int x, int y) {
        boolean isntRight = x < containerXMax;
        boolean isntLeft = (x + xsize) > containerXMin;
        boolean isntBelow = y < containerYMax;
        boolean isntAbove = (y + ysize) > 0;
        return isntRight && isntLeft && isntBelow && isntAbove;
    }

    protected void calcHighlightStatus(IClick mouse) {
        return;
    }

    protected boolean calcHoveredStatus(int xOffset, int yOffset, IClick mouse) {
        return isWithin(mouse.getX() - xOffset, mouse.getY() - yOffset) && !mouse.getPress();
    }
}
