package src;

import packet.PlayerProtos.*;
import packet.TcpPacketProtos.TcpPacket.*;
import packet.TcpPacketProtos.TcpPacket;

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatSender implements Runnable {
    private Packet packet;
    private User user;
    private String lobbyId;
    private Boolean connected;
    private String message;
    private JButton sendButton;
    private JTextField chatBox;
    private JTextArea chatArea;
    public ChatSender(Packet packet, User user, String lobbyId, JButton sendButton, JTextField chatBox, JTextArea chatArea) {
        this.packet = packet;
        this.user = user;
        this.lobbyId = lobbyId;
        this.connected = true;
        this.chatBox = chatBox;
        this.sendButton = sendButton;
        this.message = "";
        this.chatBox = chatBox;
        this.chatArea = chatArea;
       
    }
    public void setMessage(String message){
        this.chatBox.setText(message);
    }
    public String getMessage(){
        return this.chatBox.getText();
    }
        
     
    public void send(){
        this.sendButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String message = chatBox.getText();

                if (message != null && message.trim().length() > 0){
                    ChatPacket createMessage = packet.createMessage(message, user.getPlayer(), lobbyId);
                    packet.send(createMessage.toByteArray());
                    if (message.equals("quit")) {
                        DisconnectPacket removeConnection = packet.removeConnection(user.getPlayer());
                        packet.send(removeConnection.toByteArray());
                        connected = false;
                    }
                    chatBox.setText("");
                    chatArea.append( user.getPlayer().getName() + " :  " + chatBox.getText() + "\n");
                }
                
            }
        });
        
               
    }
    @Override
    public void run(){
        if(this.connected){
            this.send();
        }
    }
}