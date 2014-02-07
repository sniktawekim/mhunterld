package mhunterld;

/**
 * @author MWatkins
 */
public class MenuPanel extends MPanel{

    MenuPanel() {
        super();
    }



@Override
    protected void buildHUD() {
        hudObject chars = new hudObject(canvasWidth/2-200, 0, 400, 200, "pics/hud/titlemenu/options/Characters.png", "chars");
        hudObject ld = new hudObject(canvasWidth/2-200, 200, 400, 200, "pics/hud/titlemenu/options/LevelDesigner.png", "ld");
        hudObject play = new hudObject(canvasWidth/2-200,400, 400, 200, "pics/hud/titlemenu/options/Play.png", "play");
        hudObject shop = new hudObject(canvasWidth/2-200,600, 400, 200, "pics/hud/titlemenu/options/Shop.png", "shop");
        
        hudObjects.add(ld);
        hudObjects.add(chars);
        hudObjects.add(shop);
        hudObjects.add(play);
    }

    protected void hudAction(hudObject hudOb) {
        if (hudOb.matches("ld")) {
            status = "ld";
        }
        if (hudOb.matches("chars")) {
            status = "chars";
        }
        if (hudOb.matches("shop")) {
            status = "shop";
        }
        if (hudOb.matches("play")) {
            status = "play";
        }
    }


}
