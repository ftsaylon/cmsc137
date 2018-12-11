import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class Manual extends JPanel{
	public static void main (String[] args){
		JFrame frame = new JFrame("Game Manual");

		ImageIcon helpIcon = new ImageIcon("./images/helpIcon.png");

		frame.setPreferredSize(new Dimension(500,500));		//sets the size of the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//causes the program to end when the frame is closed
		frame.setResizable(false);

		JPanel mainPanel = new JPanel();
		JPanel subPanel1 = new JPanel();

		JButton help = new JButton(helpIcon);

		help.setPreferredSize(new Dimension(50, 50));
		// subPanel1.add(help);

		mainPanel.add(help);

		frame.add(mainPanel);

		frame.pack();
		frame.setVisible(true);		//causes the frame to appear
	}
}