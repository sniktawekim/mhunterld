/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mhunterld;

/**
 *
 * @author MWatkins
 */
public class Character extends OnScreenObject {
    private int movement = 0;
    private int attack = 0;
    private int defense = 0;
    private int maxHP = 10;
    private int currentHP = maxHP;
    private int preHitHP = currentHP;
    private String name = "";
    private CharModel model;

    public Character(CharModel characterModel, int cxMax, int cxMin, int cyMax, int cyMin) {
       // int xSize = characterModel.getXSize();
        //int ySize = characterModel.getYSize();
        super(0, 0, 0,0);
    }
    
    
}
