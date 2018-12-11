package pacman.game;

// import packet.PlayerProtos.Player;
// import packet.CharacterProtos.Character;
import packet.PlayerProtos.*;
import packet.TcpPacketProtos.TcpPacket.*;
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
	private Ghost ghost;
	private Board board;
	private JLabel[][] boardUI;
	private boolean gameOver;
	private ImageList IMAGELIST;
	private boolean is_connected;
	private String move;
	private Map map;
	private HashMap<String, String> ghosts;
	private ChatPanel chatPanel;
	static final int NOT_OVER = 0;

	private int id;
	private int clientPort, port;

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
	private String lobbyId;
	// private static final String lobbyId = "CHAT";
	public PacmanClient(String server_ip, String player_name, Integer clientPort, Integer id){
		this.server_ip = server_ip;
		this.player_name = player_name;
		this.map = new Map(3);
		this.board = new Board(map);
		this.IMAGELIST = new ImageList();
		this.gameOver = false;
		this.is_connected = false;
		this.move = "";
		this.clientPort = clientPort; // Port of this client
		this.id = id;
		this.port = 80;
		this.players = new ArrayList<Player>(); // Holds the players
		this.pacman = new Pacman(this.board.getPacmanXPos(), this.board.getPacmanYPos(), this);
    
		if(this.id == 1)	{ // ASSUMED FIRST THAT ID 1 and 2 are PACMANS
			// this.pacman = new Pacman(this.board.getPacmanXPos(), this.board.getPacmanYPos(), this);
			this.is_pacman = true;
			this.is_ghost = false;
			this.color = "pacman";
			this.characterPacket = udp_packet.createCharacter(player_name, color, id, pacman.getNumberOfLives(), pacman.getSize(), pacman.getXPos(), pacman.getYPos(), this.pacman.getXPos(), this.pacman.getYPos());
		}
		// else ghost
		else{
			int y = this.board.getInitialGhostYPos(this.id);
			int x = this.board.getInitialGhostXPos(this.id);
			this.is_ghost = true;
			this.is_pacman = false;
			switch(this.id){
				case 2:
					this.color = "red";
					break;
				case 3:
					this.color = "blue";
					break;
				case 4:
					this.color = "pink";
					break;
				case 5: 
					this.color = "orange";
					break;

			}
			System.out.println(x + " "+ y);
			this.ghost = new Ghost(player_name, x, y, color, this);
			this.characterPacket = udp_packet.createCharacter(player_name, color, id, ghost.getNumberOfLives(), 1, x, y, x, y);
		}
		
		
		this.playerPacket = udp_packet.createPlayer(player_name, this.characterPacket, this.clientPort);
		this.players.add(this.playerPacket);
		this.boardUI = new JLabel[BOARD_LENGTH][BOARD_WIDTH];
		this.setLayout(new GridLayout(31, 28));
		this.setFocusable(true);
		this.addKeyListener(this);
		setOpaque(true);
		setBackground(Color.BLACK);
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

		// Send playerPacket upon initialization of client
		udp_packet.send(this.playerPacket.toByteArray());

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

	public Player getLastPlayer(){
		Player lastPlayer = (Player) players.get(players.size() - 1);
		Iterator iter = this.players.iterator();
		while(iter.hasNext()){
			 Player player = (Player) iter.next();
			 if(!(player.getCharacter().getName().equals(this.player_name)))	lastPlayer = player;
		}
		return lastPlayer;
	}
	public String getMove(int xPos, int yPos, int prevXPos, int prevYPos){
		if(xPos == prevXPos){
			if(yPos == prevYPos + 1)	return MOVE_DOWN;
			else return MOVE_UP;
		}
		else{
			if(xPos == prevXPos + 1)	return MOVE_RIGHT;
			else return MOVE_LEFT;
		}
	}
	public void updateBoardLayout(int x, int y, int prevX, int prevY, int player_id){
		this.board.updateBoardLayout(x, y, prevX, prevY, player_id);
	}
	public void updateOtherPlayer(){
		Player lp = getLastPlayer();
		Character lpChar = lp.getCharacter();
		int xPos = lpChar.getXPos();
		int yPos = lpChar.getYPos();
		int prevYPos = lpChar.getPrevYPos();
		int prevXPos = lpChar.getPrevXPos();
		if(!(lpChar.getName().equals(this.player_name))){

			this.boardUI[yPos][xPos] = new JLabel(IMAGELIST.getImage(lpChar.getColor()+getMove(xPos, yPos, prevXPos, prevYPos)));
			this.boardUI[prevYPos][prevXPos] = new JLabel(IMAGELIST.getImage("empty"));
			if(lpChar.getId()==1){
				if(prevXPos != xPos && prevYPos != yPos){
					if(this.board.isDot(xPos, yPos))	this.board.decrementNumberOfDots();
						this.boardUI[prevYPos][prevXPos] = new JLabel(IMAGELIST.getImage("empty"));
				}

			}
			this.board.updateBoardLayout(xPos, yPos, prevXPos, prevYPos, lpChar.getId());
			
		}

	}
	public void updateBoard(){
		int yPos, xPos, prevYPos, prevXPos;
		if (this.is_pacman) {
			yPos = this.pacman.getYPos();
			xPos = this.pacman.getXPos();
			prevYPos = this.pacman.getPrevYPos();
			prevXPos = this.pacman.getPrevXPos();
			
			if(prevYPos != yPos || prevXPos != xPos){
				this.boardUI[prevYPos][prevXPos] = new JLabel(IMAGELIST.getImage("empty"));
				this.boardUI[yPos][xPos] = new JLabel(IMAGELIST.getImage("empty"));
			}
			if(pacman.getSize() == NORMAL_PACMAN) this.boardUI[yPos][xPos] = new JLabel(IMAGELIST.getImage("pac"+move));
			else this.boardUI[yPos][xPos] = new JLabel(IMAGELIST.getImage("pac"+move));
			updateBoardLayout(xPos, yPos, prevXPos, prevYPos, 1);
		}
		else {
			yPos = this.ghost.getYPos();
			xPos = this.ghost.getXPos();
			prevYPos = this.ghost.getPrevYPos();
			prevXPos = this.ghost.getPrevXPos();
			
			if(!(prevYPos == yPos && prevXPos == xPos)){
				this.boardUI[prevYPos][prevXPos] = new JLabel(IMAGELIST.getImage("empty"));
				updateBoardLayout(xPos, yPos, prevXPos, prevYPos, 2);
			}
			this.boardUI[yPos][xPos] = new JLabel(IMAGELIST.getImage(color+move));
			
			
		}
		updateOtherPlayer();
		
	}
	public void setBoardUI(){
		this.removeAll();
		for(int i = 0; i < BOARD_LENGTH; i++)
			for(int j = 0; j < BOARD_WIDTH; j++)
				this.add(this.boardUI[i][j]);
		printBoard();

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
		this.updateBoard();
		this.setBoardUI();
		this.revalidate();
		this.repaint();
	}

	public void checkGameOver(){ //checks if the puzzle is solved and if it is, the panel will not be focused
		// if(state.getGameStatus() == GAME_OVER){
		// 	this.gameOver = true;
		// 	this.setFocusable(false);
		// }
	}

	public void pacmanRespawn(){
		
	}

	public Board getGameBoard(){
		return this.board;
	}

	public void keyPressed(KeyEvent ke){ //if UP, DOWN, LEFT, or RIGHT key is pressed whedin the puzzlePanel is focused
		if(ke.getKeyCode()==KeyEvent.VK_UP){
			if(is_pacman)	this.pacman.moveUp();
			else this.ghost.moveUp();
			move = MOVE_UP;
		}else if(ke.getKeyCode()==KeyEvent.VK_DOWN){
			if(is_pacman) this.pacman.moveDown();
			else this.ghost.moveDown();
			move = MOVE_DOWN;
		}else if(ke.getKeyCode()==KeyEvent.VK_LEFT){
			if(is_pacman) this.pacman.moveLeft();
			else this.ghost.moveLeft();
			move = MOVE_LEFT;
		}else if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
			if(is_pacman) this.pacman.moveRight();
			else this.ghost.moveRight();
			move = MOVE_RIGHT;
		}

		// Update Packets to be sent to server whenever there's movement
		if(is_pacman)this.characterPacket = udp_packet.createCharacter(player_name, color, this.characterPacket.getId(), this.pacman.getNumberOfLives(), this.pacman.getSize(), this.pacman.getXPos(), this.pacman.getYPos(), this.pacman.getPrevXPos(), this.pacman.getPrevYPos());
		else this.characterPacket = udp_packet.createCharacter(player_name, color, id, ghost.getNumberOfLives(), 1, this.ghost.getXPos(), this.ghost.getYPos(), this.ghost.getPrevXPos(), this.ghost.getPrevYPos());
		this.playerPacket = udp_packet.createPlayer(player_name, this.characterPacket, this.clientPort);
		udp_packet.send(this.playerPacket.toByteArray());	
		this.updatePanel();
		checkGameOver();
		// System.out.println(this.cha)
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
			this.updatePanel(); 
		}
		
		
	}

	public static void main(String[] args){
		JFrame pacmanFrame = new JFrame("Pacman Game " + args[1]);
		JFrame chatFrame  = new JFrame("Chat Frame");
		
		try{
			String serverName = args[0];
			String playerName = args[1];
			Integer clientPort = Integer.parseInt(args[2]);
			Integer id = Integer.parseInt(args[3]);

			System.out.println(args[0] + args[1] + args[2] + args[3]);
			PacmanClient client = new PacmanClient(serverName, playerName, clientPort, id);
			boolean is_pacman;
			if(id == 1) is_pacman = true;
			else is_pacman = false;	
			pacmanFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pacmanFrame.setPreferredSize(new Dimension(975, 700));
			pacmanFrame.setResizable(false);
			pacmanFrame.add(client);
			pacmanFrame.pack();
			pacmanFrame.setVisible(true);
			ChatPanel chatPanel = new ChatPanel(playerName, "202.92.144.45", 80, is_pacman);
			pacmanFrame.add(chatPanel, BorderLayout.WEST);
			pacmanFrame.revalidate();
			chatPanel.startChat();

		}catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java PacmanClient <server ip> <name> <port no.>");
        }
        
	
	}
}