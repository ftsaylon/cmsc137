package pacman.game;

public class Pacman implements Constants{
	private int lives;
	private int size; 
	private int xPos;
	private int yPos;
	private int score;
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

	public int getNumberOfLives(){
		return this.lives;
	}
	public int getPrevXPos(String move){
		if(move == MOVE_UP || move == MOVE_DOWN)	return this.xPos;
		else if(move == MOVE_RIGHT)	return this.xPos - 1;
		else return this.xPos + 1;
	}
	public int getPrevYPos(String move){
		if(move == MOVE_RIGHT || move == MOVE_LEFT)	return this.yPos;
		else if(move == MOVE_UP)	return this.yPos + 1;
		else return this.yPos - 1;
	}

	
}