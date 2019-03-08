/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.Color;
import killergame.KillerGame;
import killergame.KillerShip;
import killergame.KillerShot;

/**
 *
 * @author Ale
 */
public class VisualHandler implements Runnable{
    private KillerGame killerGame;
    private KillerClient killerClient;
    private Socket socket;
    private int port;
    private String ip;
    
    private PrintWriter out;
    private BufferedReader in;
    
    public VisualHandler(KillerGame killerGame){
        this.killerGame = killerGame;
        killerClient = new KillerClient(killerGame, this);
    }
    
    public synchronized void setSocket(Socket socket){
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e){
            System.err.println(e);
        }
        if(killerGame.getNextKiller().equals(this)){
            killerGame.setRightConnection(socket.getInetAddress().getHostAddress());
        }else {
            killerGame.setLeftConnection(socket.getInetAddress().getHostAddress());
        }
    }
    
    public int getPort(){
        return port;
    }
    
    public void setPort(int port){
        this.port = port;
    }
    
    public String getIp(){
        return ip;
    }
    
    public void setIp(String ip){
        this.ip = ip;
    }
    
    public Socket getSocket(){
        return socket;
    }
    
    public PrintWriter getOuWriter(){
        return out;
    }
    
    
    @Override
    public void run() {
        new Thread(killerClient).start();
        while(true){
            if(socket != null){
                processMessage(in, out);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                
            }
        }
    }
    
    public void processMessage(BufferedReader in, PrintWriter out) {
        boolean done = false;
        String line;
        while (!done) {
            try {
                
                line = in.readLine();
                System.out.println(line);
                if (line != null) {
                    request(line);
                }

            } catch (IOException ex) {
                done = true;

            }
        }
    }
    
    public void request(String line) {
        
        //String ip, Color color, String name, double x, double y, doubleXSpeed, doubleYSpeed
        if(line.startsWith("KS")){
            String[] shipData = line.split(":");
            String ip = shipData[1];    //ip
            Color color = Color.decode("#" + shipData[2]); //color
            String name = shipData[3];  //name
            double x = Double.valueOf(shipData[4]); //X
            double y = Double.valueOf(shipData[5]); //y
            double xSpeed = Double.valueOf(shipData[6]);
            double ySpeed = Double.valueOf(shipData[7]);

            KillerShip killerShip = new KillerShip(killerGame, color, x, y, xSpeed, ySpeed, ip, name);
            killerGame.addVisibleObject(killerShip);
            new Thread(killerShip).start();
        
        //RKS:shipIp:serverIp:serverPort:movement
        } else if(line.startsWith("RKS")){
            KillerPad.readKillerAction(killerGame, killerGame.getNextKiller().getOuWriter(), line);
            
            
        } else if(line.startsWith("KB")){
            String[] shotData = line.split(":");
            double x = Double.valueOf(shotData[1]);
            double y = Double.valueOf(shotData[2]);
            double xSpeed = Double.valueOf(shotData[3]);
            double ySpeed = Double.valueOf(shotData[4]);
            Color color = Color.decode("#" + shotData[5]);
            
            KillerShot shot = new KillerShot(killerGame, color, x, y, xSpeed, ySpeed, 10, 10, "");
            killerGame.addVisibleObject(shot);
            new Thread(shot).start();
        
        } else if(line.startsWith("RDS")){
            KillerPad killerPad = killerGame.foundShipInPad(line.split(":")[1]);
            if(killerPad != null){
                killerPad.killShip();
            } else {
                KillerPad.sendKillShip(killerGame.getNextKiller().getOuWriter(), line.split(":")[1]);
            }
        }
    }
    
    //"KS:" + ip + ":" + color.getRGB() + ":" + name;
    public void sendShip(KillerShip ship, double x, double y){
        System.out.println("KS:" + ship.getIp());
        out.println("KS:" + ship.getIp() + ":" + rgbToHex(ship.getColor()) + ":" 
                + ship.getName() + ":" +  x + ":" + y + ":" + ship.getXSpeed() 
                + ":" + ship.getYSpeed());
    }
    
    //"KB:" + x + y + xSpeed + ySpeed + color
    public void sendShot(KillerShot shot, double x, double y){
        out.println("KB:" + x + ":" + y + ":" + shot.getXSpeed() 
                + ":" + shot.getYSpeed() + ":" + rgbToHex(shot.getColor()));
    }
    
    private String rgbToHex(Color color){
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            hex = "0" + hex;
        }           
        return hex;
    }
}
