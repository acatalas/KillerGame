/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ale
 */
public abstract class VisibleObject implements Collisionable, Renderizable{
    protected double x, y;
    protected int width, height;
    protected Color color;
    protected Rectangle rectangle;
    protected KillerGame killerGame;
    
    public VisibleObject(KillerGame killerGame,
            Color color, double x, double y, int width, int height){
        this.killerGame = killerGame;
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
        this.height = height;
        rectangle = new Rectangle((int)x, (int)y, width, height);  
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public Rectangle getRectangle(){
        return rectangle;
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval((int)x, (int)y, width, height);
    }
    
    @Override
    public boolean intersect(VisibleObject object){
        return rectangle.intersects(object.getRectangle());
    }
}
