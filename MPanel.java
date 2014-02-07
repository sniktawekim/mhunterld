/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mhunterld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author MWatkins
 */
public abstract class MPanel extends JPanel {

    final int canvasWidth = 1296;
    final int canvasHeight = 770;
    final int backgroundWidth = 1300;
    String bgiPath = "pics/backgrounds/default.png";
    ArrayList<hudObject> hudObjects;
    protected ImageIcon bgIcon;
    protected Image bgImage;
    IClick myClick;//mouse listener, useful for menu options
    boolean hudclicked = false;
    protected String status = "good";

    protected abstract void buildHUD();

    protected abstract void hudAction(hudObject current);

    MPanel() {
        hudObjects = new ArrayList<>();
        myClick = new IClick();
        this.addMouseListener(myClick);
        setBorder(BorderFactory.createLineBorder(Color.black));
        buildHUD();
    }

    protected void paintBackground(Graphics g) {
        try {//try to paint background image

            bgIcon = new ImageIcon(this.getClass().getResource(bgiPath));
            bgImage = bgIcon.getImage();
            g.drawImage(bgImage, 0, 0, this);

        } catch (Exception e) {//if it fails, paint a blue rectangle
            g.setColor(Color.blue);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        paintObjects(g);
        checkClick();
    }

    protected void paintObjects(Graphics g) {
        for (int i = 0; i < hudObjects.size(); i++) {
            hudObject current = hudObjects.get(i);
            current.paint(0, 0, g, this);
        }
    }

    protected void checkClick() {
        hudclicked = false;
        int xClicked = myClick.getX();
        int yClicked = myClick.getY();
        for (int i = 0; i < hudObjects.size(); i++) {
            hudObject current = hudObjects.get(i);
            if (current.isWithin(xClicked, yClicked)) {
                hudAction(current);
                hudclicked = true;
                return;
            }
        }

    }
    protected String update(){
        return status;
    }

}
