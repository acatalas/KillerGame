/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame;

/**
 *
 * @author Ale
 */
public class KillerAction {

    private double xSpeed;
    private double ySpeed;
    private boolean shoot = false;

    public KillerAction(String action) {
        if (action.equals("shoot")) {
            shoot = true;
        } else {
            switch(action){
                case "up":
                    ySpeed = - KillerShip.SPEED;
                    break;
                case "upright":
                    ySpeed = - KillerShip.SPEED;
                    xSpeed = KillerShip.SPEED;
                    break;
                case "right":
                    xSpeed = KillerShip.SPEED;
                    break;
                case "downright":
                    xSpeed = KillerShip.SPEED;
                    ySpeed = KillerShip.SPEED;
                    break;
                case "down":
                    ySpeed = KillerShip.SPEED;
                    break;
                case "downleft":
                    ySpeed = KillerShip.SPEED;
                    xSpeed = - KillerShip.SPEED;
                    break;
                case "left":
                    xSpeed = - KillerShip.SPEED;
                    break;
                case "upleft":
                    xSpeed = - KillerShip.SPEED;
                    ySpeed = - KillerShip.SPEED;
                    break;
                case "idle":
                    xSpeed = KillerShip.IDLE_SPEED;
                    ySpeed = KillerShip.IDLE_SPEED;
                    break;
            }
        }
    }
    
    public double getYSpeed(){
        return ySpeed;
    }
    
    public double getXSpeed(){
        return xSpeed;
    }
    
    public boolean isShoot(){
        return shoot;
    }
}
