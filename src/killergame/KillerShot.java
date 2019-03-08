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
    
    private boolean dead = false;
    private String ip;
    
    public KillerShot(KillerGame killerGame, Color color, 
            double x, double y, double xSpeed, double ySpeed,
            int width, int height, String ip) {
        super(killerGame, color, x, y, xSpeed, ySpeed, width, height);
        this.ip = ip;
    }
    
    @Override
    public void run() {
        while (!dead) {
            killerGame.testShotCollision(this);
            move();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }
        killerGame.removeVisibleObject(this);
    }
    
    @Override
    public synchronized void move() {
        x = (int)(x + xSpeed);
        y = (int)(y + ySpeed);
        rectangle.setLocation((int)x, (int)y);
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
    
    public String getIp(){
        return ip;
    }
    
    @Override
    public void die() {
        dead = true;
    }
}
