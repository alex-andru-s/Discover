package Interface;

import player.Player;

public interface IPlayerService {
	
/**
 * Create a new player with the provided name and selected unique ID,
 *  and update the repository.
 * 
 * @param name
 * 
 * @throws IllegalArgumentException if player is null or exists
 * 
 * @return player
 */
Player createPlayer(String name);

/**
 * Returns the player by given name
 * 
 * @param name
 * @return player
 */
Player getPlayerByName(String name);

/**
 * Check if the player exists by given name
 * 
 * @param name
 * @return whether the player exists or not
 */
boolean playerExists(String name);

/**
 * Load the player with given name
 * 
 * @param name
 * @return player
 */
Player loadPlayer(String name);

/**
 * Rename the player
 *  
 * @param player
 * @param newName
 * 
 * @throws IllegalArgumentException if player name  is null
 * @throws IllegalArgumentException if player new name  is null
 * @throws IllegalArgumentException if player new name exists
 * 
 */
void renamePlayer(Player player, String newName);

/**
 * Update the player's data in the repository
 * 
 * @param player
 * 
 * @throws IllegalArgumentException if player is not found
 * 
 */
void updatePlayerData(Player player);

/**
 * Removes the player from the repository
 * 
 * @param player
 * 
 * @throws IllegalArgumentException if player is null
 */
void removePlayer(Player player);

}
