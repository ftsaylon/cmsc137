package src;

import packet.PlayerProtos.*;
import packet.TcpPacketProtos.TcpPacket.*;

import java.net.Socket;
import java.util.Scanner;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Client extends JFrame{
    private int choice;
    private String lobbyId;
    private JTextArea chatArea; 
    private JTextField chatBox;
    private JButton sendButton;
    private JLabel senderName;
    private JPanel chatPanel;
    private ChatReceiver chatReceiver;
    private ChatSender chatSender;

    public Client(String serverName, int port) {
        this.chatPanel = new JPanel();
        this.chatPanel.setLayout(new BoxLayout(this.chatPanel, BoxLayout.Y_AXIS));
        this.chatArea = new JTextArea(200,300);
        this.chatArea.setEditable(false);
        this.chatArea.setLineWrap(true);
        this.sendButton = new JButton("Send");
        this.chatBox = new JTextField();
        this.chatBox.setPreferredSize(new Dimension(200, 100));
        // this.sendButton.addActionListener(new sendButtonListener());
        this.senderName = new JLabel();
        this.chatPanel.add(senderName);
        this.senderName.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.chatPanel.add(chatArea);
        this.chatArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.chatPanel.add(chatBox);
        this.chatBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.chatPanel.add(sendButton);
        this.sendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(chatPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(200, 700));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        
        try {
            Packet packet = new Packet();
            User user = new User(packet);

            do{
                System.out.println("Connecting to " + serverName + " on port " + port);
                
                Socket server = new Socket(serverName, port);

                System.out.println("\nConnected to " + server.getRemoteSocketAddress());
                packet.setSocket(server);

                System.out.println("-------- CHAT --------");
                System.out.println("[1] Create Lobby");
                System.out.println("[2] Join Lobby");
                System.out.println("[3] Exit");
                System.out.println("----------------------");
                System.out.print(">>> ");
        
                Scanner sc = new Scanner(System.in);
                choice = sc.nextInt();

                switch(choice){
                    case 1:
                        CreateLobbyPacket createLobby = packet.createLobby(4);
                        packet.send(createLobby.toByteArray());
                        CreateLobbyPacket lobby = CreateLobbyPacket.parseFrom(packet.receive());
                        lobbyId = lobby.getLobbyId();
                        System.out.println(user.getPlayer().getName() + " successfully created new lobby " + lobbyId + ".");
                        break;
                    case 2:
                        System.out.print("Enter Lobby ID: ");
                        Scanner str = new Scanner(System.in);
                        lobbyId = str.nextLine();
                        break;  
                    case 3:
                        System.exit(0);
                        break;
                }

                ConnectPacket createConnection = packet.createConnection(user.getPlayer(), lobbyId);
                packet.send(createConnection.toByteArray());
                ConnectPacket connect = ConnectPacket.parseFrom(packet.receive());
                user.setPlayer(connect.getPlayer());
                System.out.println(user.getPlayer().getName() + " has joined to the lobby.");

                chatSender = new ChatSender(packet, user, lobbyId, this.sendButton, this.chatBox, this.chatArea);
                chatReceiver = new ChatReceiver(packet, user, lobbyId, this.chatArea);
                Thread sender = new Thread(chatSender);
                Thread receiver = new Thread(chatReceiver);
                this.senderName.setText(user.getPlayer().getName());
            
                sender.start();
                receiver.start();
                try {
                    sender.join();
                    receiver.join();
                } catch(Exception e) {};

                server.close();
            }while(choice != 3);
        }
        catch(UnknownHostException unEx) {
            unEx.printStackTrace();
        }
        catch(IOException err) {
            err.printStackTrace();
        }
    }
   
    // class sendButtonListener implements ActionListener {
    //     public void actionPerformed(ActionEvent event) {
    //         if (chatBox.getText().length() < 1) {
    //             // do nothing 
    //         } else {
    //             chatArea.append("<" + senderName.getText() + ">:  " + chatBox.getText() + "\n");
    //             chatBox.setText("");
    //         }
    //     }
    // }

}