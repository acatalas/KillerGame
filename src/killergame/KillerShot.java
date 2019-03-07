/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Ale
 */
public class KillerShot extends Autonomous{
    
    public KillerShot(KillerGame killerGame, Color color, 
            double x, double y, double xSpeed, double ySpeed,
            int width, int height) {
        super(killerGame, color, x, y, xSpeed, ySpeed, width, height);
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval((int)x, (int)y, width, height);
    }

    public Color getColor() {
        return color;
    }

    public double getXSpeed() {
        return xSpeed;
    }
    
    public double getYSpeed() {
        return ySpeed;
    }
    
    
    
    
}
