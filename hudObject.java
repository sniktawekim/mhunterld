package mhunterld;

/**
 * @author MWatkins
 */
public class hudObject extends OnScreenObject{

    private String actionName;
    hudObject(int xPos,int yPos,int xSize,int ySize, String imagePath, String aName) {
        super(xPos,yPos,xSize,ySize);
        actionName = aName;
        setGraphic(imagePath);
    }


    public String getAction(){
        return actionName;
    }
        public boolean matches(String command) {
        return actionName.compareToIgnoreCase(command) == 0;
    }
        public boolean contains(String toLook){
            return actionName.contains(toLook);
        }
}
