package pacman.game;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Board implements Constants{

	private String[][] boardLayout;
	private int totalNumberOfDots;
	private ArrayList<Ghost> ghosts;

	public Board(Map map){
		this.boardLayout = map.getBoardLayout();
		this.totalNumberOfDots = 0;
		for(int i = 0; i < BOARD_LENGTH; i++)
			for(int j = 0; j < BOARD_WIDTH; j++)
				if(this.boardLayout[i][j] == DOT || this.boardLayout[i][j] == BIGDOT)	this.totalNumberOfDots++;
	}
	
	public String[][] getBoardLayout(){
		return this.boardLayout;
	}
	public void updateBoardLayout(int x, int y, int prevX, int prevY, int player){
		if(player == 1){
			this.boardLayout[y][x] = PACMAN;
				this.boardLayout[prevY][prevX] = EMPTY;
		}
		else {
			if(this.boardLayout[y][x] == DOT || this.boardLayout[y][x] == BIGDOT) this.boardLayout[y][x] = GHOST_WITH_DOT;
			else this.boardLayout[y][x] = GHOST;
			if(this.boardLayout[prevY][prevX] ==  GHOST_WITH_DOT){
				this.boardLayout[prevY][prevX] = DOT;
				// BIG DOT TO BE ADDED!!
			}	
			else	this.boardLayout[prevY][prevX] = EMPTY;
		}


	}
	public int getPacmanXPos(){
		for(int i = 0; i < BOARD_LENGTH; i++)
			for(int j = 0; j < BOARD_WIDTH; j++)
				if(boardLayout[i][j].equals(PACMAN) || boardLayout[i][j].equals(BIGPACMAN))	return j;
		return -1;
	}

	public int getPacmanYPos(){
		for(int i = 0; i < BOARD_LENGTH; i++)
			for(int j = 0; j < BOARD_WIDTH; j++)
				if(boardLayout[i][j].equals(PACMAN) || boardLayout[i][j].equals(BIGPACMAN))	return i;
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
	public String checkNextPos(char move){
		int xPos = getPacmanXPos();
		int yPos = getPacmanYPos();
		String nextPos = "";
		switch(move){
			case UP:
				nextPos = boardLayout[yPos-1][xPos];
				break;
			case DOWN:
				nextPos = boardLayout[yPos+1][xPos];
				break;
			case RIGHT:
				nextPos = boardLayout[yPos][xPos+1];
				break;
			case LEFT:
				nextPos = boardLayout[yPos][xPos-1];
				break;	
		}
		return nextPos;
	}
	
	public boolean checkMoveIfValid(Ghost ghost, char move){
		int ghostX = ghost.getXPos();
		int ghostY = ghost.getYPos();
		if(move==UP){
			if(ghostY!=0){
				String nextPos = boardLayout[ghostY-1][ghostX];
				if(!nextPos.equals(WALL) && !nextPos.equals(OUT))	return true;
			}
		}
		else if (move==DOWN){
			if(ghostY!=BOARD_LENGTH){
				String nextPos = boardLayout[ghostY+1][ghostX];
				if(!nextPos.equals(WALL) && !nextPos.equals(OUT))	return true;
			}
		}
		else if (move==LEFT){
			if(ghostX!=0){
				String nextPos = boardLayout[ghostY][ghostX-1];
				if(!nextPos.equals(WALL) && !nextPos.equals(OUT))	return true;
			}
		}
		else if (move==RIGHT){
			if(ghostX!=BOARD_WIDTH){
				String nextPos = boardLayout[ghostY][ghostX+1];
				if(!nextPos.equals(WALL) && !nextPos.equals(OUT))	return true;
			}
		}
		return false;
	}

	public int getInitialGhostXPos(int id){
		int numGhosts = 1;
		for(int i = 0; i < BOARD_LENGTH; i++){
			for(int j = 0; j < BOARD_WIDTH; j++){
				if(boardLayout[i][j].equals(GHOST) || boardLayout[i][j].equals(GHOST_WITH_DOT))	numGhosts++;
				if(id == numGhosts)	return j;
			}
		}
		return -1;
	}
	public int getInitialGhostYPos(int id){
		int numGhosts = 1;
		for(int i = 0; i < BOARD_LENGTH; i++){
			for(int j = 0; j < BOARD_WIDTH; j++){
				if(boardLayout[i][j].equals(GHOST) || boardLayout[i][j].equals(GHOST_WITH_DOT))	numGhosts++;
				if(id == numGhosts)	return i;
			}
		}
		return -1;
	}

}