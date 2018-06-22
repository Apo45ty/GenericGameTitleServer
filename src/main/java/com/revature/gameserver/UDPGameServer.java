package com.revature.gameserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UDPGameServer {

	public static DatagramSocket socket;
	public static boolean running;
	public static final Map<Endpoint,UDPSocketThread> stMap = new HashMap<Endpoint,UDPSocketThread>();	

	public static void main(String[] args) throws IOException {
		socket = new DatagramSocket(9090);
		running = true;
		while (running) {
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			System.out.println("Connection Received "+address.toString()+" : "+port+" !");
			if(!stMap.containsKey(address)) 
			{// Its a new IP Address
				Endpoint ep = new Endpoint(address, port);
				UDPSocketThread st = new UDPSocketThread(ep);
				st.packetList.add(packet);
				stMap.put(ep, st);
				new Thread(st).start();;
				
			} else
			{// Address already in memory
				stMap.get(new Endpoint(address, port)).packetList.add(packet);
			}
			
//			packet = new DatagramPacket(buf, buf.length, address, port);
//			socket.send(packet);
		}
		socket.close();
	}
}
