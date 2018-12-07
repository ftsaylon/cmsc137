package pacman.game;

import packet.PlayerProtos.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The class that contains the state of the game.
 * The game state refers the current position of the players etc.
 * @author Joseph Anthony C. Hermocilla
 *
 */


public class GameState{
	UDPPacket packet;

	/**
	 * This is a map(key-value pair) of <player name,Player>
	 */
	private Map players=new HashMap();
	
	/**
	 * Simple constructor
	 *
	 */
	public GameState(){}
	
	/**
	 * Update the game state. Called when player moves
	 * @param name
	 * @param player
	 */
	public void update(String name, Player player){
		players.put(name, player);
	}
	
	/**
	 * String representation of this object. Used for data transfer
	 * over the network
	 */
	public String toString(){
		String retval="";
		for(Iterator ite=players.keySet().iterator();ite.hasNext();){
			String name=(String)ite.next();
			Player player=(Player)players.get(name);
			retval+=player.toString()+":";
		}
		return retval;
	}
	
	/**
	 * Returns the map
	 * @return
	 */
	public Map getPlayers(){
		return players;
	}
}