package mhunterld;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author MWatkins
 */
public abstract class LevelPanel extends MPanel {

    String title = "boardName";
    static int tilesLeft = 1;
    static int tilesRight = 1;
    ArrayList<Tile> tiles;
    Board currentBoard;
    int xOffset = 0;
    int yOffset = 0;

    LevelPanel() {
        super();
        try {
            currentBoard = new Board("levels/default.lvl");//if in jar
        } catch (Exception e) {//if not in jar
            System.out.println("Hi, Mike!");
            currentBoard = new Board("src/mhunterld/levels/default.lvl");
        }
        tiles = new ArrayList<>();
        loadBoard(currentBoard);
    }

    protected void paintObjects(Graphics g) {
        for (int i = 0; i < tiles.size(); i++) {
            Tile current = tiles.get(i);
            current.paint(xOffset, yOffset, g, this);

        }
        super.paintObjects(g);
    }

    protected void checkClick() {
        if (!myClick.getClicked()) {
            return;
        }       
        super.checkClick();//checks hud clicking
        if (!hudclicked) {
            int xClicked = myClick.getX() - xOffset;
            int yClicked = myClick.getY() - yOffset;
            for (int i = 0; i < tiles.size(); i++) {
                Tile current = tiles.get(i);
                if (current.isWithin(xClicked, yClicked)) {
                    handleClickedTile(current);
                    return;
                }
            }
        }
    }

    @Override
    protected void buildHUD() {
        hudObject leftArrow = new hudObject(0, canvasHeight / 2 - 25, 40, 50, "pics/hud/arrows/lha.png", "lha");
        hudObject rightArrow = new hudObject(canvasWidth - 40, canvasHeight / 2 - 25, 40, 50, "pics/hud/arrows/rha.png", "rha");
        hudObject upArrow = new hudObject(canvasWidth / 2 - 25, 0, 50, 40, "pics/hud/arrows/uha.png", "uha");
        hudObject downArrow = new hudObject(canvasWidth / 2 - 25, canvasHeight - 40, 50, 40, "pics/hud/arrows/dha.png", "dha");

        hudObjects.add(leftArrow);
        hudObjects.add(rightArrow);
        hudObjects.add(upArrow);
        hudObjects.add(downArrow);
    }

    protected abstract void handleClickedTile(Tile clicked);

    private void loadBoard(Board gBoard) {
        gBoard.getAllTiles(tiles);
        bgiPath = gBoard.getBGI();
        title = gBoard.title;
    }

    @Override
    protected void hudAction(hudObject hudOb) {
        if (hudOb.matches("lha")) {
            shift(400, 0);
        }
        if (hudOb.matches("rha")) {
            shift(-400, 0);
        }
        if (hudOb.matches("dha")) {
            shift(0, -400);
        }
        if (hudOb.matches("uha")) {
            shift(0, 400);
        }
    }

    private void shift(int x, int y) {

        if (!(-1 * xOffset + canvasWidth > currentBoard.getRightBarrier()) && x < 0) {//RIGHT ARROW PRESSED, SHIFTING BOARD LEFT
            xOffset += x;
        }
        if (!(xOffset + canvasWidth > currentBoard.getRightBarrier()) && x > 0) {//LEFT ARROW PRESSED, SHIFTING BOARD RIGHT
            xOffset += x;
        }

        if ((yOffset < currentBoard.getUpperBarrier()) && y > 0) {//UP ARROW PRESSED, SHIFTING BOARD DOWN
            yOffset += y;
        }
        if (!((-1 * yOffset) + canvasHeight > currentBoard.getLowerBarrier()) && y < 0) {//DOWN ARROW PRESSED, SHIFTING BOARD UP
            yOffset += y;
        }

    }
}
