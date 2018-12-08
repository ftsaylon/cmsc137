package pacman.game;

// import packet.PlayerProtos.Player;
// import packet.CharacterProtos.Character;
import packet.UdpPacketProtos.UdpPacket.Player;
import packet.UdpPacketProtos.UdpPacket.Character;
import packet.UdpPacketProtos.UdpPacket.GameState;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.util.List;

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

	private int clientPort;

	List<Player> players;

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
	private boolean is_ghost;

	public PacmanClient(String server_ip, String player_name, Integer clientPort){
		this.server_ip = server_ip;
		this.player_name = player_name;
		this.map = new Map(3);
		this.board = new Board(map);
		this.IMAGELIST = new ImageList();
		this.gameOver = false;
		this.is_connected = false;
		this.move = "";
		this.numberOfPlayers ++;
		
		this.clientPort = clientPort;

		if(this.numberOfPlayers == 1)	{
			this.pacman = new Pacman(this.board.getPacmanXPos(), this.board.getPacmanYPos(), this);
			this.is_pacman = true;
			this.is_ghost = false;
		}
		// else ghost
		else{
			this.is_ghost = true;
			this.is_pacman = false;
		}

		this.characterPacket = udp_packet.createCharacter(player_name, numberOfPlayers, pacman.getNumberOfLives(), pacman.getSize(), pacman.getXPos(), pacman.getYPos());
		this.playerPacket = udp_packet.createPlayer(player_name, this.characterPacket, this.clientPort);

		// this.players.add(playerPacket); // Add instance of player to list of players

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
		int yPos, xPos, prevYPos, prevXPos;
		if (this.is_pacman) {
			yPos = this.pacman.getYPos();
			xPos = this.pacman.getXPos();
			prevYPos = this.pacman.getPrevYPos();
			prevXPos = this.pacman.getPrevXPos();
			
			if(!(prevYPos == yPos && prevXPos == xPos)){
				this.boardUI[prevYPos][prevXPos] = new JLabel(IMAGELIST.getImage("empty"));
			}
			if(pacman.getSize() == NORMAL_PACMAN) this.boardUI[yPos][xPos] = new JLabel(IMAGELIST.getImage("pac"+move));
			else this.boardUI[yPos][xPos] = new JLabel(IMAGELIST.getImage("pacman"+move));
		}
		else {
			
			
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
					case GHOST:
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

		// Update Packets to be sent to server whenever there's movement
		this.characterPacket = udp_packet.createCharacter(player_name, this.characterPacket.getId(), this.pacman.getNumberOfLives(), this.pacman.getSize(), this.pacman.getXPos(), this.pacman.getYPos());
		this.playerPacket = udp_packet.createPlayer(player_name, this.characterPacket, this.clientPort);
		
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
			byte[] buf = null;
			this.clientSocket = new DatagramSocket(this.clientPort);
			
			while(true){
				udp_packet.setSocket(this.clientSocket);
				
				if(playerPacketOld == null){
					playerPacketOld = playerPacket;
					udp_packet.send(this.playerPacket.toByteArray());
				}else if(playerPacket.getCharacter().getXPos() != playerPacketOld.getCharacter().getXPos() || playerPacket.getCharacter().getYPos() != playerPacketOld.getCharacter().getYPos()){
					udp_packet.send(this.playerPacket.toByteArray());	
				}
				
				playerPacketOld = playerPacket;

				buf = udp_packet.receive();
				GameState game = GameState.parseFrom(buf);

				Iterator iter = game.getPlayerListList().iterator();
				while(iter.hasNext()){
					Player player = (Player) iter.next();
					// INSERT CODE TO PLOT OTHER PLAYERS TO THIS BOARD
				}

				// System.out.println(game.getPlayerListCount());
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch(Exception ioe){
			ioe.printStackTrace();
		}
		
	}

	public static void main(String[] args){
		JFrame pacmanFrame = new JFrame("pacman Game");
		
		try{
			String serverName = args[0];
			String playerName = args[1];
			Integer clientPort = Integer.parseInt(args[2]);
			PacmanClient client = new PacmanClient(serverName, playerName, clientPort);
			
			pacmanFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pacmanFrame.setPreferredSize(new Dimension(775, 700));
			pacmanFrame.setResizable(false);
			pacmanFrame.add(client);
			pacmanFrame.pack();
			pacmanFrame.setVisible(true);	
		}catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java PacmanClient <server ip> <name> <port no.>");
        }
        
	
	}
}