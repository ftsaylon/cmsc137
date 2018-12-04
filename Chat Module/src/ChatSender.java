package src;

import packet.PlayerProtos.*;
import packet.TcpPacketProtos.TcpPacket.*;
import packet.TcpPacketProtos.TcpPacket;

import java.util.Scanner;

class ChatSender implements Runnable {
    private Packet packet;
    private User user;
    private String lobbyId;
    private Boolean connected;

    public ChatSender(Packet packet, User user, String lobbyId) {
        this.packet = packet;
        this.user = user;
        this.lobbyId = lobbyId;
        this.connected = true;
    }

    void send(){
        Scanner sc = new Scanner(System.in);
        System.out.println(">>> ");
        String message = sc.nextLine();

        ChatPacket createMessage = packet.createMessage(message, this.user.getPlayer(), this.lobbyId);
        packet.send(createMessage.toByteArray());
        
        if (message.equals("quit")) {
            DisconnectPacket removeConnection = packet.removeConnection(this.user.getPlayer());
            packet.send(removeConnection.toByteArray());
            this.connected = false;
        }
    }

    @Override
    public void run(){
        while(this.connected){
            this.send();
        }
    }
}