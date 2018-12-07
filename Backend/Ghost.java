package pacman.game;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;


public class Ghost{
	private String name;
	private int xPos, yPos;
	private String color;
	private PacmanClient game;
	static final char UP = 'U';
	static final char DOWN = 'D';
	static final char LEFT = 'L';
	static final char RIGHT = 'R';
	public Ghost(String name, int xPos, int yPos, String color, PacmanClient pg){
		this.name = name;
		this.xPos = xPos;
		this.yPos = yPos;
		this.color = color;
		this.game = pg;
	}

	public void moveUp(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(this, UP))	{
			currBoard.moveGhost(this, UP);
			this.yPos--;
		}
	}
	public void moveDown(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(this, DOWN))	{
			currBoard.moveGhost(this, DOWN);
			this.yPos++;
		}
	}
	public void moveLeft(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(this, LEFT))	{
			currBoard.moveGhost(this, LEFT);
			this.xPos--;
		}
	}
	public void moveRIGHT(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(this, RIGHT))	{
			currBoard.moveGhost(this, RIGHT);
			this.xPos++;
		}
	}
	public int getXPos(){
		return this.xPos;
	}
	public int getYPos(){
		return this.yPos;
	}


}