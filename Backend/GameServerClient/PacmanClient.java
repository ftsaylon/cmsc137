package pacman.game;

import proto.TcpPacketProtos.TcpPacket.*;
import proto.PlayerProtos.*;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class PacmanClient implements Runnable, KeyListener{
	private Pacman pacman;
	private Board board;
	private boolean gameOver;
	static final int GAME_OVER = 1;
	static final int NOT_OVER = 0;
	private ImageList IMAGELIST;

	// final ImageIcon wall = new ImageIcon(getClass().getResource("/images/bluewall.jpg"));
	// final ImageIcon dot = new ImageIcon(getClass().getResource("/images/coin.png"));
	// final ImageIcon empty = new ImageIcon(getClass().getResource("/images/empty.jpg"));
	// final ImageIcon ghost = new ImageIcon(getClass().getResource("/images/pacman-down.png"));
	// final ImageIcon pacmanimg = new ImageIcon(getClass().getResource("/images/pacman-right.png")); 

	private String server_ip;
	private String player_name;
	private int is_pacman = true;
	private int is_ghost = false;

	public PacmanClient(String server_ip, String player_name){
		this.server_ip = server_ip;
		this.player_name = player_name;
		this.setFocusable(true);
		this.gameOver = false;
		this.is_connected = false;
		board = state.getBoard();
		this.addKeyListener(this);
		setOpaque(true);
		setBackground(Color.BLACK);
		setLayout(new GridLayout(31, 28));
		setBoard(board.getBoardLayout());
		revalidate();
		repaint();
	}
	@Override
    public void paint(Graphics g) {
        super.paint(g);
       
    }
    public void setPacman(Pacman pm){
    	this.pacman = pm;
    }
	public void printBoard(){
		Board board2 = state.getBoard();
		String[][] bl = board2.getBoardLayout();
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
		Board board2 = state.getBoard();
		this.removeAll();
		this.setBoard(board2.getBoardLayout());
		this.revalidate();
		this.repaint();
	}
	// public void checkGameOver(){ //checks if the puzzle is solved and if it is, the panel will not be focused
	// 	if(state.getGameStatus() == GAME_OVER){
	// 		this.gameOver = true;
	// 		this.setFocusable(false);
	// 	}
	// }
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
			pacman.moveUp(state);
		}else if(ke.getKeyCode()==KeyEvent.VK_DOWN){
			pacman.moveDown(state);
		}else if(ke.getKeyCode()==KeyEvent.VK_LEFT){
			pacman.moveLeft(state);
		}else if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
			pacman.moveRight(state);
		}this.updatePanel();
		this.printBoard();
		checkGameOver();


	}public void keyTyped(KeyEvent ke){
	
		
	}public void keyReleased(KeyEvent ke){
		
	}

	public void run(){
		while(true){
			try { 
				Thread.sleep(0);
			}catch(Exception e){

			}


		}
	}

	public static void main(String[] args){
		String serverName = "202.92.144.45";
        int port = 80;   
		JFrame pacmanFrame = new JFrame("pacman Game");
		Map map = new Map(3);
		Board board = new Board(map);
		Pacman pacman = new Pacman(board.getPacmanXPos(), board.getPacmanYPos());
		PacmanGame game = new PacmanGame(board);
		game.setPacman(pacman);
		
		try{
			PacmanClient newGgame = new PacmanClient(serverName, "FPJ");	
		}catch(IOException e){
            e.printStackTrace();
            System.out.println("Cannot find (or disconnected from) Server");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
        }
        pacmanFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pacmanFrame.setPreferredSize(new Dimension(840, 910));
		pacmanFrame.setResizable(false);
		pacmanFrame.add(new PacmanGame(initialState));
		pacmanFrame.pack();
		pacmanFrame.setVisible(true);
	
	}
}