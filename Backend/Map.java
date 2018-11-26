import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Map{
	private String[][] boardLayout;
	static final int board_length = 31;
	static final int board_width = 28; 
	public Map(int numberOfPlayers){
		String file = "";
		switch(numberOfPlayers){
			case 3:
				file = "maps/map3.txt";
				break;
			case 4:
				file = "maps/map4.txt";
				break;
			case 5:
				file = "maps/map5.txt";
				break;
		}
		this.boardLayout = inputFile(file);
	}

	public String[][] inputFile(String file){
		String[][] board = new String[board_length][board_width];
		try{
			BufferedReader buffer = new BufferedReader(new FileReader(file));
			String line ;
			for (int count = 0; count < 31; count ++){
				board[count] = buffer.readLine().split(" ");
			}
		} catch(FileNotFoundException e){
			System.out.println("File not found");
		} catch(IOException e){
			System.out.println(e.getMessage());
		}
		return board;
	}

    
    public String[][] getBoardLayout(){
		return this.boardLayout;
	}
}
