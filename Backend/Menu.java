import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Menu extends JPanel{

	public Menu(){
		
	}

	public static void main (String[] args){
		JFrame frame = new JFrame("Pac Man");

		JTextField serverField = new JTextField(20);
		JTextField nameField = new JTextField(20);
		JTextField idField = new JTextField(20);
		JTextField lobbyField = new JTextField(20);
		
		String serverName;
		String lobbyID;
		String playerName;
		String playerID;

		Font font = new Font("Courier", Font.PLAIN, 22);
		Border border = BorderFactory.createLineBorder(Color.ORANGE, 9);

		ImageIcon helpIcon = new ImageIcon("./images/helpIcon.png");
		ImageIcon startIcon = new ImageIcon("./images/startIcon.png");
		ImageIcon pacIcon =  new ImageIcon("./images/pacIcon.png");
		ImageIcon bgIcon = new ImageIcon("./images/bg3.png");
		ImageIcon manIcon = new ImageIcon("./images/man.png");
		ImageIcon submitIcon = new ImageIcon("./images/submitIcon.png");

		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setPreferredSize(new Dimension(700,700));		//sets the size of the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//causes the program to end when the frame is closed
		frame.setResizable(false);

		JPanel mainPanel = new JPanel();
		JLabel subPanel1 = new JLabel(pacIcon);
		JLabel subPanel2 = new JLabel(startIcon);
		JLabel subPanel3 = new JLabel(helpIcon);
		JLabel subPanel4 = new JLabel(bgIcon);
		JLabel manPanel = new JLabel(manIcon);

		manPanel.setVisible(false);

		JButton help = new JButton(helpIcon);

		mainPanel.add(subPanel1);
		mainPanel.add(subPanel4);
		mainPanel.add(subPanel2);
		mainPanel.add(subPanel3);
		mainPanel.add(manPanel);

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

		mainPanel.add(regPanel1);
		mainPanel.add(regPanel2);
		mainPanel.add(fieldPanel1);
		mainPanel.add(fieldPanel2);
		mainPanel.add(fieldPanel3);
		mainPanel.add(fieldPanel4);
		mainPanel.add(submitPanel);

		regPanel1.setVisible(false);
		regPanel2.setVisible(false);
		fieldPanel1.setVisible(false);
		fieldPanel2.setVisible(false);
		fieldPanel3.setVisible(false);
		fieldPanel4.setVisible(false);
		submitPanel.setVisible(false);

		mainPanel.setOpaque(true);
		mainPanel.setBackground(Color.BLACK); 

		subPanel2.addMouseListener(new MouseListener(){		//action when the mouse is in subpanel2_1
			public void mouseClicked(MouseEvent e){
				subPanel1.setVisible(false);
				subPanel2.setVisible(false);
				subPanel3.setVisible(false);
				subPanel4.setVisible(false);
				regPanel1.setVisible(true);
				regPanel2.setVisible(true);
				fieldPanel1.setVisible(true);
				fieldPanel2.setVisible(true);
				fieldPanel3.setVisible(true);
				fieldPanel4.setVisible(true);
				submitPanel.setVisible(true);
				manPanel.setVisible(false);
				// totPanel.setVisible(true);
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

		subPanel3.addMouseListener(new MouseListener(){	
			public void mouseClicked(MouseEvent e){
				subPanel1.setVisible(false);
				subPanel2.setVisible(false);
				subPanel3.setVisible(false);
				subPanel4.setVisible(false);
				regPanel1.setVisible(false);
				regPanel2.setVisible(false);
				fieldPanel1.setVisible(false);
				fieldPanel2.setVisible(false);
				fieldPanel3.setVisible(false);
				fieldPanel4.setVisible(false);
				submitPanel.setVisible(false);
				manPanel.setVisible(true);
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

		manPanel.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				subPanel1.setVisible(true);
				subPanel2.setVisible(true);
				subPanel3.setVisible(true);
				subPanel4.setVisible(true);
				regPanel1.setVisible(false);
				regPanel2.setVisible(false);
				fieldPanel1.setVisible(false);
				fieldPanel2.setVisible(false);
				fieldPanel3.setVisible(false);
				fieldPanel4.setVisible(false);
				submitPanel.setVisible(false);
				manPanel.setVisible(false);
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

		frame.add(mainPanel);
		
		frame.pack();
		frame.setVisible(true);		//causes the frame to appear
	}
}