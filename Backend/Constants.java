package pacman.game;
public interface Constants {
	/**
	 * Game port
	 */
	public static final int PORT=50000;

	/**
	 * Game states.
	 */
	public static final int GAME_START=0;
	public static final int IN_PROGRESS=1;
	public final int GAME_END=2;
	public final int WAITING_FOR_PLAYERS=3;

	public static final int GAME_OVER = 1;
	public static final int NOT_OVER = 0;
	public static final int NORMAL_PACMAN = 0;
	public static final int BIG_PACMAN = 1;
	public static final char UP = 'U';
	public static final char DOWN = 'D';
	public static final char LEFT = 'L';
	public static final char RIGHT = 'R';
	public static final String MOVE_UP = "UP";
	public static final String MOVE_DOWN = "DOWN";
	public static final String MOVE_LEFT = "LEFT";
	public static final String MOVE_RIGHT = "RIGHT";
	// board size
	public static final int BOARD_WIDTH = 28;
	public static final int BOARD_LENGTH = 31;

	public static final int FRAME_LENGTH = 950;
	public static final int FRAME_WIDTH = 800;
	//text file
	public static final String PACMAN = "p";
	public static final String BIGPACMAN = "P";
	public static final String DOT = "o";
	public static final String BIGDOT = "O";
	public static final String EMPTY = "e";
	public static final String WALL = "w";
	public static final String OUT = "x";
	public static final String GHOST = "g";
	public static final String GHOST_WITH_DOT = "G";
	public static final String BLACK_GHOST = "b";
	public static final String BLACK_GHOST_WITH_DOT = "B";

	public static final int HIGHEST_POSSIBLE_SCORE = 5040;

}