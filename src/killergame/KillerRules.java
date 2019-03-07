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
        if(object1.intersect(object2)){
            if (object1 instanceof KillerMeteor && object2 instanceof KillerMeteor){
                object1.bounce();
            }
            if(object1 instanceof KillerShot && object2 instanceof KillerShip){
                object1.die();
                object2.die();
            }
        }
    }
}
