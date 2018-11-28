// package pacman;


public class Pacman{
	private int lives;
	private int size; 
	private int xPos;
	private int yPos;
	static final int NORMAL = 0;
	static final int BIG = 1;
	static final char UP = 'U';
	static final char DOWN = 'D';
	static final char LEFT = 'L';
	static final char RIGHT = 'R';
	public Pacman(int xPos, int yPos){
		this.lives = 3;
		this.size = NORMAL;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void moveLeft(State currState){
		Board currBoard = currState.getBoard();
		if(currBoard.checkMoveIfValid(LEFT))	{
			currState.update(LEFT);
			this.xPos--;
		}
	}
	public void moveRight(State currState){
		Board currBoard = currState.getBoard();
		if(currBoard.checkMoveIfValid(RIGHT))	{
			currState.update(RIGHT);
			this.xPos++;
		}
	}
	public void moveUp(State currState){
		Board currBoard = currState.getBoard();
		if(currBoard.checkMoveIfValid(UP))	{
			currState.update(UP);
			this.yPos--;
		}
	}
	public void moveDown(State currState){
		Board currBoard = currState.getBoard();
		if(currBoard.checkMoveIfValid(DOWN))	{
			currState.update(DOWN);
			this.yPos++;
		}
	}
	public int getNumberOfLives(){
		return this.lives;
	}

	
}