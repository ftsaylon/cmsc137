// import proto.TcpPacketProtos.TcpPacket.*;
// import proto.PlayerProtos.*;
package pacman.game;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class PacmanClient extends JPanel implements Runnable, KeyListener, Constants{
	private Pacman pacman;
	private Board board;
	private boolean gameOver;
	static final int GAME_OVER = 1;
	static final int NOT_OVER = 0;
	private ImageList IMAGELIST;
	private boolean is_connected;
	private String move;

	// final ImageIcon wall = new ImageIcon(getClass().getResource("/images/bluewall.jpg"));
	// final ImageIcon dot = new ImageIcon(getClass().getResource("/images/coin.png"));
	// final ImageIcon empty = new ImageIcon(getClass().getResource("/images/empty.jpg"));
	// final ImageIcon ghost = new ImageIcon(getClass().getResource("/images/pacman-down.png"));
	// final ImageIcon pacmanimg = new ImageIcon(getClass().getResource("/images/pacman-right.png")); 

	private String server_ip;
	private String player_name;
	private boolean is_pacman = true;
	private boolean is_ghost = false;

	public PacmanClient(String server_ip, String player_name, Board board){
		this.server_ip = server_ip;
		this.player_name = player_name;
		this.board = board;
		this.IMAGELIST = new ImageList();
		this.setFocusable(true);
		this.gameOver = false;
		this.is_connected = false;
		this.move = "";
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
			pacman.moveUp();
			move = MOVE_UP;
		}else if(ke.getKeyCode()==KeyEvent.VK_DOWN){
			pacman.moveDown();
			move = MOVE_DOWN;
		}else if(ke.getKeyCode()==KeyEvent.VK_LEFT){
			pacman.moveLeft();
			move = MOVE_LEFT;
		}else if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
			pacman.moveRight();
			move = MOVE_RIGHT;
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
		// pacmanGame game = new pacmanGame(board);
		// Pacman pacman = new Pacman(board.getPacmanXPos(), board.getPacmanYPos(), game);
		// game.setPacman(pacman);
		
		try{
			PacmanClient client = new PacmanClient(serverName, "FPJ", board);
			Pacman pacman = new Pacman(board.getPacmanXPos(), board.getPacmanYPos(), client);
			client.setPacman(pacman);
			pacmanFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			pacmanFrame.setPreferredSize(new Dimension(775, 700));
			pacmanFrame.setResizable(false);
			pacmanFrame.add(client);
			pacmanFrame.pack();
			pacmanFrame.setVisible(true);	
		// }catch(IOException e){
  //           e.printStackTrace();
  //           System.out.println("Cannot find (or disconnected from) Server");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
        }
        
	
	}
}