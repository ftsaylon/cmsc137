import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class Manual extends JPanel{
	public static void main (String[] args){
		JFrame frame = new JFrame("Game Manual");

		ImageIcon helpIcon = new ImageIcon("./images/helpIcon.png");
		ImageIcon startIcon = new ImageIcon("./images/startIcon.png");
		ImageIcon pacIcon =  new ImageIcon("./images/pacIcon.png");
		ImageIcon bgIcon = new ImageIcon("./images/bg3.png");
		ImageIcon manIcon = new ImageIcon("./images/man.png");

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
		
		mainPanel.setOpaque(true);
		mainPanel.setBackground(Color.BLACK); 

		subPanel3.addMouseListener(new MouseListener(){		//action when the mouse is in subpanel2_1
			public void mouseClicked(MouseEvent e){
				subPanel1.setVisible(false);
				subPanel2.setVisible(false);
				subPanel3.setVisible(false);
				subPanel4.setVisible(false);
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

		manPanel.addMouseListener(new MouseListener(){		//action when the mouse is in subpanel2_1
			public void mouseClicked(MouseEvent e){
				subPanel1.setVisible(true);
				subPanel2.setVisible(true);
				subPanel3.setVisible(true);
				subPanel4.setVisible(true);
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