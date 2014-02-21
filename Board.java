package mhunterld;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author MWatkins
 */
class Board {

    Tile tiles[][];
    int board[][];
    
    static ArrayList<String> currentFile;
    static ArrayList<Tile> tiledef;
    final int canvasWidth = 1175;
    static BufferedReader br = null;

    String bgi = "bgi.png";
    String filepath = "levels/default.lvl";

    String title = "Level";
    int defaultTile = 0;
    boolean fill = false;
    int tilesLeft = 10;
    int tilesRight = 10;

    Board(String levelFilePath) {
        currentFile = new ArrayList<>();
        tiledef = new ArrayList<>();
        filepath = levelFilePath;
        levelLoader();
    }

    private String removeXML(String toremove) {
        toremove = toremove.trim();
        int startpoint = toremove.indexOf(">") + 1;
        int endpoint = toremove.indexOf("</");
        toremove = toremove.substring(startpoint, endpoint);
        return toremove;
    }

    private void setBGI(String path) {
        bgi = path;
    }

    private void setFill(boolean toFill) {
        fill = toFill;
    }

    private void setDefaultTile(int toD) {
        defaultTile = toD;
    }

    public void setBoardSize(int left, int right) {
        tilesLeft = left;
        tilesRight = right;
        tiles = new Tile[tilesLeft][tilesRight];
        board = new int[tilesLeft][tilesRight];
    }

    private void readFile() {
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(filepath));

            while ((sCurrentLine = br.readLine()) != null) {
                currentFile.add(sCurrentLine);
            }
        } catch (IOException e) {
            
        }
        try {
            br.close();
        } catch(Exception e){
            
        }
    }

    private void levelLoader() {

        try {
            readFile();
        } catch (Exception ex) {
            System.out.println("LEVEL FILE NOT FOUND");
            System.exit(0);
        }

        for (int i = 0; i < currentFile.size(); i++) {
            String cLine = currentFile.get(i);
            if (cLine.contains("<def>")) {
                i = buildDef(i);
            }
            if (cLine.contains("<board>")) {
                i = buildBoard(i);
            }
        }
        makeTiles();
    }

    private int buildDef(int i) {
        String cLine = currentFile.get(i);
        while (!cLine.contains("</def>")) {
            if (cLine.contains("<tiledef>")) {
                i = buildTileDef(i);
            }
            if (cLine.contains("<bgidef>")) {
                cLine = removeXML(cLine);
                setBGI(cLine);
            }
            i++;
            cLine = currentFile.get(i);
        }
        return i;
    }

    private int buildBoard(int i) {
        String cLine = currentFile.get(i);
        while (!cLine.contains("</board>")) {
            if (cLine.contains("<title>")) {
                cLine = removeXML(cLine);
                title = cLine;
            }
            if (cLine.contains("<width>")) {
                cLine = removeXML(cLine);
                tilesLeft = Integer.parseInt(cLine);
            }
            if (cLine.contains("<height>")) {
                cLine = removeXML(cLine);
                tilesRight = Integer.parseInt(cLine);
            }
            if (cLine.contains("<default>")) {
                cLine = removeXML(cLine);
                defaultTile = Integer.parseInt(cLine);
            }
            if (cLine.contains("<fill>")) {
                cLine = removeXML(cLine);
                if (cLine.compareToIgnoreCase("true") == 0) {
                    setBoardSize(tilesLeft, tilesRight);
                    fillBoardArray();
                }
            }
            if (cLine.contains("<overwrite>")) {
                i = overWrite(i);
            }

            i++;
            cLine = currentFile.get(i);
        }
        return i;
    }

    private int buildTileDef(int i) {

        Tile toadd = new Tile(0, 0);
        boolean error = true;
        String cLine = currentFile.get(i);
        while (!cLine.contains("</tiledef>")) {

            if (cLine.contains("<id>")) {
                cLine = removeXML(cLine);
                int tileID = Integer.parseInt(cLine);
                if (tileID != tiledef.size()) {
                    System.out.println("TILE DEF ID ERROR: " + tiledef.size());
                } else {
                    error = false;
                }
            }
            if (cLine.contains("<cost>")) {
                cLine = removeXML(cLine);
                toadd.terrainCost = Integer.parseInt(cLine);
            }
            if (cLine.contains("<path>")) {
                cLine = removeXML(cLine);
                toadd.setGraphic(cLine);
            }
            i++;
            cLine = currentFile.get(i);
        }

        if (!error) {
            tiledef.add(toadd);
        }
        return i;
    }

    private void makeTiles() {
        for (int i = 0; i < tilesLeft; i++) {
            for (int j = 0; j < tilesRight; j++) {
                Tile toAdd;
                toAdd = tiledef.get(board[i][j]).copy();
                toAdd.setID(board[i][j]);
                toAdd.setXMin(canvasWidth / 2 - (j * 60) + (i * 60));
                toAdd.setYMin(-15 + (35 * j) + (i * (50 - 15)));
                toAdd.setLoc(i+1,tilesRight - j);
                tiles[i][j] = toAdd;
            }
        }
    }

    void getAllTiles(ArrayList<Tile> objects) {
        for (int i = 0; i < tilesLeft; i++) {
            for (int j = 0; j < tilesRight; j++) {
                objects.add(tiles[i][j]);
            }
        }
    }

    Tile getTile(int x, int y) {
        return tiles[x-1][tilesLeft - y];
    }

    private void fillBoardArray() {
        for (int i = 0; i < tilesLeft; i++) {
            for (int j = 0; j < tilesRight; j++) {
                board[i][j] = defaultTile;
            }
        }
    }

    private void setIndTile(int x, int y, int tiledefint) {
        x--;
        y = tilesRight - y;
        board[x][y] = tiledefint;
    }

    private int overWrite(int i) {
        int type = defaultTile;
        String cLine = currentFile.get(i);
        while (!cLine.contains("</overwrite>")) {
            if (cLine.contains("<type>")) {
                cLine = removeXML(cLine);
                type = Integer.parseInt(cLine);
            }
            if (cLine.contains("<tile>")) {
                cLine = removeXML(cLine);
                String[] coords = cLine.split(",");
                setIndTile(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), type);
            }

            i++;
            cLine = currentFile.get(i);
        }
        return i;
    }

    String getBGI() {
        return bgi;
    }
    public int getLeftBarrier(){
        Tile left = getTile(1,1);
        int leftmost = left.getXMin() - 20;
        return leftmost;
    }
    public int getRightBarrier(){
        Tile right = getTile(tilesLeft,tilesLeft);
        int rightmost = right.getXMax() +20;
        return rightmost;
    }
    public int getUpperBarrier(){
        Tile top = getTile(1,tilesRight);   
        int topmost = top.getYMin() + 20;
        return topmost;
    }
    public int getLowerBarrier(){
        Tile bot = getTile(tilesLeft,1);
        int bottommost = bot.getYMax() +20;
        return bottommost;
    }
    public void clearHighlights(){
        for(int i=0;i<tilesLeft;i++){
            for(int j=0; j<tilesRight; j++){
                tiles[i][j].setHighlight(false);
            }
        }
    }

}
