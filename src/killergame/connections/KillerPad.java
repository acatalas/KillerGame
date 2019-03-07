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
        
        addKillerShip(userInfo);
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
        killerShip = null;
        killerGame.removeKillerPad(this);
        
        System.out.println("KillerPad connection closed" + socket.getInetAddress().getHostAddress());
    }

    public void processClient(BufferedReader in, PrintWriter out) {
        boolean done = false;
        String line;
        while (!done) {
            try {
                line = in.readLine();
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
    
    private void addKillerShip(String userInfo){
        String name = userInfo.substring(0, userInfo.indexOf("&"));
        String color = "#" + userInfo.substring(userInfo.indexOf("&") + 1);
        
        killerShip = new KillerShip(killerGame, Color.decode(color), 100, 100, 
                KillerShip.IDLE_SPEED, KillerShip.IDLE_SPEED, ip, name);
        killerGame.addVisibleObject(killerShip);
        new Thread(killerShip).start();
    }
    
    public void request(String msg) {
        if(killerGame.getKillerShip(ip) != null){
            killerGame.getKillerShip(ip).doKillerAction(new KillerAction(msg));
        } else if(killerGame.getKillerShip(ip) == null){
            killerGame.getNextKiller().getOuWriter().println("RKS:" + ip + ":" + killerGame.getServerIp() + ":" + killerGame.getPort() + ":" + msg); 
        }
    }
    
    //RKS:shipIp:serverIp:serverPort:movement
    public static void readKillerAction(KillerGame killerGame, PrintWriter out, String msg){
        if(msg.startsWith("RKS") && 
                (killerGame.getKillerShip(msg.split(":")[1]) != null) && 
                ((((msg.split(":")[2].equals(killerGame.getServerIp())) &
                (!(msg.split(":")[3].equals(killerGame.getPort()))))) |
                (!(msg.split(":")[2].equals(killerGame.getServerIp())) &
                ((msg.split(":")[3].equals(killerGame.getPort())))))){
            
            killerGame.getKillerShip(msg.split(":")[1]).doKillerAction(new KillerAction(msg.split(":")[4]));
        
        } else if(msg.startsWith("RKS") && 
                (killerGame.getKillerShip(msg.split(":")[1]) == null) && 
                (msg.split(":")[2].equals(killerGame.getServerIp()))&&
                (msg.split(":")[3].equals(killerGame.getPort()))){ 
            
        }else if(msg.startsWith("RKS") && killerGame.getKillerShip(msg.split(":")[1]) == null){
           out.println(msg); 
        }
    }
    
    public static void removeShip(KillerGame killerGame, String ip) {
         //Check if I have ship
        KillerShip killerShipToRemove = killerGame.getKillerShip(ip);
        //Remove ship from game
        if(killerShipToRemove != null){
            killerShipToRemove.die();
            System.out.println("KillerShip deleted");
        }
    }

}
