package pacman.game;

public class Pacman implements Constants{
	private int lives;
	private int size; 
	private int xPos;
	private int yPos;
	private int score;
	private int prevXPos;
	private int prevYPos;
	public static final int NORMAL_PACMAN = 0;
	public static final int BIG_PACMAN = 1;
	private PacmanClient game;
	
	public Pacman(int xPos, int yPos, PacmanClient pg){
		this.lives = 3;
		this.size = NORMAL_PACMAN;
		this.xPos = xPos;
		this.yPos = yPos;
		this.game = pg;
		this.score = 0;
	}


	public void pacmanDies(){
		this.game.pacmanRespawn();
	}

	public void transformBack(){
		this.size = NORMAL_PACMAN;
	}
	public void moveLeft(){
		this.prevXPos = this.xPos;
		this.prevYPos = this.yPos;
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
		this.prevXPos = this.xPos;
		this.prevYPos = this.yPos;
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
		this.prevXPos = this.xPos;
		this.prevYPos = this.yPos;
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
		this.prevXPos = this.xPos;
		this.prevYPos = this.yPos;
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

	public int getNumberOfLives(){
		return this.lives;
	}
	public int getPrevXPos(){
		return this.prevXPos;
	}
	public int getPrevYPos(){
		return this.prevYPos;
	}

	
}