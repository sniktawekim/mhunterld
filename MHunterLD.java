package mhunterld;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * MWatkins Feb 4, 2014
 */
public class MHunterLD {

    static ArrayList<String> currentFile;
    static BufferedReader br = null;
    static int startX = 100;
    static int startY = 100;
    static Board board;

    private static void init() {
        currentFile = new ArrayList<>();
    }

    public static void main(String[] args) {
        init();
        readFile("src/mhunterld/levels/default.txt");
        buildFromFile();
    }

    static private void readFile(String path) {
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));

            while ((sCurrentLine = br.readLine()) != null) {
                currentFile.add(sCurrentLine);
            }
        } catch (IOException e) {
            System.out.println("Error in " + path + ": " + e);
        }
    }

    private static void buildFromFile() {
        for(int i=0;i<currentFile.size();i++){
            String cLine = currentFile.get(i);
            if(cLine.contains("<def>")){
                i=buildDef(i);
            }
            if(cLine.contains("<board>")){
                i=buildBoard(i);
            }
        }
    }

    private static int buildDef(int i) {
        System.out.println("building Def");
        String cLine = currentFile.get(i);
        while(!cLine.contains("</def>")){
        if(cLine.contains("<tiledef>")){
            i=buildTileDef(i);
        }
        if(cLine.contains("<bgidef>")){
            i=setBGI(i);
        }
        i++;
        cLine=currentFile.get(i);
    }
        return i;
    }

    private static int buildBoard(int i) {
        System.out.println("building board");
        return i;
    }

    private static int buildTileDef(int i) {
        System.out.println("building tile definition");
        return i;
    }

    private static int setBGI(int i) {
        System.out.println("setting background image");
        return i;
    }

/*
    private void makeTiles() {
        for (int i = 0; i < 5; i++) {
            Tile toadd = new Tile( startX + (i * 60), startY + (i * 30));
            tiles.add(toadd);
        }
    }
    */
}