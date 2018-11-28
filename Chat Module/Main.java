import proto.TcpPacketProtos.TcpPacket.*;
import java.io.*;
import java.net.*;
import java.util.*;

class Main{
    public static void main(String[] args) {
        Lobby lobby = new Lobby();
        lobby.CreateLobby();
        
        ChatClient chat_client = new ChatClient();
        
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        

        while(choice != 4){
            System.out.println("[1] Join Lobby");
            System.out.println("[2] Chat");
            System.out.println("[3] Disconnect");
            System.out.println("[4] Exit");
            System.out.print(">>> ");

            choice = sc.nextInt();

            switch(choice){
                case 1:
                    String inputLobbyId;
                    System.out.print("Enter Lobby ID: ");
                    inputLobbyId = sc.next();

                    String playerName;
                    System.out.print("Enter Player Name: ");
                    playerName = sc.next();

                    chat_client.JoinLobby(inputLobbyId, playerName);               
                    break;
                case 2: 
                    String message;
                    System.out.print("Enter Message: ");
                    message = sc.next();
                    chat_client.Chat(message);
                    break;
                case 3: 
                    break;
                case 4:
                    try{
                        lobby.server.close();
                    }catch(IOException e){
                        e.printStackTrace();
                        System.out.println("Cannot find (or disconnected from) Server");
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
                    }
                    break;                
            } 
        }

        sc.close();
    }
}