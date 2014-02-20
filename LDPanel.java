package mhunterld;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 * @author MWatkins
 */
public class LDPanel extends LevelPanel {

    ArrayList<String> output;
    String outputTxt = "";
    int tileLibOffset = 1;
    ArrayList<Tile> tileLibrary;
    Tile godsTile;
    hudObject tilePreview;
    int tilePrevLoc;
    Tile holeTile;

    LDPanel() {
        super();
        output = new ArrayList();
        loadTileLib();
        //now load tile library
    }

    @Override
    protected void handleClickedTile(Tile clicked) {
        clicked.setHighlight(!clicked.getHighlight());
        if (clicked.getHighlight()) {
            System.out.println("Tile ID: " + clicked.getID() + " " + clicked.getLoc() + " " + clicked.getGraphPath());
        }
    }

    @Override
    protected void buildHUD() {
        int tileRows = 12;

        super.buildHUD();
        hudObject buttonBg = new hudObject(11, 237, 476, 157, "pics/hud/leveldesigner/buttonBg.png", "");

        hudObject saveMap = new hudObject(10, 310, 100, 40, "pics/hud/leveldesigner/saveButton.png", "save");
        hudObject newMap = new hudObject(10, 350, 100, 40, "pics/hud/leveldesigner/newButton.png", "new");

        hudObject deleteSelection = new hudObject(391, 270, 100, 40, "pics/hud/leveldesigner/deleteButton.png", "delete");
        hudObject fillSelection = new hudObject(391, 310, 100, 40, "pics/hud/leveldesigner/fillButton.png", "fill");
        hudObject clearSelection = new hudObject(391, 350, 100, 40, "pics/hud/leveldesigner/clearButton.png", "clear");

        hudObject tilesTab = new hudObject(0, 0, 500, 405, "pics/hud/leveldesigner/tilesTab.png", "");

        hudObjects.add(tilesTab);
        int ypos = 27;

        for (int rows = 0; rows < tileRows; rows++) {
            hudObject newRow = new hudObject(11, ypos, 478, 17, "pics/hud/leveldesigner/row0" + ((rows % 2) + 1) + ".png", "row" + rows);
            hudString rowTileText = new hudString("Tile " + (rows + tileLibOffset), 11, ypos);
            ypos += 17;
            hudObjects.add(newRow);
            hudFonts.add(rowTileText);
        }
        Tile temp = new Tile(0, 0);
        temp.setGraphic("levels/tilepic/tile001.png");
        godsTile = temp;
        holeTile = temp;
        hudObjects.add(buttonBg);
        hudObjects.add(saveMap);
        hudObjects.add(newMap);
        hudObjects.add(clearSelection);
        hudObjects.add(fillSelection);
        hudObjects.add(deleteSelection);

    }

    @Override
    protected void hudAction(hudObject hudOb) {
        //System.out.println(hudOb.getAction());
        super.hudAction(hudOb);
        if (hudOb.matches("save")) {
            System.out.println("SAVING");
            saveLevel();
        }
        if (hudOb.matches("clear")) {
            System.out.println("CLEARING SELECTION");
            clearSelection();
        }
        if (hudOb.matches("delete")) {
            System.out.println("DELETING SELECTED");
            deleteSelection();
        }
        if (hudOb.matches("fill")) {
            System.out.println("FILLING SELECTED");
            fillSelection();
        }
        if(hudOb.contains("row")){
            int selectedTile = Integer.parseInt(hudOb.getAction().substring(3));
            Tile toReplace = new Tile(godsTile.getXMin(),godsTile.getYMin());
            String selectedTileS = String.format("%03d", selectedTile);
            toReplace.setGraphic("levels/tilepic/tile" + selectedTileS +".png");
            setGodsTile(toReplace);
        }
    }

    protected void saveLevel() {
        fillOutput();
        saveOutput();
    }

    protected void fillOutput() {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        outputTxt = timeStamp + ".lvl";

        title =  JOptionPane.showInputDialog ( "Level Name: " ); 
        outputTxt = title + ".lvl";
        output.add("<def>");
        output.add("<bgidef>pics/backgrounds/gamePanel.png</bgidef>");

        ArrayList<String> defPaths = new ArrayList();
        defPaths.add("levels/tilepic/tile000.png");

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
        output.add("<title>" + title + "</title>");
        output.add("<width>50</width>");
        output.add("<height>50</height>");
        output.add("<default>0</default>");
        output.add("<fill>true</fill>");

        for (int i = 1; i < defPaths.size(); i++) {
            output.add("<overwrite>");
            output.add("<type>" + i + "</type>");
            for (int j = 0; j < tiles.size(); j++) {
                if (tiles.get(j).getGraphPath().compareToIgnoreCase(defPaths.get(i)) == 0) {
                    String tileAdd = tiles.get(j).getLoc();
                    tileAdd = "<tile>" + tileAdd + "</tile>";
                    output.add(tileAdd);
                }
            }
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

    private void clearSelection() {
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).setHighlight(false);
        }
    }

    private void loadTileLib() {
        tileLibrary = new ArrayList();
        File tileFolder = new File(prePath + "levels/tilepic");
        File[] tilePics = tileFolder.listFiles();
        holeTile = new Tile(0, 0);
        holeTile.setGraphic("levels/tilepic/tile000.png");
        for (int i = 0; i < tilePics.length; i++) {
            String path = tilePics[i].getName();
            Tile libTile = new Tile(0, 0);
            libTile.setGraphic("levels/tilepic/" + path);
            libTile.setID(i);
            tileLibrary.add(libTile);
        }

        godsTile = tileLibrary.get(2);
        System.out.println(godsTile.getGraphPath());
        tilePreview = new hudObject(180, 270, 160, 100, godsTile.getGraphPath(), "");
        hudObjects.add(tilePreview);
        tilePrevLoc = hudObjects.size()-1;
    }

    private void deleteSelection() {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getHighlight()) {
                tiles.get(i).replaceWith(holeTile);
            }
        }
    }

    private void fillSelection() {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getHighlight()) {
                tiles.get(i).replaceWith(godsTile);
            }
        }
    }
    private void setGodsTile(Tile replaceTo){
        godsTile.replaceWith(replaceTo);
        tilePreview = new hudObject(180, 270, 160, 100, godsTile.getGraphPath(), "");
        hudObjects.set(tilePrevLoc,tilePreview);
    }

}
