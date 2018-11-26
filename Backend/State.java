import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class State{
	private Board currentBoard;
	private Pacman pacman;
	private int gameStatus;
	private int pacmanXPos;
	private int pacmanYPos;
	private int numberOfDots;
	static final int GAME_OVER = 1;
	static final int NOT_OVER = 0;

	public State(Board board, Pacman pacman){
		this.currentBoard = board;
		this.gameStatus = NOT_OVER;
		this.pacman = pacman;
	}
	public Board getBoard(){
		return this.currentBoard;
	}
	public Pacman getPacman(){
		return this.pacman;
	}
	public void update(char move){
		this.currentBoard.movePacman(move);
		this.gameStatus = (checkIfOver()) ? GAME_OVER : NOT_OVER; 
	}
	public int getGameStatus(){
		return this.gameStatus;
	}
	public void updateGhost(Ghost ghost, char move){
		this.currentBoard.moveGhost(ghost, move);
		this.gameStatus = (checkIfOver()) ? GAME_OVER : NOT_OVER; 
	}

	public boolean checkIfOver(){

		if (currentBoard.getNumberOfDots()==0)	return true;
		else if(pacman.getNumberOfLives()==0)	return true;
		else return false; 
	}
}