package com.revature.gameserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPSocketThread implements Runnable {
	
	public TCPSocketThread(Socket socket) {
		super();
		this.socket = socket;
	}
	Socket socket;
	boolean done = false;
	private BufferedReader in;
	private PrintWriter out;
	public void run() {
        try {
    		in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			System.err.println("Socket Crash" + e.getMessage());
		}
        
		do {
			String input = null;
			try {
				input = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (input == null || input.equals(".")) {
                break;
            }
           System.out.println(input.toUpperCase());
		}while(!done);
		System.out.println("Connection Closed");
		TCPGameServer.stList.remove(this);
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
