package killergame.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import killergame.KillerGame;

/**
 
 */
public class KillerServer implements Runnable {
    private KillerGame killerGame;
    
    //Connection objects
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private int port;
    
    private BufferedReader in;
    private PrintWriter out;

    public KillerServer(KillerGame killerGame, int port) {
        this.killerGame = killerGame;
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("ConnectionHandler deployed: " + clientSocket.getInetAddress().getHostAddress()+
                        ":" + clientSocket.getPort());
                new Thread(new ConnectionHandler(killerGame, clientSocket)).start();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
