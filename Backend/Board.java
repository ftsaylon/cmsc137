package pacman.game;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Board {
	//extends JPanel
	// final ImageIcon wall = new ImageIcon(getClass().getResource("/images/bluewall.jpg"));
	// final ImageIcon dot = new ImageIcon(getClass().getResource("/images/coin.png"));
	// final ImageIcon empty = new ImageIcon(getClass().getResource("/images/empty.jpg"));
	// final ImageIcon ghost = new ImageIcon(getClass().getResource("/images/pacman-down.png"));
	// final ImageIcon pacman = new ImageIcon(getClass().getResource("/images/pacman-right.png")); 
	// public Board(Map map){
	// 	setOpaque(true);
	// 	setBackground(Color.BLACK);
	// 	setLayout(new GridLayout(31, 28));
	// 	setBoard(map.getBoardLayout());
	// 	revalidate();
	// 	repaint();
	// }

	// public void setBoard(String[][] boardLayout){
	// 	for(int i = 0; i < 31; i++){
	// 		for(int j = 0; j < 28; j++){
	// 			switch(boardLayout[i][j]){
	// 				case "w":
	// 					this.add(new JLabel(wall));
	// 					break;
	// 				case "o":
	// 					this.add(new JLabel(dot));
	// 					break;
	// 				case "e":
	// 					this.add(new JLabel(empty));
	// 					break;
	// 				case "x":
	// 					this.add(new JLabel(empty));
	// 					break;
	// 				case "G":
	// 					this.add(new JLabel(ghost));
	// 					break;
	// 				case "P":
	// 					this.add(new JLabel(pacman));
	// 					break;
	// 				}
	// 		}
	// 	}
	// }
	// public static void main(String[] args) {
	// 	JFrame pacman = new JFrame("pacman Game");
	// 	pacman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// 	pacman.setPreferredSize(new Dimension(840, 910));
	// 	pacman.setResizable(false);
	// 	pacman.add(new Board(new Map(3)));
	// 	pacman.pack();
	// 	pacman.setVisible(true);
	// }

	private String[][] boardLayout;
	private int totalNumberOfDots;
	private ArrayList<Ghost> ghosts;
	// private Pacman pacman;
	static final int BOARD_LENGTH = 31;
	static final int BOARD_WIDTH = 28; 
	static final char UP = 'U';
	static final char DOWN = 'D';
	static final char LEFT = 'L';
	static final char RIGHT = 'R';
	static final String PACMAN = "P";
	static final String DOT = "o";
	static final String BIGDOT = "O";
	static final String EMPTY = "e";
	static final String WALL = "w";
	static final String OUT = "x";
	static final String GHOST = "G";
	public Board(Map map){
		this.boardLayout = map.getBoardLayout();
		this.totalNumberOfDots = 0;
		// this.pacman = pacman;
		// this.
		for(int i = 0; i < BOARD_LENGTH; i++)
			for(int j = 0; j < BOARD_WIDTH; j++)
				if(this.boardLayout[i][j] == DOT || this.boardLayout[i][j] == BIGDOT)	this.totalNumberOfDots++;
	}
	public String[][] getBoardLayout(){
		return this.boardLayout;
	}
	public int getPacmanXPos(){
		for(int i = 0; i < BOARD_LENGTH; i++)
			for(int j = 0; j < BOARD_WIDTH; j++)
				if(boardLayout[i][j].equals(PACMAN))	return j;
		return -1;
	}

	public int getPacmanYPos(){
		for(int i = 0; i < BOARD_LENGTH; i++)
			for(int j = 0; j < BOARD_WIDTH; j++)
				if(boardLayout[i][j].equals(PACMAN))	return i;
		return -1;
	}

	public int getNumberOfDots(){
		int count = 0;
		for(int i = 0; i < BOARD_LENGTH; i++)
			for(int j = 0; j < BOARD_WIDTH; j++)
				if(this.boardLayout[i][j].equals(DOT) || this.boardLayout[i][j].equals(BIGDOT))	count++;
		return count;
	}

	public void movePacman(char move){
		int pacmanX = getPacmanXPos();
		int pacmanY = getPacmanYPos();
		if(move == UP){
			boardLayout[pacmanY][pacmanX] = EMPTY;
			boardLayout[pacmanY-1][pacmanX] = PACMAN;
		}
		else if(move == DOWN){
			boardLayout[pacmanY][pacmanX] = EMPTY;
			boardLayout[pacmanY+1][pacmanX] = PACMAN;
		}
		else if(move == LEFT){
			boardLayout[pacmanY][pacmanX] = EMPTY;
			boardLayout[pacmanY][pacmanX-1] = PACMAN;
		}
		else if(move == RIGHT){
			boardLayout[pacmanY][pacmanX] = EMPTY;
			boardLayout[pacmanY][pacmanX+1] = PACMAN;
		}

	}
	public void moveGhost(Ghost ghost, char move){
		int ghostX = ghost.getXPos();
		int ghostY = ghost.getYPos();
		if(move == UP){
			boardLayout[ghostY][ghostX] = EMPTY;
			boardLayout[ghostY-1][ghostX] = GHOST;
		}
		else if(move == DOWN){
			boardLayout[ghostY][ghostX] = EMPTY;
			boardLayout[ghostY+1][ghostX] = GHOST;
		}
		else if(move == LEFT){
			boardLayout[ghostY][ghostX] = EMPTY;
			boardLayout[ghostY][ghostX-1] = GHOST;
		}
		else if(move == RIGHT){
			boardLayout[ghostY][ghostX] = EMPTY;
			boardLayout[ghostY][ghostX+1] = GHOST;
		}
	}
	public boolean checkMoveIfValid(char move){
		int pacmanX = getPacmanXPos();
		int pacmanY = getPacmanYPos();
		if(move==UP){
			if(pacmanY!=0){
				String nextPos = boardLayout[pacmanY-1][pacmanX];
				System.out.println(nextPos);
				if(nextPos.equals(DOT) || nextPos.equals(EMPTY) || nextPos.equals(BIGDOT))	return true;
			}

			
		}
		else if (move==DOWN){
			if(pacmanY!=BOARD_LENGTH){
				String nextPos = boardLayout[pacmanY+1][pacmanX];
				if(nextPos.equals(DOT) || nextPos.equals(EMPTY) || nextPos.equals(BIGDOT))	return true;
			}
		}
		else if (move==LEFT){
			if(pacmanX!=0){
				String nextPos = boardLayout[pacmanY][pacmanX-1];
				if(nextPos.equals(DOT) || nextPos.equals(EMPTY) || nextPos.equals(BIGDOT))	return true;
			}
		}
		else if (move==RIGHT){
			if(pacmanX!=BOARD_WIDTH){
				String nextPos = boardLayout[pacmanY][pacmanX+1];
				if(nextPos.equals(DOT) || nextPos.equals(EMPTY) || nextPos.equals(BIGDOT))	return true;
			}
		}
		return false;
	}
	public boolean checkMoveIfValid(Ghost ghost, char move){
		int ghostX = ghost.getXPos();
		int ghostY = ghost.getYPos();
		if(move==UP){
			if(ghostY!=0){
				String nextPos = boardLayout[ghostX][ghostY-1];
				if(!nextPos.equals(WALL) && !nextPos.equals(OUT))	return true;
			}
		}
		else if (move==DOWN){
			if(ghostY!=BOARD_LENGTH){
				String nextPos = boardLayout[ghostX][ghostY+1];
				if(!nextPos.equals(WALL) && !nextPos.equals(OUT))	return true;
			}
		}
		else if (move==LEFT){
			if(ghostX!=0){
				String nextPos = boardLayout[ghostX-1][ghostY];
				if(!nextPos.equals(WALL) && !nextPos.equals(OUT))	return true;
			}
		}
		else if (move==RIGHT){
			if(ghostX!=BOARD_WIDTH){
				String nextPos = boardLayout[ghostX+1][ghostY];
				if(!nextPos.equals(WALL) && !nextPos.equals(OUT))	return true;
			}
		}
		return false;
	}


}