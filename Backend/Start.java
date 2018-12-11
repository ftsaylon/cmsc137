import java.awt.Graphics;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class Start extends JPanel{
	public static void main (String[] args){
		JFrame frame = new JFrame("Game Manual");
		JTextField serverField = new JTextField(20);
		JTextField nameField = new JTextField(20);
		JTextField idField = new JTextField(20);
		JTextField lobbyField = new JTextField(20);
		
		String serverName;
		String lobbyID;
		String playerName;
		String playerID;
		
		ImageIcon startIcon = new ImageIcon("./images/startIcon.png");
		ImageIcon bgIcon = new ImageIcon("./images/bg3.png");
		ImageIcon submitIcon = new ImageIcon("./images/submitIcon.png");

		Font font = new Font("Courier", Font.PLAIN, 22);
		Border border = BorderFactory.createLineBorder(Color.ORANGE, 9);

		serverField.setText("Server Name");
		serverField.setBorder(border);
		serverField.setPreferredSize(new Dimension(70, 70));
		serverField.setFont(font);

		lobbyField.setText("Lobby ID");
		lobbyField.setBorder(border);
		lobbyField.setPreferredSize(new Dimension(70, 70));
		lobbyField.setFont(font);
		
		nameField.setText("Name");
		nameField.setBorder(border);
		nameField.setPreferredSize(new Dimension(70, 70));
		nameField.setFont(font);

		idField.setText("Player Number");
		idField.setBorder(border);
		idField.setPreferredSize(new Dimension(70, 70));
		idField.setFont(font);

		JPanel fieldPanel1 = new JPanel();
		JPanel fieldPanel2 = new JPanel();
		JPanel fieldPanel3 = new JPanel();
		JPanel fieldPanel4 = new JPanel();
		
		JLabel start = new JLabel(startIcon);
		JLabel bg = new JLabel(bgIcon);
		JLabel submit = new JLabel(submitIcon);

		JPanel regPanel1 = new JPanel();
		JPanel regPanel2 = new JPanel();
		JPanel submitPanel = new JPanel();

		regPanel1.setOpaque(true);
		regPanel1.setBackground(Color.BLACK);

		regPanel2.setOpaque(true);
		regPanel2.setBackground(Color.BLACK);

		submitPanel.setOpaque(true);
		submitPanel.setBackground(Color.BLACK);

		regPanel1.add(start);
		regPanel2.add(bg);
		submitPanel.add(submit);
		fieldPanel1.add(serverField);
		fieldPanel2.add(lobbyField);
		fieldPanel3.add(nameField);
		fieldPanel4.add(idField);

		JPanel blankPanel = new JPanel();
		JPanel totPanel = new JPanel();


		totPanel.add(regPanel1);
		totPanel.add(regPanel2);
		totPanel.add(fieldPanel1);
		totPanel.add(fieldPanel2);
		totPanel.add(fieldPanel3);
		totPanel.add(fieldPanel4);
		totPanel.add(submitPanel);

		totPanel.setOpaque(true);
		totPanel.setBackground(Color.BLACK);

		submitPanel.addMouseListener(new MouseListener(){		//action when the mouse is in subpanel2_1
			public void mouseClicked(MouseEvent e){
				serverName = serverField.getText();
				lobbyID = lobbyField.getText();
				playerName = nameField.getText();
				playerID = idField.getText();
			}
			public void mousePressed(MouseEvent e){
			
			}
			public void mouseReleased(MouseEvent e){
			
			}
			public void mouseEntered(MouseEvent e){
			
			}
			public void mouseExited(MouseEvent e){
			
			}
		});

		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setPreferredSize(new Dimension(700,700));		//sets the size of the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//causes the program to end when the frame is closed
		frame.setResizable(false);

		frame.add(totPanel);

		frame.pack();
		frame.setVisible(true);		//causes the frame to appear
	}
}