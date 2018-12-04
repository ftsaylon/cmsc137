package src;

import packet.PlayerProtos.*;
import packet.TcpPacketProtos.TcpPacket.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import java.io.IOException;

class Packet {
    private Socket server;

    void setSocket(Socket server) {
        this.server = server;
    }

    Player createPlayer(String playerName){
        Player player = 
            Player.newBuilder()
                .setName(playerName)
                .build();
        
        return player;
    }

    CreateLobbyPacket createLobby(int maxPlayers){
        CreateLobbyPacket lobbyPacket;
        
        if(maxPlayers != 0){
            lobbyPacket = 
                CreateLobbyPacket.newBuilder()
                    .setType(PacketType.CREATE_LOBBY)
                    .setMaxPlayers(maxPlayers)
                    .build();
        }else{
            lobbyPacket = 
                CreateLobbyPacket.newBuilder()
                    .setType(PacketType.CREATE_LOBBY)
                    .build();
        }

        return lobbyPacket;
    }

    ConnectPacket createConnection(Player player, String lobbyId){
        ConnectPacket connectPacket;

        if(player != null && lobbyId == null){
            connectPacket = 
                ConnectPacket.newBuilder()
                    .setType(PacketType.CONNECT)
                    .setPlayer(player)
                    .build();
        }else if(player == null && lobbyId != null){
            connectPacket = 
                ConnectPacket.newBuilder()
                    .setType(PacketType.CONNECT)
                    .setLobbyId(lobbyId)
                    .build();
        }else{
            connectPacket = 
                ConnectPacket.newBuilder()
                    .setType(PacketType.CONNECT)
                    .setPlayer(player)
                    .setLobbyId(lobbyId)
                    .build();
        }

        return connectPacket;
    }

    ChatPacket createMessage(String message, Player player, String lobbyId) {
        ChatPacket chatPacket;

        if(player != null && lobbyId == null){
            chatPacket = 
                ChatPacket.newBuilder()
                    .setType(PacketType.CHAT)
                    .setMessage(message)
                    .setPlayer(player)
                    .build();
        }else if(player == null && lobbyId != null){
            chatPacket = 
                ChatPacket.newBuilder()
                    .setType(PacketType.CHAT)
                    .setMessage(message)
                    .setLobbyId(lobbyId)
                    .build();
        }else{
            chatPacket = 
                ChatPacket.newBuilder()
                    .setType(PacketType.CHAT)
                    .setMessage(message)
                    .setPlayer(player)
                    .setLobbyId(lobbyId)
                    .build();
        }

        return chatPacket;
    }

    DisconnectPacket removeConnection(Player player) {
        DisconnectPacket disconnectPacket;
        
        if(player != null){
            disconnectPacket = 
                DisconnectPacket.newBuilder()
                    .setType(PacketType.DISCONNECT)
                    .setPlayer(player)
                    .build();
        }else{
            disconnectPacket = 
                DisconnectPacket.newBuilder()
                    .setType(PacketType.DISCONNECT)
                    .build();
        }

        return disconnectPacket;
    }

    void send(byte[] buf) {
        try{
            OutputStream outToServer = this.server.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.write(buf);
        }catch(IOException err) {
            err.printStackTrace();
            System.out.println("Cannot find (or disconnected from) Server");
        }
    }

    byte[] receive() {
        byte[] buf = null;

        try{
            InputStream inFromServer = this.server.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            while(in.available() == 0){}
            buf = new byte[in.available()];
            in.read(buf);
        }catch(IOException err) {
            err.printStackTrace();
            System.out.println("Cannot find (or disconnected from) Server");
        }

        return buf;
    }
}