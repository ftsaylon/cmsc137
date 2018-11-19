import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Main{
	public static void main(String[] args){
		// InputStream in = new FileInputStream("charfile.txt");
		String filename = "map.txt";
		String array[][] = new String[31][28];
		Scanner sc = new Scanner(System.in);
		int oldxPos = 0;
		int oldyPos = 0;
		int xPos = 0;
		int yPos = 0;
		int spawnX = 0;
		int spawnY = 0;
		int count = 0;
		int temp = 0;
		int score = 0;
		int total = 0;
		int i = 0;
		int j = 0;
		String move = "";
		String former = "e";
		boolean hasWon = false;
		boolean hasDied = false;

		// FileReader fp = new FileReader(filename);
		BufferedReader buffer = null;

		try{
			buffer = new BufferedReader(new FileReader(filename));
			for (count = 0; count < 31; count ++){
				array[count] = buffer.readLine().split(" ");
			}
		}
		catch (FileNotFoundException e){
			System.out.println("Something went wrong!");
		}
		catch (IOException x){
		    System.out.println("Something went wrong!");
		}
		count = 0;

		for(i = 0; i < 31; i++){
			for(j = 0; j < 28; j++){
				if (array[i][j].equals("P")){
					spawnX = oldxPos = xPos = i;
					spawnY = oldyPos = yPos = j;
				}
				else if (array[i][j].equals("o") || array[i][j].equals("o")){
					total = total + 1;
				}
			}
		}

		while (total > 0 && hasDied == false){
			for(i = 0; i < 31; i++){
				for(j = 0; j < 28; j++){
					System.out.print(array[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println("x: " + yPos);
			System.out.println("y: " + xPos);
			System.out.println("Pellets Left: " + total);
			System.out.println("Score: " + score);
			System.out.println();
			System.out.print("Move: ");
			move = sc.next();

			if (move.toLowerCase().equals("w")){
				oldyPos = yPos;
				oldxPos = xPos;
				xPos = xPos - 1;
				if (array[xPos][yPos].equals("w")){
					xPos = xPos + 1;
				}
				else if (array[xPos][yPos].equals("o")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 10;
					total = total - 1;				}
				else if (array[xPos][yPos].equals("O")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 50;
					total = total - 1;
					for (i = 0; i < 31; i++){
						for (j = 0; j < 28; j++){
							if (array[i][j].equals("G")){
								array[i][j] = "B";
							}
						}
					}
				}
				else if (array[xPos][yPos].equals("G")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					hasDied = true;
				}
				else if (array[xPos][yPos].equals("B")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 200;
				}
				else{
					array[xPos][yPos] = "P";
					former = "e";
					array[oldxPos][oldyPos] = former;
				}
			}
			else if (move.toLowerCase().equals("s")){
				oldyPos = yPos;
				oldxPos = xPos;
				xPos = xPos + 1;
				if (array[xPos][yPos].equals("w")){
					xPos = xPos - 1;
				}
				else if (array[xPos][yPos].equals("o")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 10;
					total = total - 1;				}
				else if (array[xPos][yPos].equals("O")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 50;
					total = total - 1;
					for (i = 0; i < 31; i++){
						for (j = 0; j < 28; j++){
							if (array[i][j].equals("G")){
								array[i][j] = "B";
							}
						}
					}
				}
				else if (array[xPos][yPos].equals("G")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					hasDied = true;
				}
				else if (array[xPos][yPos].equals("B")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 200;
				}
				else{
					array[xPos][yPos] = "P";
					former = "e";
					array[oldxPos][oldyPos] = former;
				}
			}
			else if (move.toLowerCase().equals("a")){
				oldyPos = yPos;
				oldxPos = xPos;
				yPos = yPos - 1;
				if (xPos == 14 && yPos < 0){
					yPos = 27;
				}
				if (array[xPos][yPos].equals("w")){
					yPos = yPos + 1;
				}
				else if (array[xPos][yPos].equals("o")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 10;
					total = total - 1;				}
				else if (array[xPos][yPos].equals("O")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 50;
					total = total - 1;
					for (i = 0; i < 31; i++){
						for (j = 0; j < 28; j++){
							if (array[i][j].equals("G")){
								array[i][j] = "B";
							}
						}
					}
				}
				else if (array[xPos][yPos].equals("G")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					hasDied = true;
				}
				else if (array[xPos][yPos].equals("B")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 200;
				}
				else{
					array[xPos][yPos] = "P";
					former = "e";
					array[oldxPos][oldyPos] = former;
				}
			}
			else if (move.toLowerCase().equals("d")){
				oldyPos = yPos;
				oldxPos = xPos;
				yPos = yPos + 1;
				if (xPos == 14 && yPos > 27){
					yPos = 0;
				}
				if (array[xPos][yPos].equals("w")){
					yPos = yPos - 1;
				}
				else if (array[xPos][yPos].equals("o")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 10;
					total = total - 1;				}
				else if (array[xPos][yPos].equals("O")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 50;
					total = total - 1;
					for (i = 0; i < 31; i++){
						for (j = 0; j < 28; j++){
							if (array[i][j].equals("G")){
								array[i][j] = "B";
							}
						}
					}
				}
				else if (array[xPos][yPos].equals("G")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					hasDied = true;
				}
				else if (array[xPos][yPos].equals("B")){
					former = array[xPos][yPos];
					array[xPos][yPos] = "P";
					array[oldxPos][oldyPos] = "e";
					score = score + 200;
				}
				else{
					array[xPos][yPos] = "P";
					former = "e";
					array[oldxPos][oldyPos] = former;
				}
			}
		}
		if (hasDied == true){
			System.out.println("Game Over!");
		}
		else{
			System.out.println("Congratulations!");
		}
	}
}