/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

/**
 *
 * @author Ale
 */
public class Viewer extends Canvas implements Runnable {

    private List<VisibleObject> objects;
    private Image image = null;
    private Graphics imageGraphics;
    private KillerGame killerGame;

    public Viewer(KillerGame killerGame) {
        super();
        setSize(KillerGame.SCREEN_WIDTH, KillerGame.SCREEN_HEIGHT);
        setBackground(Color.BLACK);
        this.killerGame = killerGame;
    }

    @Override
    public void run() {
        while (true) {
            
            gameRender();
            paint(this.getGraphics());
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void gameRender() {
        if (image == null) {
            image = createImage(KillerGame.SCREEN_WIDTH, KillerGame.SCREEN_HEIGHT);
            if (image == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                imageGraphics = image.getGraphics();
            }
        }
        //clear the background
        imageGraphics.setColor(Color.BLACK);
        imageGraphics.fillRect(0, 0, KillerGame.SCREEN_WIDTH, KillerGame.SCREEN_HEIGHT);
        for (int i = 0; i < killerGame.getVisibleObjects().size(); i++){
            killerGame.getVisibleObjects().get(i).paint(imageGraphics);
        }
    }

    
    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
