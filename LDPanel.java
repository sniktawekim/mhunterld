package mhunterld;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.BorderFactory;

/**
 * @author MWatkins
 */
public class LDPanel extends LevelPanel {

LDPanel(){
    super();
    //now load tile library
}

    @Override
    protected void handleClickedTile(Tile clicked) {
        clicked.setHighlight(!clicked.getHighlight());
        if (clicked.getHighlight()) {
            System.out.println(clicked.getID() + clicked.getLoc() + " " + clicked.getGraphPath());
        }
    }
    @Override
    protected void buildHUD(){
        super.buildHUD();
        
    }
    @Override
    protected void hudAction(hudObject hudOb) {
        super.hudAction(hudOb);
    }

}
