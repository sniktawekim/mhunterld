package mhunterld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author MWatkins
 */
public class gamePanel extends JPanel {

    final int canvasWidth = 1296;
    final int canvasHeight = 770;
    final int backgroundWidth = 1300;
    String bgiPath = "testBg.png";
    static int tilesLeft = 1;
    static int tilesRight = 1;
    IClick myClick;//mouse listener, useful for menu options
    ArrayList<Tile> tiles;
    ArrayList<hudObject> hudObjects;
    private ImageIcon graphic;
    private Image gr;
    Board currentBoard;

    int xOffset = 0;
    int yOffset = 0;

    gamePanel(Board gBoard) {
        currentBoard = gBoard;
        setBorder(BorderFactory.createLineBorder(Color.black));
        tiles = new ArrayList<>();
        hudObjects = new ArrayList<>();
        myClick = new IClick();
        this.addMouseListener(myClick);
        loadBoard(gBoard);
        buildHUD();
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
        for (int i = 0; i < tiles.size(); i++) {
            OnScreenObject current = tiles.get(i);
            int xpos = current.getXMin() + xOffset;
            int ypos = current.getYMin() + yOffset;
            g.setColor(current.getColor());
            if (current.getGraphic() != null) {//try to paint the object's image
                g.drawImage(current.getGraphic(), xpos, ypos, this);
            } else {//if it fails to load the image, paint it as a default color
                g.fillOval(xpos, ypos, current.getXSize(), current.getYSize());
            }
        }
        for (int i = 0; i < hudObjects.size(); i++) {
            OnScreenObject current = hudObjects.get(i);
            int xpos = current.getXMin();
            int ypos = current.getYMin();
            g.setColor(current.getColor());
            if (current.getGraphic() != null) {//try to paint the object's image
                g.drawImage(current.getGraphic(), xpos, ypos, this);
            } else {//if it fails to load the image, paint it as a default color
                g.fillOval(xpos, ypos, current.getXSize(), current.getYSize());
            }
        }
    }

    public String check() {
        return "testing";
    }

    private void checkClick() {
        if (!myClick.getClicked()) {
            return;
        }
        int xClicked = myClick.getX();
        int yClicked = myClick.getY();

        for (int i = 0; i < hudObjects.size(); i++) {
            hudObject current = hudObjects.get(i);
            if (current.isClicked(xClicked, yClicked)) {
                hudAction(current.getAction());
                return;
            }
        }

        xClicked = myClick.getX() - xOffset;
        yClicked = myClick.getY() - yOffset;
        for (int i = 0; i < tiles.size(); i++) {
            Tile current = tiles.get(i);
            if (current.isClicked(xClicked, yClicked)) {
                System.out.println(current.getID() + current.getLoc() + " " + current.getGraphPath());
                return;
            }
        }
    }

    private void loadBoard(Board gBoard) {
        gBoard.getAllTiles(tiles);
        bgiPath = gBoard.getBGI();
    }

    private void buildHUD() {
        hudObject leftArrow = new hudObject(0, canvasHeight / 2 - 25, 40, 50, "lha.png", "lha");
        hudObject rightArrow = new hudObject(canvasWidth - 40, canvasHeight / 2 - 25, 40, 50, "rha.png", "rha");
        hudObject upArrow = new hudObject(canvasWidth / 2 - 25, 0, 50, 40, "uha.png", "uha");
        hudObject downArrow = new hudObject(canvasWidth / 2 - 25, canvasHeight - 40, 50, 40, "dha.png", "dha");

        hudObjects.add(leftArrow);
        hudObjects.add(rightArrow);
        hudObjects.add(upArrow);
        hudObjects.add(downArrow);
    }

    private void hudAction(String action) {
        if (matches(action, "lha")) {
            shift(400, 0);
        }
        if (matches(action, "rha")) {
            shift(-400, 0);
        }
        if (matches(action, "dha")) {
            shift(0, -400);
        }
        if (matches(action, "uha")) {
            shift(0, 400);
        }
    }

    private boolean matches(String action, String command) {
        return action.compareToIgnoreCase(command) == 0;
    }

    private void shift(int x, int y) {
        
        if(!(-1*xOffset+canvasWidth>currentBoard.getRightBarrier())&&x<0){//RIGHT ARROW PRESSED, SHIFTING BOARD LEFT
            xOffset += x;
        }
        if(!(xOffset+canvasWidth>currentBoard.getRightBarrier())&&x>0){//LEFT ARROW PRESSED, SHIFTING BOARD RIGHT
            xOffset += x;
        }
        
        if ((yOffset < currentBoard.getUpperBarrier()) && y>0) {//UP ARROW PRESSED, SHIFTING BOARD DOWN
            yOffset += y;
        }
        if (!((-1*yOffset) + canvasHeight > currentBoard.getLowerBarrier()) && y<0) {//DOWN ARROW PRESSED, SHIFTING BOARD UP
            yOffset += y;
        }
                
    }
}
