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
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e){
            System.err.println(e);
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
                System.out.println("Visual Handler message:" + line);
                if (line != null) {
                    request(line);
                }

            } catch (IOException ex) {
                done = true;

            }
        }
    }
    
    public void request(String line) {
        System.out.println(line);

        if(line.startsWith("KS")){
            String[] shipData = line.split(":");
            String ip = shipData[1];
            double x = Double.valueOf(shipData[2]);
            double y = Double.valueOf(shipData[3]);
            double xSpeed = Double.valueOf(shipData[4]);
            double ySpeed = Double.valueOf(shipData[5]);
            Color color = Color.decode("#" + shipData[6]);
            String name = shipData[7];
            
            KillerShip killerShip = new KillerShip(killerGame, color, x, y, ip, name);
            killerGame.addVisibleObject(killerShip);
            new Thread(killerShip).start();
            
        }
    }
    
    public void sendShip(KillerShip ship, double x, double y){
        System.out.println("Ship to send: " + ship.toString());
        out.print(ship.toString() + ":" + x + ":" + y);
    }
}
