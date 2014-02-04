package mhunterld;

/**
 * @author MWatkins
 */
class Board {
    Tile tiles[][];
    String bgi = "bgi.png";
    int defaultTile;
    boolean fill = false;
    Board(){
        
    }
    private void setBoardSize(int x, int y){
        tiles = new Tile[x][y];
    }
    private void setBGI(String path){
        bgi = path;
    }
    private void setFill(boolean toFill){
        fill = toFill;
    }
    private void setDefaultTile(int toD){
        defaultTile = toD;
    }
}

