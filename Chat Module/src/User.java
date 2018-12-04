package src;

import packet.PlayerProtos.*;
import packet.TcpPacketProtos;

import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

import java.io.IOException;
import java.net.UnknownHostException;

class User {
    private Player player;

    public User(Packet packet) {
        System.out.print("Enter Name: ");
        Scanner str = new Scanner(System.in);
        String name = str.nextLine();
        this.player = packet.createPlayer(name);
    }

    public int menu() {
        System.out.println("-------- CHAT --------");
        System.out.println("[1] Create Lobby");
        System.out.println("[2] Join Lobby");
        System.out.println("[3] Exit");
        System.out.println("----------------------");
        System.out.print(">>> ");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        return choice;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}