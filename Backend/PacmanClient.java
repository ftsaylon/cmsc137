package pacman.game;

import packet.PlayerProtos.Player;
import packet.CharacterProtos.Character;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

import java.net.*;

public class PacmanClient extends JPanel implements Runnable, KeyListener, Constants{
	private Pacman pacman;
	private Board board;
	private boolean gameOver;
	static final int GAME_OVER = 1;
	static final int NOT_OVER = 0;
	private ImageList IMAGELIST;
	private boolean is_connected;
	private String move;
	Map map;
	
	DatagramSocket clientSocket; 
	UDPPacket udp_packet = new UDPPacket();
	Player playerPacket;
	Character characterPacket;

	Thread t = new Thread(this);

	// final ImageIcon wall = new ImageIcon(getClass().getResource("/images/bluewall.jpg"));
	// final ImageIcon dot = new ImageIcon(getClass().getResource("/images/coin.png"));
	// final ImageIcon empty = new ImageIcon(getClass().getResource("/images/empty.jpg"));
	// final ImageIcon ghost = new ImageIcon(getClass().getResource("/images/pacman-down.png"));
	// final ImageIcon pacmanimg = new ImageIcon(getClass().getResource("/images/pacman-right.png")); 

	private String server_ip;
	private String player_name;
	private boolean is_pacman = true;
	private boolean is_ghost = false;

	public PacmanClient(String server_ip, String player_name){
		this.server_ip = server_ip;
		this.player_name = player_name;
		this.map = new Map(3);
		this.board = new Board(map);
		this.IMAGELIST = new ImageList();
		this.gameOver = false;
		this.is_connected = false;
		this.move = "";
		
		this.pacman = new Pacman(this.board.getPacmanXPos(), this.board.getPacmanYPos(), this);
		this.characterPacket = udp_packet.createCharacter(player_name, "1", Integer.toString(pacman.getNumberOfLives()), Integer.toString(pacman.getSize()), Integer.toString(pacman.getXPos()), Integer.toString(pacman.getYPos()));
		this.playerPacket = udp_packet.createPlayer(player_name, this.characterPacket);

		this.setFocusable(true);
		this.addKeyListener(this);
		setOpaque(true);
		setBackground(Color.BLACK);
		setLayout(new GridLayout(31, 28));
		setBoard(board.getBoardLayout());
		revalidate();
		repaint();

		t.start();
	}

	@Override
    public void paint(Graphics g) {
        super.paint(g);
       
	}
	
    public void setPacman(Pacman pm){
    	this.pacman = pm;
	}
	
	public void printBoard(){
		String[][] bl = this.board.getBoardLayout();
		for(int i = 0; i < 31; i++){
			for(int j = 0; j < 28; j++){
				System.out.print(bl[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public void setBoard(String[][] boardLayout){
		for(int i = 0; i < 31; i++){
			for(int j = 0; j < 28; j++){
				switch(boardLayout[i][j]){
					case "w":
						this.add(new JLabel(IMAGELIST.getImage("wall")));
						break;
					case "o":
						this.add(new JLabel(IMAGELIST.getImage("smalldot")));
						break;
					case "O":
						this.add(new JLabel(IMAGELIST.getImage("bigdot")));
						break;	
					case "e":
						this.add(new JLabel(IMAGELIST.getImage("empty")));
						break;
					case "x":
						this.add(new JLabel(IMAGELIST.getImage("empty")));
						break;
					case "G":
						this.add(new JLabel(IMAGELIST.getImage("ghost")));
						break;
					case "P":
						if(move=="")this.add(new JLabel(IMAGELIST.getImage("pacRIGHT")));
						else this.add(new JLabel(IMAGELIST.getImage("pac"+move)));
						break;
					}
			}
		}
	}

	public void updatePanel(){ //updates the puzzlePanel whenever the player is moved
		this.removeAll();
		this.setBoard(this.board.getBoardLayout());
		this.revalidate();
		this.repaint();
	}

	// public void checkGameOver(){ //checks if the puzzle is solved and if it is, the panel will not be focused
	// 	if(state.getGameStatus() == GAME_OVER){
	// 		this.gameOver = true;
	// 		this.setFocusable(false);
	// 	}
	// }

	public void pacmanRespawn(){
		
	}

	public void checkGameOver(){ //checks if the puzzle is solved and if it is, the panel will not be focused
		
		// if(state.getGameStatus() == GAME_OVER){
		// 	this.gameOver = true;
		// 	this.setFocusable(false);
		// }
	}

	public Board getGameBoard(){
		return this.board;
	}

	public void keyPressed(KeyEvent ke){ //if UP, DOWN, LEFT, or RIGHT key is pressed whedin the puzzlePanel is focused
		if(ke.getKeyCode()==KeyEvent.VK_UP){
			this.pacman.moveUp();
			move = MOVE_UP;
		}else if(ke.getKeyCode()==KeyEvent.VK_DOWN){
			this.pacman.moveDown();
			move = MOVE_DOWN;
		}else if(ke.getKeyCode()==KeyEvent.VK_LEFT){
			this.pacman.moveLeft();
			move = MOVE_LEFT;
		}else if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
			this.pacman.moveRight();
			move = MOVE_RIGHT;
		}

		// Update Packets to be sent to server whenever there's movement
		this.characterPacket = udp_packet.createCharacter(player_name, "1", Integer.toString(this.pacman.getNumberOfLives()), Integer.toString(this.pacman.getSize()), Integer.toString(this.pacman.getXPos()), Integer.toString(this.pacman.getYPos()));
		this.playerPacket = udp_packet.createPlayer(player_name, this.characterPacket);
		
		this.updatePanel();
		checkGameOver();
	}
	
	public void keyTyped(KeyEvent ke){
	
	}
	
	public void keyReleased(KeyEvent ke){
		
	}

	public void run(){
		try {
			Player playerPacketOld = null;
			this.clientSocket = new DatagramSocket();
			
			while(true){
				udp_packet.setSocket(this.clientSocket);
				
				if(playerPacketOld == null){
					playerPacketOld = playerPacket;
					udp_packet.send(this.playerPacket.toByteArray());
				}else if(playerPacket.getCharacter().getXPos() != playerPacketOld.getCharacter().getXPos() || playerPacket.getCharacter().getYPos() != playerPacketOld.getCharacter().getYPos()){
					udp_packet.send(this.playerPacket.toByteArray());	
				}
				
				playerPacketOld = playerPacket;
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} 
		
	}

	public static void main(String[] args){
		JFrame pacmanFrame = new JFrame("pacman Game");
		
		try{
			String serverName = args[0];
			String playerName = args[1];
			PacmanClient client = new PacmanClient(serverName, playerName);
			
			pacmanFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pacmanFrame.setPreferredSize(new Dimension(775, 700));
			pacmanFrame.setResizable(false);
			pacmanFrame.add(client);
			pacmanFrame.pack();
			pacmanFrame.setVisible(true);	
		// }
		// catch(IOException e){
        //     e.printStackTrace();
        //     System.out.println("Cannot find (or disconnected from) Server");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java PacmanClient <server ip> '<name>'");
        }
        
	
	}
}