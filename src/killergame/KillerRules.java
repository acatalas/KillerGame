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
public class KillerRules {
    public static void testCollision(VisibleObject object1, VisibleObject object2){
        /*if(object1.intersect(object2)){
            if(object1 instanceof KillerShip && object2 instanceof KillerShot){
                object1.die();
                object2.die();
                System.out.println("INTERSECTED");
            }
        }*/
    }
    
    public static boolean testShotCollision(KillerShot shot, KillerShip ship){
        if(ship.intersect(shot) && !shot.getIp().equals(ship.getIp())){
                    ship.die();
                    shot.die();
                    return true;
        }
        return false;
    }
}
