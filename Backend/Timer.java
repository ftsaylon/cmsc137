package pacman.game;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class Timer extends JPanel{
    public static void main(String[] args) throws InterruptedException{
        JFrame timerFrame = new JFrame("Timer");
        JLabel timeLeft = new JLabel();
        int timet = 1 * 60;
        long delay = timet * 1000;
        String time = "3:00";

        timeLeft.setFont(new java.awt.Font("Tahoma", 0, 18));
        timeLeft.setText(time);

        timerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        timerFrame.setPreferredSize(new Dimension(80, 80));
        timerFrame.add(timeLeft);
        timerFrame.setResizable(false);
        timerFrame.pack();
        timerFrame.setVisible(true);

        do{
            int minutes = timet / 60;
            int seconds = timet % 60;
            time = Integer.toString(minutes) + ":" + String.format("%02d", seconds);
        
            timeLeft.setText(time);

            Thread.sleep(1000);
            timet = timet - 1;
            delay = delay - 1000;

            if (timet == 0){
            	timeLeft.setText("Game Over");
            }

        }
        while (delay != 0);
    }
}