package pacman.game;

// import packet.PlayerProtos.*;
// import packet.CharacterProtos.Character;
import packet.UdpPacketProtos.UdpPacket.Player;
import packet.UdpPacketProtos.UdpPacket.Character;
import packet.UdpPacketProtos.UdpPacket.GameState;
import packet.UdpPacketProtos.UdpPacket.PacketType;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.*;
import java.util.Arrays;
import java.util.List;

class UDPPacket implements Constants {
    private DatagramSocket socket;

    void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    Character createCharacter(String characterName, Integer Id, Integer lives, Integer size, Integer xPos, Integer yPos){
        Character character = 
            Character.newBuilder()
                .setType(PacketType.CHARACTER)
                .setName(characterName)
                .setId(Id)
                .setLives(lives)
                .setSize(size)
                .setXPos(xPos)
                .setYPos(yPos)
                .build();
        
        return character;
    }

    Player createPlayer(String playerName, Character character, Integer clientPort){
        Player player = 
            Player.newBuilder()
                .setType(PacketType.PLAYER)
                .setName(playerName)
                .setId(character.getId())
                .setCharacter(character)
                .setPort(clientPort)
                .build();
        
        return player;
    }

    GameState createGameState(Player player){
        GameState gameState = null;
            if(player != null){
                gameState = 
                    GameState.newBuilder()
                        .setType(PacketType.GAMESTATE)
                        .addPlayerList(player)
                        .build();
            }else{
                gameState = 
                    GameState.newBuilder()
                        .setType(PacketType.GAMESTATE)
                        .build();
            }
        
        return gameState;
    }

    GameState addPlayerToGame(GameState gameState1, Player player){
        GameState gameState2 = null;
        if(player != null){
            gameState2 = 
                GameState.newBuilder()
                    .setType(gameState1.getType())
                    .addPlayerList(player)
                    .mergeFrom(gameState1)
                    .build();
        }
        return gameState2;
    }


    void send(byte[] buf) {
        try {
            DatagramPacket datagramPacket = new DatagramPacket(
                buf, 
                buf.length,
                InetAddress.getLocalHost(),
                PORT
            );

            System.out.println(datagramPacket.getPort());

            socket.send(datagramPacket);
            
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    byte[] receive() {
        byte[] buf = new byte[65507];
        try {
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
            socket.receive(datagramPacket);
            buf = new byte[datagramPacket.getLength()];
            System.arraycopy(datagramPacket.getData(), datagramPacket.getOffset(), buf, 0, datagramPacket.getLength());

        } catch(Exception ioe){
            ioe.printStackTrace();
		}
        
        return buf;
    }

    void sendToClient(Player player, byte[] buf) {
        try {
            DatagramPacket datagramPacket = new DatagramPacket(
                buf, 
                buf.length,
                InetAddress.getLocalHost(),
                player.getPort()
            );

            socket.send(datagramPacket);
            
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}