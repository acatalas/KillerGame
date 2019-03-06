/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame;

import java.awt.Color;

/**
 *
 * @author Ale
 */
public abstract class Static extends VisibleObject{
    
    public Static(KillerGame killerGame, Color color, double x, double y, 
            int width, int height) {
        super(killerGame, color, x, y, width, height);
    }
    
}
