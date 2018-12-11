package pacman.game;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;


public class Ghost{
	private String name;
	private int xPos, yPos, prevXPos, prevYPos;
	private String color;
	private PacmanClient game;
	private int lives;
	static final char UP = 'U';
	static final char DOWN = 'D';
	static final char LEFT = 'L';
	static final char RIGHT = 'R';
	public Ghost(String name, int xPos, int yPos, String color, PacmanClient pg){
		this.name = name;
		this.xPos = xPos;
		this.yPos = yPos;
		this.prevXPos = xPos;
		this.prevYPos = yPos;
		this.color = color;
		this.game = pg;
		this.lives = 2;
	}
	public int getNumberOfLives(){
		return this.lives;
	}
	public void moveUp(){
		this.prevXPos = this.xPos;
		this.prevYPos = this.yPos;
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(this, UP))	{
			currBoard.moveGhost(this, UP);
			this.yPos--;
		}
	}
	public void moveDown(){
		this.prevXPos = this.xPos;
		this.prevYPos = this.yPos;
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(this, DOWN))	{
			currBoard.moveGhost(this, DOWN);
			this.yPos++;
		}

	}
	public void moveLeft(){
		this.prevXPos = this.xPos;
		this.prevYPos = this.yPos;
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(this, LEFT))	{
			currBoard.moveGhost(this, LEFT);
			this.xPos--;
		}
	}
	public void moveRight(){
		this.prevXPos = this.xPos;
		this.prevYPos = this.yPos;
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(this, RIGHT))	{
			currBoard.moveGhost(this, RIGHT);
			this.xPos++;
		}
	}
	public boolean checkIfCanEatPacman(){
		return true;
	}
	// getters
	public int getXPos(){
		return this.xPos;
	}
	public int getYPos(){
		return this.yPos;
	}
	public int getPrevXPos(){
		return this.prevXPos;
	}
	public int getPrevYPos(){
		return this.prevYPos;
	}
	

}