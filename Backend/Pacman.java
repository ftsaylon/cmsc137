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

	public Pacman(int xPos, int yPos, int lives, int size){
		this.lives = 3;
		this.size = NORMAL_PACMAN;
		this.xPos = xPos;
		this.yPos = yPos;
		this.score = 0;
	}

	public void pacmanDies(){
		this.xPos = this.game.getGameBoard().getPacmanX();
		this.yPos = this.game.getGameBoard().getPacmanY();
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
			String next = currBoard.checkNextPos(LEFT);
			if(next.equals(DOT)){
				this.score += 20;
			}
			else if (next.equals(BIGDOT)){
				this.score += 50;
				this.size = BIG_PACMAN;
			}
			this.xPos--;
			currBoard.movePacman(LEFT);
		}
	}

	public void moveRight(){
		this.prevXPos = this.xPos;
		this.prevYPos = this.yPos;
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(RIGHT))	{
			String next = currBoard.checkNextPos(RIGHT);
			if(next.equals(DOT)){
				this.score += 20;
			}
			else if (next.equals(BIGDOT)){
				this.score += 50;
				this.size = BIG_PACMAN;
			}
			currBoard.movePacman(RIGHT);
			this.xPos++;
		}
	}
	public void dead(){
		this.lives--;
		this.xPos = this.game.getGameBoard().getPacmanX();
		this.yPos = this.game.getGameBoard().getPacmanY();
	}
	public void moveUp(){
		this.prevXPos = this.xPos;
		this.prevYPos = this.yPos;
		Board currBoard = this.game.getGameBoard();
		if(currBoard.checkMoveIfValid(UP))	{
			String next = currBoard.checkNextPos(UP);
			if(next.equals(DOT)){
				this.score += 20;
			}
			else if (next.equals(BIGDOT)){
				this.score += 50;
				this.size = BIG_PACMAN;
			}
			currBoard.movePacman(UP);
			this.yPos--;
		}
		
	}

	public void moveDown(){
		Board currBoard = this.game.getGameBoard();
		this.prevXPos = this.xPos;
		this.prevYPos = this.yPos;
		if(currBoard.checkMoveIfValid(DOWN))	{
			String next = currBoard.checkNextPos(DOWN);
			if(next.equals(DOT)){
				this.score += 20;
			}
			else if (next.equals(BIGDOT)){
				this.score += 50;
				this.size = BIG_PACMAN;
			}
			currBoard.movePacman(DOWN);
			this.yPos++;
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