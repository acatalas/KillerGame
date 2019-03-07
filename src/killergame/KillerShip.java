/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

/**
 *
 * @author Ale
 */
public class KillerShip extends Controlled {
    private boolean alive = true;
    private String ip;
    private String name;
    private double prevXSpeed;
    private double prevYSpeed;
    public static final int SHIP_HEIGHT = 30;
    public static final int SHIP_WIDTH = 30;
    public static final double SPEED = 2.0;
    public static final double IDLE_SPEED = 0.0;
    
    public KillerShip(KillerGame killerGame, Color color, 
            double x, double y, double xSpeed, double ySpeed, String ip, String name) {
        super(killerGame, color, x, y, xSpeed, ySpeed, SHIP_WIDTH, SHIP_HEIGHT);
        this.ip = ip;
        this.name = name;
    }
    
    @Override
    public void run(){
        while(alive){
            move();
            killerGame.testCollision(this);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }
        killerGame.removeVisibleObject(this);
    }
    
    @Override
    public void move(){
        x = (int)(x + xSpeed);
        y = (int)(y + ySpeed);
    }
    
    public void shoot(){
        KillerShot shot = null;
        if(xSpeed == 0){
            shot = new KillerShot(killerGame, color, 
                x, y , prevXSpeed, prevYSpeed, 10, 10);
        } else {
            shot = new KillerShot(killerGame, color, 
                x, y , xSpeed, ySpeed, 10, 10);
        }
        
        killerGame.addVisibleObject(shot);
        new Thread(shot).start();
    }
    
    @Override
    public void bounce(){
        ySpeed = 0;
    }
    
    public void doKillerAction(KillerAction action){
        if(action.isShoot()){
            shoot();
        } else {
            if(action.getXSpeed() == 0){
                prevYSpeed = ySpeed;
                prevXSpeed = xSpeed;
            }
            xSpeed = action.getXSpeed();
            ySpeed = action.getYSpeed();
        }
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(color);
        g.drawString(name, (int)x, (int)y - 10);
    }
    
    public String getIp(){
        return ip;
    }
    
    public Color getColor() {
        return color;
    }
    
    public String getName(){
        return name;
    }
    
    public double getXSpeed(){
        return xSpeed;
    }
    
    public double getYSpeed(){
        return ySpeed;
    }
    
    @Override
    public void die(){
        alive = false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KillerShip other = (KillerShip) obj;
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
