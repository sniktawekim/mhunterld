package mhunterld;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author MWatkins
 */
public class LDPanel extends LevelPanel {

    ArrayList<String> output;
    String outputTxt = "";

    LDPanel() {
        super();
        output = new ArrayList();
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
    protected void buildHUD() {
        super.buildHUD();
        hudObject saveMap = new hudObject(10, 300, 50, 40, "pics/hud/leveldesigner/saveButton.png", "save");
        hudObjects.add(saveMap);
    }

    @Override
    protected void hudAction(hudObject hudOb) {
        super.hudAction(hudOb);
        if (hudOb.matches("save")) {
            fillOutput();
            saveOutput();
        }
    }

    protected void fillOutput() {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        outputTxt = timeStamp + ".lvl";

        output.add("<def>");
        output.add("<bgidef>pics/backgrounds/gamePanel.png</bgidef>");

        ArrayList<String> defPaths = new ArrayList();
        defPaths.add("levels/tilepic/hole.png");

        //adding defpaths
        for (int i = 0; i < tiles.size(); i++) {
            String tilePath = tiles.get(i).getGraphPath();
            boolean found = false;
            for (int j = 0; j < defPaths.size(); j++) {
                if (tilePath.compareToIgnoreCase(defPaths.get(j)) == 0) {
                    found = true;
                }
            }
            if (!found) {
                defPaths.add(tilePath);
            }
        }

        //making tiledefs
        for (int i = 0; i < defPaths.size(); i++) {
            output.add("<tiledef>");
            output.add("<id>" + i + "</id>");
            if (i == 0) {
                output.add("<cost>0</cost>");
            } else {
                output.add("<cost>1</cost>");
            }
            output.add("<path>" + defPaths.get(i) + "</path>");
            output.add("</tiledef>");
        }

        output.add("</def>");
        output.add("<board>");
        output.add("<title>My First Board</title>");
        output.add("<width>50</width>");
        output.add("<height>50</height>");
        output.add("<default>1</default>");
        output.add("<fill>true</fill>");
        //adding tile overwrites
        for (int i = 0; i < tiles.size(); i++) {
            String tileAdd = tiles.get(i).getLoc();
            tileAdd = "<tile>" + tileAdd + "</tile>";
            output.add("<overwrite>");
            output.add(tileAdd);
            output.add("</overwrite>");

        }
        output.add("</board>");
    }

    protected void saveOutput() {
        try {
            PrintWriter writer = new PrintWriter(outputTxt, "UTF-8");//for output file

            for (int i = 0; i < output.size(); i++) {
                writer.println(output.get(i));
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        output = new ArrayList();
    }

}
