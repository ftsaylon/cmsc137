package src;

import packet.PlayerProtos.*;
import packet.TcpPacketProtos.TcpPacket.*;

import java.net.Socket;
import java.util.Scanner;

import java.io.IOException;
import java.net.UnknownHostException;

public class Client {
    int choice;
    String lobbyId;

    public Client(String serverName, int port) {
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

                ChatSender chatSender = new ChatSender(packet, user, lobbyId);
                ChatReceiver chatReceiver = new ChatReceiver(packet, user, lobbyId);
                
                Thread sender = new Thread(chatSender);
                Thread receiver = new Thread(chatReceiver);

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
}