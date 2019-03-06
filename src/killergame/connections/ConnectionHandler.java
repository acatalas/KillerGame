/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import killergame.KillerGame;

/**
 * Protocol for recieving a ball from a client: [x coordinate] [y coordinate] [x
 * axis speed] [y axis speed]
 */
public class ConnectionHandler implements Runnable {

    private Socket socket;
    private KillerGame killerGame;
    private BufferedReader in;
    private String ip;

    public ConnectionHandler(KillerGame killerGame, Socket socket) {
        this.killerGame = killerGame;
        this.socket = socket;
        this.ip = this.socket.getInetAddress().getHostAddress();
    }

    @Override
    public void run() {
        String line = "";
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            line = in.readLine();
        } catch (IOException e) {
            System.err.println(e);
        }

        //If request from a new KillerPad (fromPnew:name&colorHex)
        if (line.startsWith("fromP")) {
            //Add killer pad
            KillerPad killerPad = new KillerPad(killerGame, socket,
                    line.substring(line.indexOf(":") + 1));
            killerGame.getKillerPads().add(killerPad);
            new Thread(killerPad).start();

        //Else if request from previous killer (PK:port)
        } else if (line.startsWith("PK")) {
            VisualHandler previousKiller = killerGame.getPreviousKiller();
            previousKiller.setSocket(socket);
            previousKiller.setPort(Integer.parseInt(line.substring(line.indexOf(":") + 1)));
        
        //Else if request from next killer  (NK:port)
        } else if (line.startsWith("NK")) {
            VisualHandler nextKiller = killerGame.getNextKiller();
            nextKiller.setSocket(socket);
            nextKiller.setPort(Integer.parseInt(line.substring(line.indexOf(":") + 1)));
        } 
    }
}
