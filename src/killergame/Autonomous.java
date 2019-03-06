/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ale
 */
public class Autonomous extends Alive {
    private boolean dead = false;
    
    public Autonomous(KillerGame killerGame, Color color,
            double x, double y, double xSpeed, double ySpeed,
            int width, int height) {
        super(killerGame, color, x, y, xSpeed, ySpeed, width, height);
    }
    
    @Override
    public void run() {
        while (!dead) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
            killerGame.testCollision(this);
            move();
        }
        killerGame.removeVisibleObject(this);
    }

    @Override
    public synchronized void move() {
        x = (int)(x + xSpeed);
        y = (int)(y + ySpeed);
        
        //Checks collision with right or left of screen
        if (x > RIGHT_MARGIN | x < LEFT_MARGIN ){
            dead = true;
        }
        
        rectangle.setLocation((int)x, (int)y);
    }
    
    //inverts xSpeed value
    private void invertXSpeed(){
        if(xSpeed > 0){
            xSpeed = xSpeed - (xSpeed*2);
        } else if (xSpeed < 0){
            xSpeed = xSpeed + (2*(-1*xSpeed));
        }
    }
    
    //inverts ySpeed value
    private void invertYSpeed(){
        if(ySpeed > 0){
            ySpeed = ySpeed - (ySpeed*2);
        } else if (ySpeed < 0){
            ySpeed = ySpeed + (2*(-1*ySpeed));
        }
    }
    
    @Override
    public void bounce() {
        invertYSpeed();
        invertXSpeed();
    }

    @Override
    public void die() {
        dead = true;
    }
}
