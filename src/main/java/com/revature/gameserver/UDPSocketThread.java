package com.revature.gameserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class UDPSocketThread implements Runnable{
	public Queue<DatagramPacket> packetList = new LinkedList<DatagramPacket>();
	public Endpoint myendpoint;
	
	public UDPSocketThread(Endpoint myendpoint) {
		super();
		this.myendpoint = myendpoint;
	}

	public boolean done = false;

	public void run() {
		while(!done) {
			if(packetList.isEmpty())
				try {
					Thread.sleep(100);
					continue;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			DatagramPacket packet = packetList.remove();
			byte[] buf = packet.getData();
			String received = new String(packet.getData(), 0, packet.getLength());
			System.out.println(received);
			for(Endpoint ep:UDPGameServer.stMap.keySet()) {
				if(ep.equals(myendpoint))
					continue;
				packet = new DatagramPacket(buf, buf.length, ep.address, ep.port);
				
				try {
					UDPGameServer.socket.send(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

}
