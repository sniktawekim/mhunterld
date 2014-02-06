package mhunterld;

/**
 * @author MWatkins
 */
public class hudObject extends OnScreenObject{

    private String actionName;
    hudObject(int xPos,int yPos,int xSize,int ySize, String imagePath, String aName) {
        super(xPos,yPos,xSize,ySize,1300,0,800,0);
        actionName = aName;
        setGraphic(imagePath);
    }

    @Override
    public boolean isClicked(int x, int y) {
        return isWithin(x,y);
    }
    public String getAction(){
        return actionName;
    }
}
