package src;

import packet.PlayerProtos.*;
import packet.TcpPacketProtos.TcpPacket.*;
import packet.TcpPacketProtos.TcpPacket;

import java.io.IOException;
import javax.swing.JTextArea;

public class ChatReceiver implements Runnable {
    private Packet packet;
    private User user;
    private String lobbyId;
    private Boolean connected;
    private JTextArea chatArea;
    public ChatReceiver(Packet packet, User user, String lobbyId, JTextArea chatArea) {
        this.packet = packet;
        this.user = user;
        this.lobbyId = lobbyId;
        this.connected = true;
        this.chatArea = chatArea;
    }

    void receive(){
        try {
            byte[] bytes = packet.receive();
            TcpPacket data = TcpPacket.parseFrom(bytes);
            PacketType type = data.getType();

            switch(type){
                case CONNECT:
                    ConnectPacket connect = ConnectPacket.parseFrom(bytes);
                    System.out.println(connect.getPlayer().getName() + " has joined to the lobby.");
                    break;
                case CHAT:
                    ChatPacket chat = ChatPacket.parseFrom(bytes);
                    if(chat.getMessage().equals("quit")) {
                        if(chat.getPlayer().getId().equals(user.getPlayer().getId())){
                            System.out.println(chat.getPlayer().getName() + " has left from the lobby.");
                            this.connected = false;
                        }
                    }
                    else if(chat.hasPlayer())
                        System.out.println(chat.getPlayer().getName() + ": " + chat.getMessage());
                        chatArea.append(chat.getPlayer().getName() + ": " + chat.getMessage());
                    break;
                case DISCONNECT:
                    DisconnectPacket disconnect = DisconnectPacket.parseFrom(bytes);
                    System.out.println(disconnect.getPlayer().getName() + " has left from the lobby.");
                    break;
            }
        }
        catch(IOException err) {
            err.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(this.connected){
            this.receive();
        }
    }
}