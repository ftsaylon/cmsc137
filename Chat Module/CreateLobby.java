import proto.TcpPacketProtos.TcpPacket.*;
import java.io.*;
import java.net.*;
import java.util.*;

class CreateLobby{
    public static void main(String[] args) {
        try{
            String serverName = "202.92.144.45";
            int port = 80;
            String message = "Yes!";

            /* Open a ClientSocket and connect to ServerSocket */
            System.out.println("Connecting to " + serverName + " on port " + port);

            //creating a new socket for client and binding it to a port
            Socket server = new Socket(serverName, port);

            CreateLobbyPacket lobbyPacket = 
                CreateLobbyPacket.newBuilder()
                    .setType(PacketType.CREATE_LOBBY)
                    .setMaxPlayers(3)
                    .build();   

            /* Receive data from the ServerSocket */
            InputStream inFromServer = server.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            

            /* Send data to the ServerSocket */
            OutputStream outToServer = server.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            // Send to server
            out.write(lobbyPacket.toByteArray());

            byte[] buf = new byte[1024]; // Create storage
            int fromServer = in.read(buf); // Get reply from server

            buf = Arrays.copyOf(buf, fromServer); // Process data
            CreateLobbyPacket lobbyPacket2 = CreateLobbyPacket.parseFrom(buf); // Parse data

            String lobbyId = lobbyPacket2.getLobbyId(); // Get lobby ID

            System.out.println(lobbyId);

            //closing the socket of the client
            server.close();

        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Cannot find (or disconnected from) Server");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
        }
    }
}