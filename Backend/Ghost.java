import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Ghost{
	private String name;
	private int xPos, yPos;
	static final char UP = 'U';
	static final char DOWN = 'D';
	static final char LEFT = 'L';
	static final char RIGHT = 'R';
	public Ghost(String name, int xPos, int yPos){
		this.name = name;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void moveUp(State currState){
		Board currBoard = currState.getBoard();
		if(currBoard.checkMoveIfValid(this, UP))	{
			currState.updateGhost(this, UP);
			this.yPos--;
		}
	}
	public void moveDown(State currState){
		Board currBoard = currState.getBoard();
		if(currBoard.checkMoveIfValid(this, DOWN))	{
			currState.updateGhost(this, DOWN);
			this.yPos++;
		}
	}
	public void moveLeft(State currState){
		Board currBoard = currState.getBoard();
		if(currBoard.checkMoveIfValid(this, LEFT))	{
			currState.updateGhost(this, LEFT);
			this.xPos--;
		}
	}
	public void moveRIGHT(State currState){
		Board currBoard = currState.getBoard();
		if(currBoard.checkMoveIfValid(this, RIGHT))	{
			currState.updateGhost(this, RIGHT);
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