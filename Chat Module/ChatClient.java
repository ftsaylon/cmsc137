import proto.TcpPacketProtos.TcpPacket.*;
import proto.PlayerProtos.*;

import java.io.*;
import java.net.*;
import java.util.*;

class ChatClient{
    Player player;
    InputStream inFromServer;
    DataInputStream in;
    OutputStream outToServer;
    DataOutputStream out;
    Socket server;

    void JoinLobby(String lobbyId, String playerName){
        try{
            String serverName = "202.92.144.45";
            int port = 80;
            this.server = new Socket(serverName, port);

            this.player = 
                Player.newBuilder()
                    .setId("1")
                    .setName(playerName)
                    .build();

            ConnectPacket connectPacket = 
                ConnectPacket.newBuilder()
                    .setType(PacketType.CONNECT)
                    .setPlayer(player)
                    .setLobbyId(lobbyId)
                    .build();
            
            // Send to server
            
            /* Receive data from the ServerSocket */
            this.inFromServer = server.getInputStream();
            this.in = new DataInputStream(inFromServer);

            /* Send data to the ServerSocket */
            this.outToServer = server.getOutputStream();
            this.out = new DataOutputStream(outToServer);
            
            this.out.write(connectPacket.toByteArray());

            byte[] buf = new byte[1024]; // Create storage
            int fromServer = this.in.read(buf); // Get reply from server

            buf = Arrays.copyOf(buf, fromServer); // Process data
            ConnectPacket connectPacket2 = ConnectPacket.parseFrom(buf); // Parse data

            System.out.println("Successfully entered lobby " + lobbyId);
            // System.out.println(connectPacket2);

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
            this.out.write(chatPacket.toByteArray());

            byte[] buf = new byte[1024]; // Create storage
            int fromServer = this.in.read(buf); // Get reply from server

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