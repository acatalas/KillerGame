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
public class KillerMeteor extends Autonomous {
    
    public KillerMeteor(KillerGame killerGame, Color color, 
            double x, double y, double xSpeed, double ySpeed,
            int width, int height) {
        super(killerGame, color, x, y, xSpeed, ySpeed, width, height);
    }
    
}
