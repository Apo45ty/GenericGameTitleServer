package com.revature.gameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * A TCP server that runs on port 9090.  When a client connects, it
 * sends the client the current date and time, then closes the
 * connection with that client.  Arguably just about the simplest
 * server you can write.
 */
public class TCPGameServer {
	public static List<TCPSocketThread> stList = new ArrayList<TCPSocketThread>();	
    /**
     * Runs the server.
     */
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        try {
            while (true) {
                Socket socket = listener.accept();
                System.out.println("Connection accepted");
                TCPSocketThread st = new TCPSocketThread(socket);
                stList.add(st);
                new Thread(st).start();
            }
        }
        finally {
            listener.close();
        }
    }
}