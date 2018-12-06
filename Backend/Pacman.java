package pacman.game;

public class Pacman implements Constants{
	private int lives;
	private int size; 
	private int xPos;
	private int yPos;
	public static final int NORMAL_PACMAN = 0;
	public static final int BIG_PACMAN = 1;
	private PacmanClient game;
	// public Pacman(int xPos, int yPos, pacmanGame pg){
	public Pacman(int xPos, int yPos, PacmanClient pg){
		this.lives = 3;
		this.size = NORMAL_PACMAN;
		this.xPos = xPos;
		this.yPos = yPos;
		this.game = pg;
	}
	public void pacmanDies(){
		this.game.pacmanRespawn();
	}
	public void moveLeft(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(LEFT))	{
			this.xPos--;
			currBoard.movePacman(LEFT);
		}
	}
	public void moveRight(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(RIGHT))	{
			currBoard.movePacman(RIGHT);
			this.xPos++;
		}
	}
	public void moveUp(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(UP))	{
			currBoard.movePacman(UP);
			this.yPos--;
		}
	}
	public void moveDown(){
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(DOWN))	{
			currBoard.movePacman(DOWN);
			this.yPos++;
		}
	}
	public int getNumberOfLives(){
		return this.lives;
	}

	
}