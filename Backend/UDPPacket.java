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

    Character createCharacter(String characterName, String color, Integer Id, Integer lives, Integer size, Integer xPos, Integer yPos, Integer prevXPos, Integer prevYPos, Integer score){
        Character character = 
            Character.newBuilder()
                .setType(PacketType.CHARACTER)
                .setName(characterName)
                .setColor(color)
                .setId(Id)
                .setLives(lives)
                .setSize(size)
                .setXPos(xPos)
                .setYPos(yPos)
                .setPrevXPos(prevXPos)
                .setPrevYPos(prevYPos)
                .setScore(score)
                .build();
        
        return character;
    }

    Player createPlayer(String playerName, String ip_address, Character character, Integer clientPort){
        Player player = 
            Player.newBuilder()
                .setType(PacketType.PLAYER)
                .setName(playerName)
                .setId(character.getId())
                .setCharacter(character)
                .setPort(clientPort)
                .setIpAddress(ip_address)
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

    Player parseToPlayer(byte[] buf){
        Player playerPacket = null;
        try{
            playerPacket = Player.parseFrom(buf);
        }catch(Exception ioe){
            ioe.printStackTrace();
        }
        return playerPacket;
    }

    GameState parseToGameState(byte[] buf){
        GameState gameState = null;
        try{
            gameState = GameState.parseFrom(buf);
        }catch(Exception ioe){
            ioe.printStackTrace();
        }
        return gameState;
    }

    void send(byte[] buf, InetAddress ip) {
        try {
            DatagramPacket datagramPacket = new DatagramPacket(
                buf, 
                buf.length,
                ip,
                PORT
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
                InetAddress.getByName(player.getIpAddress()),
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