package pacman.game;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// import packet.PlayerProtos.Player;
import packet.UdpPacketProtos.UdpPacket.*;

public class PacmanServer implements Runnable, Constants{
	UDPPacket udp_packet;
	
	// Placeholder for the data received from the player 	 
	// Player playerPacket;

	// The number of currently connected player	 
	int playerCount=0;

	// The socket	 
    DatagramSocket serverSocket = null;

    // The current game state     
	GameState game;

	// The current game stage	 
	int gameStage=WAITING_FOR_PLAYERS;
	
	// Number of players	 
	int numPlayers;
	
	// The main game thread
	Thread t = new Thread(this);
	
	Player player = null;

	public PacmanServer(int numPlayers){
		this.numPlayers = numPlayers;
		this.udp_packet = new UDPPacket();

		try{
            serverSocket = new DatagramSocket(PORT);
			// serverSocket.setSoTimeout(10000);

			System.out.println("Now listening on port: " + PORT);
		}catch (IOException e) {
            System.err.println("Could not listen on port: " + PORT);
            System.exit(-1);
		}catch(Exception e){}
		
		//Create the game state
		game = udp_packet.createGameState(player);
		
		System.out.println("Game created...");
		
		//Start the game thread
		t.start();
	}


	public void run(){
		Player playerPacketOld = null;
		Player playerPacket = null;
		byte[] buf = null;
		try{
			// Get the data from players
			this.udp_packet.setSocket(this.serverSocket);
		}catch(Exception ioe){
			ioe.printStackTrace();
		}

		while(true){
			
			buf = this.udp_packet.receive();
			try{
				// Get the data from players
				playerPacket = Player.parseFrom(buf);
			}catch(Exception ioe){
				ioe.printStackTrace();
			}
	
			switch(gameStage){
				case WAITING_FOR_PLAYERS:

					if(playerPacketOld == null){
						playerPacketOld = playerPacket;

						this.game = udp_packet.createGameState(playerPacket);
						System.out.println("Number of players: " + this.game.getPlayerListCount());
						System.out.println(playerPacket.getName() + " joined the game");	
						this.playerCount++;

					}else if(playerPacket.getCharacter().getName() != playerPacketOld.getCharacter().getName()){
						this.game = udp_packet.addPlayerToGame(this.game, playerPacket);
						System.out.println("Number of players: " + this.game.getPlayerListCount());
						System.out.println(playerPacket.getName() + " joined the game");
						this.playerCount++;
					}else continue;

					playerPacketOld = playerPacket;

					if(numPlayers==playerCount){
						gameStage=GAME_START;
					}
					
					break;
					
				case GAME_START:
					System.out.println("Game is starting...");
					gameStage=IN_PROGRESS;
					
					break;

				case IN_PROGRESS:
					System.out.println("Game is in progress");
					Integer index = 0;
					this.game = this.game.toBuilder().setPlayerList(playerPacket.getId()-1, playerPacket).build();
					
					System.out.println(this.game.getPlayerListList());
					Iterator iter = this.game.getPlayerListList().iterator();
						while(iter.hasNext()){
							Player player = (Player) iter.next();
							this.udp_packet.sendToClient(player, this.game.toByteArray());
						}
					break;
			}

			
		}
	}

	public static void main(String[] args){
		if (args.length != 1){
			System.out.println("Usage: java pacman.game.PacmanServer <number of players>");
			System.exit(1);
		}

		new PacmanServer(Integer.parseInt(args[0]));

	}
}