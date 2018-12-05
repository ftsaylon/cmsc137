import src.*;
import packet.*;

import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Main {
    public static void main(String[] args) throws Exception {
        JFrame chatFrame = new JFrame("Chat");
        Client client = new Client("202.92.144.45", 80);
        chatFrame.add(client);
       	chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.pack();
        chatFrame.setLocationRelativeTo(null);
        chatFrame.setResizable(false);
        chatFrame.setVisible(true);
        System.out.println("YOW");
    }
}