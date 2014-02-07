package mhunterld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

/**
 * @author MWatkins
 */
public class CharacterPanel extends MPanel {

    
    static int tilesLeft = 1;
    static int tilesRight = 1;
    ArrayList<Tile> tiles;
    private ImageIcon graphic;
    private Image gr;

    int xOffset = 0;
    int yOffset = 0;

    CharacterPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        tiles = new ArrayList<>();
        hudObjects = new ArrayList<>();
        myClick = new IClick();
        this.addMouseListener(myClick);
        buildHUD();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintBackground(g);
        super.paintComponent(g);
        paintBackground(g);
        paintObjects(g);
        checkClick();

    }


    protected void paintObjects(Graphics g) {
        for (int i = 0; i < tiles.size(); i++) {
            Tile current = tiles.get(i);
            current.paint(xOffset, yOffset, g, this);

        }
        for (int i = 0; i < hudObjects.size(); i++) {
            hudObject current = hudObjects.get(i);
            current.paint(0, 0, g, this);
        }
    }

    protected void checkClick() {
    }

@Override
    protected void buildHUD() {
        hudObject leftArrow = new hudObject(0, canvasHeight / 2 - 25, 40, 50, "lha.png", "lha");
        hudObjects.add(leftArrow);
    }

    protected void hudAction(hudObject hudOb) {
        if (hudOb.matches("lha")) {
            //do stuff
        }
    }



}
