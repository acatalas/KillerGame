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
public class Controlled extends Alive {

    private boolean alive = true;

    public Controlled(KillerGame killerGame, Color color,
            double x, double y, double xSpeed, double ySpeed,
            int width, int height) {
        super(killerGame, color, x, y, xSpeed, ySpeed, width, height);
    }

    @Override
    public void run() {
        while (alive) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
            move();
        }
    }

    @Override
    public void move() {
        x = (int)(x + xSpeed);
        y = (int)(y + ySpeed);

        //Checks collision with right or left of screen
        if (x > RIGHT_MARGIN | x < LEFT_MARGIN) {
            //Move to next screen
        }

        rectangle.setLocation((int)x, (int)y);
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    @Override
    public void bounce() {

    }

    @Override
    public void die() {
        alive = false;
    }

    

}
