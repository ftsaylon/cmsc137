package pacman.game;

import packet.PlayerProtos.*;
import packet.TcpPacketProtos.TcpPacket.*;

import java.net.Socket;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class ChatPanel extends JPanel{
	private String name;
	private String lobbyId;
	private String serverName;
	private int port;
    private JTextArea chatArea; 
    private JTextField chatBox;
    private JLabel senderName;
    private ChatReceiver chatReceiver;
    private ChatSender chatSender;
    private Boolean startChat;
    private Socket server;
    
    private User user;
    private Packet packet;
    private String player_name;

	public ChatPanel(String player_name, String serverName, int port, boolean is_pacman){
		this.name = name;
		// this.lobbyId = lobbyId;
		
		this.setPreferredSize(new Dimension(200, 700));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.chatArea = new JTextArea(200,300);
        this.chatArea.setEditable(false);
        this.chatArea.setLineWrap(true);
        this.chatBox = new JTextField();
        this.chatBox.setPreferredSize(new Dimension(200, 100));
        this.senderName = new JLabel();
        this.add(senderName);
        this.senderName.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(chatArea);
        this.chatArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(chatBox);
        this.chatBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.player_name = player_name;

		 try {
            this.packet = new Packet();
            this.user = new User(packet, player_name);
           
            System.out.println("Connecting to " + serverName + " on port " + port);
            
            this.server = new Socket(serverName, port);

            System.out.println("\nConnected to " + server.getRemoteSocketAddress());
            packet.setSocket(server);

            if(is_pacman){
                CreateLobbyPacket createLobby = packet.createLobby(5);
                packet.send(createLobby.toByteArray());
                CreateLobbyPacket lobby = CreateLobbyPacket.parseFrom(packet.receive()); 
                lobbyId = lobby.getLobbyId();
                System.out.println("Lobby id: "+ lobbyId);
            }
            else{
                System.out.print("Enter Lobby ID: ");
                Scanner str = new Scanner(System.in);
                lobbyId = str.nextLine();
            }
            ConnectPacket createConnection = packet.createConnection(user.getPlayer(), lobbyId);
            packet.send(createConnection.toByteArray());
            ConnectPacket connect = ConnectPacket.parseFrom(packet.receive());
            
            System.out.println(player_name + " has joined to the lobby.");
                
        }
        catch(UnknownHostException unEx) {
            unEx.printStackTrace();
        }
        catch(IOException err) {
            err.printStackTrace();
        }
    }

	public String getLobbyId(){
		return this.lobbyId;
    }
    
    public void startChat(){
        try{
            this.startChat = true;
    
            this.chatSender = new ChatSender(this.packet, this.user, this.lobbyId, this.chatBox, this.chatArea);
            this.chatReceiver = new ChatReceiver(this.packet, this.user, this.lobbyId, this.chatArea);
            Thread sender = new Thread(this.chatSender);
            Thread receiver = new Thread(this.chatReceiver);
            this.senderName.setText(this.player_name);
            
            sender.start();
            receiver.start();
            try {
                sender.join();
                receiver.join();
            } catch(Exception e) {};
            
            this.server.close();

        }
        catch(UnknownHostException unEx) {
            unEx.printStackTrace();
        }
        catch(IOException err) {
            err.printStackTrace();
        }

    }
    

}
