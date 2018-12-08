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

	private int id;
	private int clientPort;

	List<Player> players;

	DatagramSocket clientSocket; 
	UDPPacket udp_packet = new UDPPacket();
	Player playerPacket;
	Character characterPacket;

	Thread t = new Thread(this);

	private String server_ip;
	private String player_name;
	private boolean is_pacman;
	private boolean is_ghost;
	private String color;
	public PacmanClient(String server_ip, String player_name, Integer clientPort, Integer id){
		this.server_ip = server_ip;
		this.player_name = player_name;
		this.map = new Map(3);
		this.board = new Board(map);
		this.IMAGELIST = new ImageList();
		this.gameOver = false;
		this.is_connected = false;
		this.move = "";
		PacmanClient.numberOfPlayers ++;

		this.clientPort = clientPort; // Port of this client
		this.id = id;
		this.players = new ArrayList<Player>(); // Holds the players

		if(this.id == 1 || id == 2 || id == 3)	{ // ASSUMED FIRST THAT ID 1 and 2 are PACMANS
			this.pacman = new Pacman(this.board.getPacmanXPos(), this.board.getPacmanYPos(), this);
			this.is_pacman = true;
			this.is_ghost = false;
		}
		// else ghost
		else{
			this.is_ghost = true;
			this.is_pacman = false;
			switch(this.id){
				case 2:
					this.color = "RED";
					break;
				case 3:
					this.color = "BLUE";
					break;
				case 4:
					this.color = "PINK";
					break;
				case 5: 
					this.color = "ORANGE";
					break;

			}
		}

		this.characterPacket = udp_packet.createCharacter(player_name, id, pacman.getNumberOfLives(), pacman.getSize(), pacman.getXPos(), pacman.getYPos(), this.pacman.getXPos(), this.pacman.getYPos());
		this.playerPacket = udp_packet.createPlayer(player_name, this.characterPacket, this.clientPort);

		// this.players.add(playerPacket); // Add instance of player to list of players

		this.players.add(this.playerPacket);

		this.boardUI = new JLabel[BOARD_LENGTH][BOARD_WIDTH];
		this.setFocusable(true);
		this.addKeyListener(this);
		setOpaque(true);
		setBackground(Color.BLACK);
		setLayout(new GridLayout(31, 28));
		setBoard(board.getBoardLayout());
		setBoardUI();
		revalidate();
		repaint();

		try{
			this.clientSocket = new DatagramSocket(this.clientPort);
			this.udp_packet.setSocket(this.clientSocket); 
			this.udp_packet.send(this.playerPacket.toByteArray()); // Send playerPacket upon initialization of client
		}catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

		// Start Client thread
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
				// System.out.print(bl[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public Player getLastPlayer(){
		Player lastPlayer = (Player) players.get(players.size() - 1);
		Iterator iter = this.players.iterator();
		while(iter.hasNext()){
			 Player player = (Player) iter.next();
			 if(!(player.getCharacter().getName().equals(this.player_name)))	lastPlayer = player;
		}
		return lastPlayer;
	}
	public void updateBoardLayout(int x, int y, int prevX, int prevY, int player_id){
		this.board.updateBoardLayout(x, y, prevX, prevY, player_id);
	}
	public void updateOtherPlayer(){
		// Iterator iter = this.players.iterator();
		// while(iter.hasNext()){
		// Player player = (Player) players.get(players.size() - 1);
		Player lp = getLastPlayer();
		Character lpChar = lp.getCharacter();
		if(!(lpChar.getName().equals(this.player_name))){
			this.boardUI[lpChar.getYPos()][lpChar.getXPos()] = new JLabel(IMAGELIST.getImage("pacmanRIGHT"));
			// if(lpChar.getId()==1)
				this.boardUI[lpChar.getPrevYPos()][lpChar.getPrevXPos()] = new JLabel(IMAGELIST.getImage("empty"));
			this.board.updateBoardLayout(lpChar.getXPos(), lpChar.getYPos(), lpChar.getPrevXPos(), lpChar.getPrevYPos(), lpChar.getId());
			// this.boardUI[lpChar.getPrevYPos()][lpChar.getPrevXPos()] = 
			// if(this.lp.getCharacter().getId() == 1 || this.lp.getCharacter().getId() == 2)
			// System.out.println("NOOOO");
		}

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
		updateOtherPlayer();
		
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
	}

	public void updatePanel(){ //updates the puzzlePanel whenever the player is moved
		this.removeAll();
		this.updateBoard();
		this.setBoardUI();
		this.revalidate();
		this.repaint();
	}

	public void checkGameOver(){ //checks if the puzzle is solved and if it is, the panel will not be focused
	// 	if(state.getGameStatus() == GAME_OVER){
	// 		this.gameOver = true;
	// 		this.setFocusable(false);
	// 	}
	}

	public void pacmanRespawn(){
		
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
		this.characterPacket = udp_packet.createCharacter(player_name, this.characterPacket.getId(), this.pacman.getNumberOfLives(), this.pacman.getSize(), this.pacman.getXPos(), this.pacman.getYPos(), this.pacman.getPrevXPos(), this.pacman.getPrevYPos());
		this.playerPacket = udp_packet.createPlayer(player_name, this.characterPacket, this.clientPort);
		udp_packet.send(this.playerPacket.toByteArray());	
		this.updatePanel();
		checkGameOver();
	}
	
	public void keyTyped(KeyEvent ke){
	}
	
	public void keyReleased(KeyEvent ke){	
	}

	public void run(){
		byte[] buf = null;
		
		while(true){
			try{
				Thread.sleep(1);
			}catch(Exception ioe){}

			// Receive Game State Packet from Server
			buf = this.udp_packet.receive();
			GameState game = this.udp_packet.parseToGameState(buf);

			// Add players from server to list of players here in client
			if(game.getPlayerListCount() != 0){
				Iterator iter = game.getPlayerListList().iterator();
				while(iter.hasNext()){
					Player player = (Player) iter.next();
					// INSERT CODE TO PLOT OTHER PLAYERS TO THIS BOARD
					// System.out.println(player);
					players.add(player); // Add other players to the list of players
				}
			}
			this.updatePanel(); // Update the panel
			// if(game.getPlayerListList().size()==0)	System.out.println("JUSQ GG");
			System.out.println(game.getPlayerListList());
		}
		
		
	}

	public static void main(String[] args){
		JFrame pacmanFrame = new JFrame("Pacman Game " + args[1]);
		
		try{
			String serverName = args[0];
			String playerName = args[1];
			Integer clientPort = Integer.parseInt(args[2]);
			Integer id = Integer.parseInt(args[3]);

			System.out.println(args[0] + args[1] + args[2] + args[3]);
			PacmanClient client = new PacmanClient(serverName, playerName, clientPort, id);
			
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