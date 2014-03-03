package mhunterld;

/**
 * @author MWatkins
 */
public class GamePanel extends LevelPanel {

    private Tile selected = null;
    private boolean selectMode = false;

    GamePanel() {
        super();
        for(int i=0;i<6;i++){
        ItemBox test = new ItemBox(tiles.get(i).getXMin(),tiles.get(5).getYMin());
        tiles.get(i).setOnTop(test);
        }
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
