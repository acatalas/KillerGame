/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame.connections;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import killergame.KillerAction;
import killergame.KillerGame;
import killergame.KillerShip;

/**
 * Handler for pad requests
 */
public class KillerPad implements Runnable {

    private KillerGame killerGame;
    private Socket socket;
    private String ip;
    private String userInfo;
    private BufferedReader in;
    private PrintWriter out;

    private KillerShip killerShip = null;

    public KillerPad(KillerGame killerGame, Socket socket, String userInfo) {
        this.killerGame = killerGame;
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        this.userInfo = userInfo;
        
        addKillerPad(userInfo);
    }

    @Override
    public void run() {
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            processClient(in, out);
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        //When socket closed, delete killerShip
        killerGame.removeVisibleObject(killerShip);
        killerGame.removeKillerPad(this);
        System.out.println("KillerPad connection closed" + socket.getInetAddress().getHostAddress());
    }

    public void processClient(BufferedReader in, PrintWriter out) {
        boolean done = false;
        String line;
        while (!done) {
            try {
                line = in.readLine();
                System.out.println(line);
                if (line != null) {
                    if (line.equals("bye")) {
                        killerGame.removeKillerPad(this);
                        done = true;
                    } else {
                        request(line);
                    }
                } else {
                    done = true;
                }

            } catch (IOException ex) {
                System.err.println(ex);
            }
        }

    }
    
    private void addKillerPad(String userInfo){
        String name = userInfo.substring(0, userInfo.indexOf("&"));
        String color = "#" + userInfo.substring(userInfo.indexOf("&") + 1);
        killerShip = new KillerShip(killerGame, Color.decode(color), 100, 100, ip, name);
        killerGame.addVisibleObject(killerShip);
        new Thread(killerShip).start();
    }
    
    public void request(String msg) {
        killerShip.doKillerAction(new KillerAction(msg));
    }
    
     public static void removeShip(KillerGame killerGame, String ip) {
         //Check if I have ship
        KillerShip killerShipToRemove = killerGame.getKillerShip(ip);
        //Remove ship from game
        if(killerShipToRemove != null){
            killerShipToRemove.die();
            killerGame.removeVisibleObject(killerShipToRemove);
            System.out.println("KillerShip deleted");
        }
    }

}
