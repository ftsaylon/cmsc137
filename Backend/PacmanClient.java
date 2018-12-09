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
	private Board board;
	private JLabel[][] boardUI;
	private boolean gameOver;
	private ImageList IMAGELIST;
	private boolean is_connected;
	private String move;
	private Map map;
	private HashMap<Ghost, String> ghosts;
	// private ChatPanel chatPanel;
	// private JPanel boardPanel;
	static final int NOT_OVER = 0;
	// private static int numberOfPlayers = 0;

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
		// PacmanClient.numberOfPlayers ++;

		this.clientPort = clientPort; // Port of this client
		this.id = id;
		this.port = 80;
		this.players = new ArrayList<Player>(); // Holds the players
		this.pacman = new Pacman(this.board.getPacmanXPos(), this.board.getPacmanYPos(), this);
    
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
		// if(this.id == 1) setChatServer();
		// else {

		// }

		// this.players.add(playerPacket); // Add instance of player to list of players

		this.players.add(this.playerPacket);

		this.boardUI = new JLabel[BOARD_LENGTH][BOARD_WIDTH];
		
		// this.boardPanel.setPreferredSize(new Dimension(775, 700));
		this.setLayout(new GridLayout(31, 28));
		
		// this.add(chatPanel, BorderLayout.WEST);
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
	public void setChatServer(){
		// JPanel chatPanel = new JPanel();
  //       chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
  //       JTextArea chatArea = new JTextArea(200,300);
  //       chatArea.setEditable(false);
  //       chatArea.setLineWrap(true);
  //       JButton sendButton = new JButton("Send");
  //       JTextField chatBox = new JTextField();
  //       chatBox.setPreferredSize(new Dimension(200, 100));
  //       // sendButton.addActionListener(new sendButtonListener());
  //       JLabel senderName = new JLabel();
  //       chatPanel.add(senderName);
  //       senderName.setAlignmentX(Component.CENTER_ALIGNMENT);
  //       chatPanel.add(chatArea);
  //       chatArea.setAlignmentX(Component.CENTER_ALIGNMENT);
  //       chatPanel.add(chatBox);
  //       chatBox.setAlignmentX(Component.CENTER_ALIGNMENT);
  //       chatPanel.add(sendButton);
  //       sendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
  //       this.add(chatPanel, BorderLayout.WEST);
        // add(chatPanel, Bo);
       //  try {
       //      Packet packet = new Packet();
       //      // packet.createPlayer(player_name);
       //      User user = new User(packet, player_name);

            
       //      System.out.println("Connecting to " + "202.92.144.45" + " on port " + port);
                
     		// Socket server = new Socket("202.92.144.45", port);

       //      System.out.println("\nConnected to " + server.getRemoteSocketAddress());
       //      packet.setSocket(server);

      	// 	CreateLobbyPacket createLobby = packet.createLobby(5);
       //      packet.send(createLobby.toByteArray());
       //      CreateLobbyPacket lobby = CreateLobbyPacket.parseFrom(packet.receive()); 
       //      lobbyId = lobby.getLobbyId();

       //      ConnectPacket createConnection = packet.createConnection(user.getPlayer(), lobbyId);
       //      packet.send(createConnection.toByteArray());
       //      ConnectPacket connect = ConnectPacket.parseFrom(packet.receive());
       //      user.setPlayer(connect.getPlayer());
       //      System.out.println(user.getPlayer().getName() + " has joined to the lobby.");

       //      ChatSender chatSender = new ChatSender(packet, user, lobbyId, sendButton, chatBox, chatArea);
       //      ChatReceiver chatReceiver = new ChatReceiver(packet, user, lobbyId, chatArea);
       //      Thread sender = new Thread(chatSender);
       //      Thread receiver = new Thread(chatReceiver);
       //      senderName.setText(user.getPlayer().getName());
            
       //          sender.start();
       //          receiver.start();
       //          try {
       //              sender.join();
       //              receiver.join();
       //          } catch(Exception e) {};
       //          System.out.println("YOW");
       //          server.close();
            

       //  }
       //  catch(UnknownHostException unEx) {
       //      unEx.printStackTrace();
       //  }
       //  catch(IOException err) {
       //      err.printStackTrace();
       //  }
	}

	// public CreateLobbyPacket createLobbyPacket(String lobby_id, Integer max_players){
 //        CreateLobbyPacket lobby_packet =
 //            CreateLobbyPacket.newBuilder()
 //            .setType(PacketType.CREATE_LOBBY)
 //            .setLobbyId(lobby_id)
 //            .setMaxPlayers(max_players)
 //            .build();

 //        return lobby_packet;
 //    }
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
			pacmanFrame.setPreferredSize(new Dimension(775, 700));
			pacmanFrame.setResizable(false);
			pacmanFrame.add(client);
			pacmanFrame.pack();
			pacmanFrame.setVisible(true);

			System.out.println("CREATE CHAT ");
			ChatPanel chatPanel = new ChatPanel(playerName, "202.92.144.45", 80, is_pacman);
			chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			chatFrame.setPreferredSize(new Dimension(200, 700));
			chatFrame.setResizable(false);
			chatFrame.add(chatPanel);
			chatFrame.pack();
			chatFrame.setVisible(true);
			chatPanel.startChat();

		}catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java PacmanClient <server ip> <name> <port no.>");
        }
        
	
	}
}