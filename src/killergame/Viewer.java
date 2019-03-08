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
    private String leftConnectionState;
    private Color leftConnectionColor;
    private String rightConnectionState;
    private Color rightConnectionColor;
    private String rightConnectionIp;
    private String leftConnectionIp;

    public Viewer(KillerGame killerGame) {
        super();
        setSize(KillerGame.SCREEN_WIDTH, KillerGame.SCREEN_HEIGHT);
        setBackground(Color.BLACK);
        this.killerGame = killerGame;
        leftConnectionState = "NOT CONNECTED";
        rightConnectionState = "NOT CONNECTED";
        leftConnectionColor = Color.RED;
        rightConnectionColor = Color.RED;
        leftConnectionIp = "";
        rightConnectionIp = "";
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
    
    public void setLeftConnection(String ip){
        leftConnectionColor = Color.GREEN;
        leftConnectionState = "CONNECTED";
        leftConnectionIp = ip;
    }
    
    public void setRightConnection(String ip){
        rightConnectionColor = Color.GREEN;
        rightConnectionState = "CONNECTED";
        rightConnectionIp = ip;;
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
        imageGraphics.setColor(leftConnectionColor);
        imageGraphics.drawString(leftConnectionState, 10, 50);
        imageGraphics.drawString(leftConnectionIp, 10, 75);
        imageGraphics.setColor(rightConnectionColor);
        imageGraphics.drawString(rightConnectionState, KillerGame.SCREEN_WIDTH - 150, 50);
        imageGraphics.drawString(rightConnectionIp, KillerGame.SCREEN_WIDTH - 150, 75);
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
