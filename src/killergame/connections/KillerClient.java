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
            if (this.visualHandler.getIp() != null && this.visualHandler.getSocket() == null) {
                try {
                    // Solicitar conexion al servidor
                    // Pasar a otro metodo
                    System.out.println("KillerClient connecting to " + visualHandler.getIp()
                    + ":" + visualHandler.getPort());
                    Socket socket = new Socket(visualHandler.getIp(), visualHandler.getPort());
                    answer(socket);
                    visualHandler.setSocket(socket);
                    System.out.println("Connection to " + visualHandler.getIp() + 
                            ":" + visualHandler.getPort() + " SUCCESSFUL");

                } catch (IOException ex) {
                    System.err.println(ex);
                } catch (Exception ex) {
                    System.err.println(ex);
                }

            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }

        }
    }
  
    private void answer(Socket socket) throws IOException{
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        if(killerGame.getPreviousKiller() == visualHandler){
            out.println("PK:" + killerGame.getPort());
        } else {
            out.println("NK:" + killerGame.getPort());
        }
    }
}
