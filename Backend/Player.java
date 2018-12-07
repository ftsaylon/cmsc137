package pacman.game;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Player{
	private int xPos, yPos;
	private PacmanClient game;

	public Player(int xPos, int yPos, PacmanClient pg){
		this.xPos = xPos;
		this.yPos = yPos;
		game = pg;
	}

	public void moveLeft(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(LEFT))	{
			this.xPos--;
			currBoard.movePacman(LEFT);
		}
		String next = currBoard.checkNextPos(LEFT);
		if(next == DOT){
			this.score += 20;
		}
		else if (next == BIGDOT){
			this.score += 50;
			this.size = BIG_PACMAN;
		}
	}

	public void moveRight(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(RIGHT))	{
			currBoard.movePacman(RIGHT);
			this.xPos++;
		}
		String next = currBoard.checkNextPos(RIGHT);
		if(next == DOT){
			this.score += 20;
		}
		else if (next == BIGDOT){
			this.score += 50;
			this.size = BIG_PACMAN;
		}
	}

	public void moveUp(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(UP))	{
			currBoard.movePacman(UP);
			this.yPos--;
		}
		String next = currBoard.checkNextPos(UP);
		if(next == DOT){
			this.score += 20;
		}
		else if (next == BIGDOT){
			this.score += 50;
			this.size = BIG_PACMAN;
		}
	}

	public void moveDown(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(DOWN))	{
			currBoard.movePacman(DOWN);
			this.yPos++;
		}
		String next = currBoard.checkNextPos(DOWN);
		if(next == DOT){
			this.score += 20;
		}
		else if (next == BIGDOT){
			this.score += 50;
			this.size = BIG_PACMAN;
		}
	}

	// Getters
	public int getSize(){
		return this.size;
	}

	public int getScore(){
		return this.score;
	}

	public int getXPos(){
		return this.xPos;
	}

	public int getYPos(){
		return this.yPos;
	}
}