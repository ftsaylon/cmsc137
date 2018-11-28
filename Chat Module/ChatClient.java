import proto.TcpPacketProtos.TcpPacket.*;
import proto.PlayerProtos.*;

import java.io.*;
import java.net.*;
import java.util.*;

class ChatClient{
    Lobby lobby;
    Player player;

    public ChatClient(Lobby lobby){
        this.lobby = lobby;
    }

    void JoinLobby(){
        try{
            this.player = 
                Player.newBuilder()
                    .setId("1")
                    .setName("Self")
                    .build();

            ConnectPacket connectPacket = 
                ConnectPacket.newBuilder()
                    .setType(PacketType.CONNECT)
                    .setPlayer(player)
                    .setLobbyId(this.lobby.lobbyId)
                    .build();
            
            // Send to server
            this.lobby.out.write(connectPacket.toByteArray());

            byte[] buf = new byte[1024]; // Create storage
            int fromServer = lobby.in.read(buf); // Get reply from server

            buf = Arrays.copyOf(buf, fromServer); // Process data
            ConnectPacket connectPacket2 = ConnectPacket.parseFrom(buf); // Parse data

            System.out.println(connectPacket2);

            //closing the socket of the client
            // lobby.server.close();

        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Cannot find (or disconnected from) Server");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
        }
    }

    void Chat(String message){
        try{
            ChatPacket chatPacket = 
                ChatPacket.newBuilder()
                    .setType(PacketType.CHAT)
                    .setMessage(message)
                    .setPlayer(this.player)
                    .build();
            
            // Send to server
            this.lobby.out.write(chatPacket.toByteArray());

            byte[] buf = new byte[1024]; // Create storage
            int fromServer = this.lobby.in.read(buf); // Get reply from server

            buf = Arrays.copyOf(buf, fromServer); // Process data
            ChatPacket chatPacket2 = ChatPacket.parseFrom(buf); // Parse data

            System.out.print(chatPacket2.getPlayer().getName() + ": ");
            System.out.println(chatPacket2.getMessage());

            //closing the socket of the client
            // this.lobby.server.close();

        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Cannot find (or disconnected from) Server");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
        }
    }
}