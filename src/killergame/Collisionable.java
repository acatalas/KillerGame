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
public interface Collisionable {
    public boolean intersect(VisibleObject object);
    public void bounce();
    public void die();
}
