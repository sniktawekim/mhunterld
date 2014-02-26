package mhunterld;

/**
 * @author MWatkins
 */
public class GamePanel extends LevelPanel {

    private Tile selected = null;
    private boolean selectMode = false;

    GamePanel() {
        super();
        int tnum = 2;
        
        ItemBox test = new ItemBox(tiles.get(tnum).getXMin(),tiles.get(tnum).getYMin());
        tiles.get(tnum).setOnTop(test);
        tiles.get(tnum).setHighlight(true);
    }

    @Override
    protected void buildHUD() {
        super.buildHUD();

    }

    @Override
    protected void hudAction(hudObject hudOb) {
        super.hudAction(hudOb);

    }

    protected void handleClickedTile(Tile clicked) {
        if (selected == null&&selectMode) {
            System.out.println("Tile ID: " + clicked.getID() + " " + clicked.getLoc() + " " + clicked.getGraphPath());
            clicked.setHighlight(true);
            selected = clicked;
        }
    }

}
