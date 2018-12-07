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
	private JLabel[][] boardUI;
	private boolean gameOver;
	private ImageList IMAGELIST;
	private boolean is_connected;
	private String move;
	private Map map;
	private HashMap<Ghost, String> ghosts;
	static final int NOT_OVER = 0;

	private static int numberOfPlayers = 0;
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
	private boolean is_pacman;
	private boolean is_blinky;
	private boolean is_speedy;
	private boolean is_inky;
	private boolean is_clyde;

	public PacmanClient(String server_ip, String player_name){
		this.server_ip = server_ip;
		this.player_name = player_name;
		this.map = new Map(3);
		this.board = new Board(map);
		this.IMAGELIST = new ImageList();
		this.gameOver = false;
		this.is_connected = false;
		this.move = "";
		this.numberOfPlayers ++;
		if(this.numberOfPlayers == 1)	{
			this.pacman = new Pacman(this.board.getPacmanXPos(), this.board.getPacmanYPos(), this);
			this.is_pacman = true;
			this.is_blinky = false;
			this.is_speedy = false;
			this.is_inky = false;
			this.is_clyde = false;
		}
		else if(this.numberOfPlayers == 2){
			this.is_pacman = false;
			this.is_blinky = true;
			this.is_speedy = false;
			this.is_inky = false;
			this.is_clyde = false;
		}
		else if(this.numberOfPlayers == 3){
			this.is_pacman = false;
			this.is_blinky = false;
			this.is_speedy = true;
			this.is_inky = false;
			this.is_clyde = false;
		}
		else if(this.numberOfPlayers == 4){
			this.is_pacman = false;
			this.is_blinky = false;
			this.is_speedy = false;
			this.is_inky = true;
			this.is_clyde = false;
		}
		else if(this.numberOfPlayers == 5){
			this.is_pacman = false;
			this.is_blinky = false;
			this.is_speedy = false;
			this.is_inky = false;
			this.is_clyde = true;
		}
		this.characterPacket = udp_packet.createCharacter(player_name, Integer.toString(numberOfPlayers), Integer.toString(pacman.getNumberOfLives()), Integer.toString(pacman.getSize()), Integer.toString(pacman.getXPos()), Integer.toString(pacman.getYPos()));
		this.playerPacket = udp_packet.createPlayer(player_name, this.characterPacket);

		this.boardUI = new JLabel[BOARD_LENGTH][BOARD_WIDTH];
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

	public void updateBoard(){
		int yPos, xPos;
		if (this.is_pacman) {
			yPos = this.board.getPacmanYPos();
			xPos = this.board.getPacmanXPos();
			this.boardUI[this.pacman.getPrevYPos(move)][this.pacman.getPrevXPos(move)] = new JLabel(IMAGELIST.getImage("empty"));
			if(pacman.getSize() == NORMAL_PACMAN) this.boardUI[yPos][xPos] = new JLabel(IMAGELIST.getImage("pac"+move));
			else this.boardUI[yPos][xPos] = new JLabel(IMAGELIST.getImage("pacman"+move));
		}
		setBoardUI();
	}
	public void setBoardUI(){
		for(int i = 0; i < BOARD_LENGTH; i++)
			for(int j = 0; j < BOARD_WIDTH; j++)
				this.add(this.boardUI[i][j]);
	}
	public void setBoard(String[][] boardLayout){
		for(int i = 0; i < BOARD_LENGTH; i++){
			for(int j = 0; j < BOARD_WIDTH; j++){
				switch(boardLayout[i][j]){
					case WALL:
						this.boardUI[i][j] = new JLabel(IMAGELIST.getImage("wall"));
						break;
					case DOT:
						this.boardUI[i][j] = new JLabel(IMAGELIST.getImage("smalldot"));
						break;
					case BIGDOT:
						this.boardUI[i][j] = new JLabel(IMAGELIST.getImage("bigdot"));
						break;	
					case EMPTY:
						this.boardUI[i][j] = new JLabel(IMAGELIST.getImage("empty"));
						break;
					case OUT:
						this.boardUI[i][j] = new JLabel(IMAGELIST.getImage("empty"));
						break;
					case BLINKY:
						this.boardUI[i][j] = new JLabel(IMAGELIST.getImage("ghost"));
						break;
					case SPEEDY:
						this.boardUI[i][j] = new JLabel(IMAGELIST.getImage("ghost"));
						break;
					case INKY:
						this.boardUI[i][j] = new JLabel(IMAGELIST.getImage("ghost"));
						break;
					case CLYDE:
						this.boardUI[i][j] = new JLabel(IMAGELIST.getImage("ghost"));
						break;
					case PACMAN:
						if(move=="")this.boardUI[i][j] = new JLabel(IMAGELIST.getImage("pacRIGHT"));
						else this.boardUI[i][j] = new JLabel(IMAGELIST.getImage("pac"+move));
						break;
				}
					
			}
		}
		setBoardUI();
	}

	public void updatePanel(){ //updates the puzzlePanel whenever the player is moved
		this.removeAll();
		// this.setBoard(this.board.getBoardLayout());
		this.updateBoard();
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
		this.characterPacket = udp_packet.createCharacter(player_name, "1", Integer.toString(this.pacman.getNumberOfLives()), Integer.toString(this.pacman.getSize()), Integer.toString(this.pacman.getXPos()), Integer.toString(this.pacman.getYPos()));
		this.playerPacket = udp_packet.createPlayer(player_name, this.characterPacket);
		this.updatePanel();
		// this.printBoard();
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
		String serverName = args[0];
		String playerName = args[1];
		JFrame pacmanFrame = new JFrame("pacman Game");
		
		try{
			PacmanClient client = new PacmanClient(serverName, playerName);
			
			pacmanFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pacmanFrame.setPreferredSize(new Dimension(775, 700));
			pacmanFrame.setResizable(false);
			pacmanFrame.add(client);
			pacmanFrame.pack();
			pacmanFrame.setVisible(true);	
		}
		// catch(IOException e){
  //           e.printStackTrace();
  //           System.out.println("Cannot find (or disconnected from) Server");
  //       }
		catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
            System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
        }
        
	
	}
}