/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mhunterld;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author MWatkins
 */
public class ItemBox extends OnScreenObject {

    private Item contents;

    public ItemBox(int tilex, int tiley) {
        super(tilex, tiley - 40, 120, 140);
        setGraphic(randBox());
    }

    private String randBox() {
        ArrayList<String> graphLib = loadLib();
        Random generator = new Random();
        if (graphLib.size() > 0) {
            return graphLib.get(generator.nextInt(graphLib.size()));
        } else {
            return "failure to load";
        }
    }

    private ArrayList<String> loadLib() {
        ArrayList<String> loadLib = new ArrayList();
        File boxFolder = new File( LevelPanel.prePath + "pics/itemBox");
        //prePath + 
        File[] boxPics = boxFolder.listFiles();

        try {
            for (int i = 0; i < boxPics.length; i++) {
                String path = "pics/itemBox/" + boxPics[i].getName();
                loadLib.add(path);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getCause());
        }

        return loadLib;
    }

}
