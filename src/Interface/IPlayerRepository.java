package Interface;

import java.util.List;

import player.Player;

public interface IPlayerRepository {
	
/**
 * Returns a list of all existing players.
 * 
 * @return List<> 
*/
List<Player> findAllPlayers();

/**
 *Create a new player with a unique ID.
 *
 * @param player
*/
void CreatePlayer(Player player);

/**
 *Delete a player using the player's unique ID.
 *
 * @param player
*/
void deletePlayer(Player player);

/**
 *Update the data for the specified player.
 *
 * @param player
*/
void updatePlayerData(Player player);

}
