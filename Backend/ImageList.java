package pacman.game;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
public class ImageList{
	private HashMap<String, ImageIcon> imageList = new HashMap<String, ImageIcon>();

	public ImageList(){
		//sprites
		try{
			//normal-sized pacman
			imageList.put("pacUP", new ImageIcon(ImageIO.read(new File("images/sprites/pac-up.png"))));
			imageList.put("pacDOWN", new ImageIcon(ImageIO.read(new File("images/sprites/pac-down.png"))));
			imageList.put("pacLEFT", new ImageIcon(ImageIO.read(new File("images/sprites/pac-left.png"))));
			imageList.put("pacRIGHT", new ImageIcon(ImageIO.read(new File("images/sprites/pac-right.png"))));
			//big-sized pacman
			imageList.put("pacmanUP", new ImageIcon(ImageIO.read(new File("images/sprites/pacman-up.png"))));
			imageList.put("pacmanDOWN", new ImageIcon(ImageIO.read(new File("images/sprites/pacman-down.png"))));
			imageList.put("pacmanLEFT", new ImageIcon(ImageIO.read(new File("images/sprites/pacman-left.png"))));
			imageList.put("pacmanRIGHT", new ImageIcon(ImageIO.read(new File("images/sprites/pacman-right.png"))));
			//red ghost
			imageList.put("redUP", new ImageIcon(ImageIO.read(new File("images/sprites/red-up.png"))));
			imageList.put("redDOWN", new ImageIcon(ImageIO.read(new File("images/sprites/red-down.png"))));
			imageList.put("redLEFT", new ImageIcon(ImageIO.read(new File("images/sprites/red-left.png"))));
			imageList.put("redRIGHT", new ImageIcon(ImageIO.read(new File("images/sprites/red-right.png"))));
			//blue ghost
			imageList.put("blueUP", new ImageIcon(ImageIO.read(new File("images/sprites/blue-up.png"))));
			imageList.put("blueDOWN", new ImageIcon(ImageIO.read(new File("images/sprites/blue-down.png"))));
			imageList.put("blueLEFT", new ImageIcon(ImageIO.read(new File("images/sprites/blue-left.png"))));
			imageList.put("blueRIGHT", new ImageIcon(ImageIO.read(new File("images/sprites/blue-right.png"))));
			//pink ghost
			imageList.put("pinkUP", new ImageIcon(ImageIO.read(new File("images/sprites/pink-up.png"))));
			imageList.put("pinkDOWN", new ImageIcon(ImageIO.read(new File("images/sprites/pink-down.png"))));
			imageList.put("pinkLEFT", new ImageIcon(ImageIO.read(new File("images/sprites/pink-left.png"))));
			imageList.put("pinkRIGHT", new ImageIcon(ImageIO.read(new File("images/sprites/pink-right.png"))));
			//orange ghost
			imageList.put("orangeUP", new ImageIcon(ImageIO.read(new File("images/sprites/orange-up.png"))));
			imageList.put("orangeDOWN", new ImageIcon(ImageIO.read(new File("images/sprites/orange-down.png"))));
			imageList.put("orangeLEFT", new ImageIcon(ImageIO.read(new File("images/sprites/orange-left.png"))));
			imageList.put("orangeRIGHT", new ImageIcon(ImageIO.read(new File("images/sprites/orange-right.png"))));
			//ghost
			imageList.put("ghost", new ImageIcon(ImageIO.read(new File("images/sprites/ghost.png"))));
			//small dot
			imageList.put("smalldot", new ImageIcon(ImageIO.read(new File("images/smalldot.png"))));
			//big dot
			imageList.put("bigdot", new ImageIcon(ImageIO.read(new File("images/bigdot.png"))));
			//wall
			imageList.put("wall", new ImageIcon(ImageIO.read(new File("images/wall.png"))));
			//empty 
			imageList.put("empty", new ImageIcon(ImageIO.read(new File("images/empty.png"))));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public ImageIcon getImage(String imageName){ 
		return imageList.get(imageName);
	}
}