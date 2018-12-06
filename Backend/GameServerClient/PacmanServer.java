package pacman.game;

import packet.PlayerProtos.*;
import packet.TcpPacketProtos.TcpPacket.*;
import packet.TcpPacketProtos.TcpPacket;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;


public class PacmanServer implements Runnable{
	private int player_count;

	public PacmanServer(int player_count){
		this.player_count = player_count;
	}

	public void run(){
		while(true){

		}
	}

	public static void main(String[] args){
		
	}
}