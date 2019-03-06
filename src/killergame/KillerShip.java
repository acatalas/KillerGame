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
public class KillerShip extends Controlled {
    private boolean alive = true;
    private String ip;
    private String name;
    public static final int SHIP_HEIGHT = 30;
    public static final int SHIP_WIDTH = 30;
    public static final double SPEED = 2.0;
    public static final double IDLE_SPEED = 0.0;
    
    public KillerShip(KillerGame killerGame, Color color, 
            double x, double y, String ip, String name) {
        super(killerGame, color, x, y, IDLE_SPEED, IDLE_SPEED, SHIP_WIDTH, SHIP_HEIGHT);
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
        System.out.println("Dead");
        killerGame.removeVisibleObject(this);
    }
    
    @Override
    public void move(){
        x = (int)(x + xSpeed);
        y = (int)(y + ySpeed);
    }
    
    public void shoot(){
        KillerShot shot = new KillerShot(killerGame, color, 
                x, y , xSpeed, ySpeed, 10, 10);
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
            xSpeed = action.getXSpeed();
            ySpeed = action.getYSpeed();
        }
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawString(name, (int)x, (int)y - 10);
    }
    
    public String getIp(){
        return ip;
    }

    @Override
    public String toString() {
        return "KS:" + ip + ":" + xSpeed + ":" + ySpeed
                + ":" + color.getRGB() + ":" + name;
    }
    
    @Override
    public void die(){
        alive = false;
    }
    
    
    
}
