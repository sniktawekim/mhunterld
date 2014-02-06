package mhunterld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MWatkins
 */
public class gamePanel extends JPanel {

    final int canvasWidth = 1175;
    final int canvasHeight = 770;
    final int backgroundWidth = 1300;
    String bgiPath = "testBg.png";
    static int tilesLeft=1;
    static int tilesRight=1;
    IClick myClick;//mouse listener, useful for menu options
    ArrayList<OnScreenObject> objects;
    private ImageIcon graphic;
    private Image gr;
    
    int board[][];

    gamePanel(Board gBoard) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        objects = new ArrayList<>();
        myClick = new IClick();
        this.addMouseListener(myClick);
        loadBoard(gBoard);
        
    }





@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        paintBackground(g);
        paintObjects(g);
        checkClick();
    }

    private void paintBackground(Graphics g) {
        try {//try to paint background image

            graphic = new ImageIcon(this.getClass().getResource(bgiPath));           
            gr = graphic.getImage();
            g.drawImage(gr, 0, 0, this);

        } catch (Exception e) {//if it fails, paint a blue rectangle
            g.setColor(Color.blue);
            g.fillRect(0, 0, getWidth(), getHeight());
            System.out.println("BG Error: " + e);
        }

    }

    private void paintObjects(Graphics g) {

        for (int i = 0; i < objects.size(); i++) {
            OnScreenObject current = objects.get(i);
            g.setColor(current.getColor());
            if (current.getGraphic() != null) {//try to paint the object's image
                g.drawImage(current.getGraphic(), current.getXMin(), current.getYMin(), this);
            } else {//if it fails to load the image, paint it as a default color
                g.fillOval(current.getXMin(), current.getYMin(), current.getXSize(), current.getYSize());
            }
        }
    }
    public String check(){
        return "testing";
    }
    private void checkClick() {
        if (!myClick.getClicked()) {
            return;
        }
        for (int i = 0; i < objects.size(); i++) {
            OnScreenObject current = objects.get(i);
            if (current.isClicked(myClick.getX(), myClick.getY())) {
                System.out.println("CLICKED");
            }

        }
    }

    private void loadBoard(Board gBoard) {
        gBoard.getAllTiles(objects);
        bgiPath = gBoard.getBGI();
    }

}
