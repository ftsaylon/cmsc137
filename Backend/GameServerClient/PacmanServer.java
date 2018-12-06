package pacman.game;
import proto.TcpPacketProtos.TcpPacket.*;
import proto.PlayerProtos.*;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.net.Socket;

public class PacmanServer implements Runnable{
	private int player_count;
	Thread t = new Thread(this);

	public PacmanServer(int player_count){
		this.player_count = player_count;

		t = new Thread(this);

		t.start();
	}

	public void run(){
		while(true){

		}
	}

	public static void main(String[] args){
		if (args.length != 1){
			System.out.println("Usage: java PacmanServer <number of players>");
			System.exit(1);
		}

		new PacmanServer(Integer.parseInt(args[0]));

	}
}