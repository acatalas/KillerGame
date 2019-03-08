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
public abstract class Alive extends VisibleObject implements Runnable{
    
    public final int TOP_MARGIN;
    public final int BOTTOM_MARGIN;
    public final int RIGHT_MARGIN;
    public final int LEFT_MARGIN;
    protected double ySpeed;
    protected double xSpeed;

    public Alive(KillerGame killerGame,Color color,
            double x, double y, double xSpeed, double ySpeed,
            int width, int height) {
        super(killerGame, color, x, y, width, height);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        TOP_MARGIN = 0;
        BOTTOM_MARGIN = KillerGame.SCREEN_HEIGHT - height;
        LEFT_MARGIN = 0 - width;
        RIGHT_MARGIN = KillerGame.SCREEN_WIDTH + width;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    @Override
    public abstract void run();
    
    public abstract void move();
    
}
