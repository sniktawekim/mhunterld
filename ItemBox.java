/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mhunterld;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import static mhunterld.LevelPanel.prePath;

/**
 *
 * @author MWatkins
 */
public class ItemBox extends OnScreenObject{
    private Item contents;

    public ItemBox(int tilex, int tiley) {
        super(tilex, tiley-40, 120, 140, MHunterLD.frameWidth, 0, MHunterLD.frameHeight, 0);
        setGraphic(randBox());
    }

    private String randBox() {
        ArrayList<String> graphLib = loadLib();
        Random generator = new Random(); 
        return graphLib.get(generator.nextInt(graphLib.size()));
    }

    private ArrayList<String> loadLib() {
        ArrayList<String> loadLib = new ArrayList();
        File boxFolder = new File("pics/itemBox");
        //prePath + 
        File[] boxPics = boxFolder.listFiles();

        try{
        for (int i = 0; i < boxPics.length; i++) {
            String path = "pics/itemBox/" + boxPics[i].getName();
            loadLib.add(path);
        }
        } catch(NullPointerException e){
            System.out.println(e.getCause());
        }

        return loadLib;
    }
    
    
}
