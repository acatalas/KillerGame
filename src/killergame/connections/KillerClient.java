/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame.connections;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import killergame.KillerGame;

/**
 *
 * @author Ale
 */
public class KillerClient implements Runnable {

    private KillerGame killerGame;
    private VisualHandler visualHandler;

    public KillerClient(KillerGame killerGame, VisualHandler visualHandler) {
        this.killerGame = killerGame;
        this.visualHandler = visualHandler;
    }

    @Override
    public void run() {
        while (true) {
            if (this.visualHandler.getSocket() != null) {
                System.out.println("Control time: " + (System.currentTimeMillis() - visualHandler.getCurrentTime()));
                if (System.currentTimeMillis() - visualHandler.getCurrentTime() > 1000) {
                    visualHandler.killSocket();
                }
            }
            if (this.visualHandler.getIp() != null && this.visualHandler.getSocket() == null) {
                
                disconnectHandler();
                
                try {
                    /*System.out.println("KillerClient connecting to " + visualHandler.getIp()
                            + ":" + visualHandler.getPort());*/
                    Socket socket = new Socket(visualHandler.getIp(), visualHandler.getPort());

                    answer(socket);
                    visualHandler.setSocket(socket);
                    //System.out.println("VisualHandler given : " + socket.getInetAddress().getHostAddress() + socket.getLocalPort());
                    
                    connectHandler(socket);
                    
                    visualHandler.sendEcho();
                } catch (IOException ex) {
                    System.err.println(ex);
                } catch (Exception ex) {
                    System.err.println(ex);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {}
        }
    }

    private void answer(Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        if (killerGame.getPreviousKiller() == visualHandler) {
            out.println("PK:" + killerGame.getPort());
        } else {
            out.println("NK:" + killerGame.getPort());
        }
    }

    private void disconnectHandler() {
        if (killerGame.getNextKiller().equals(visualHandler)) {
            killerGame.setRightConnection("DISCONNECTED", "");
        } else {
            killerGame.setLeftConnection("DISCONNECTED", "");
        }
    }

    private void connectHandler(Socket socket) {
        if (killerGame.getNextKiller().equals(visualHandler)) {
            killerGame.setRightConnection("CONNECTED", socket.getInetAddress().getHostAddress());
        } else {
            killerGame.setLeftConnection("CONNECTED", socket.getInetAddress().getHostAddress());
        }
    }
}
