package service;


import Interface.IPlayerService;
import player.Player;
import repositories.PlayerRepository;

public class PlayerService implements IPlayerService{

private PlayerRepository repository;

public PlayerService(PlayerRepository repository) {
    this.repository = repository; 
}

@Override
public Player createPlayer(String name) {
	 if(name == null) {
	     throw new IllegalArgumentException("The player's name is null.");
     }
     long id = getNextId();
     Player player = new Player(name, id);
    
    if(playerExists(player.getName())) {
        throw new IllegalArgumentException("The player's name exists.");
    }
    repository.CreatePlayer(player); 
    return player;
}

@Override
public Player getPlayerByName(String name) {
    return repository.findAllPlayers().stream()
            .filter(player ->
                player.getName() != null &&
                player.getName().equals(name))
            .findFirst()
            .orElse(null);                    
    }	

@Override
public boolean playerExists(String name) {
    return getPlayerByName(name) != null;
    }

@Override
public Player loadPlayer(String name) {
	 return getPlayerByName(name);
    }

@Override
public void renamePlayer(Player player, String newName) {
	if(player == null) 
        throw new IllegalArgumentException("The player's name is null.");

    if(newName == null) {
        throw new IllegalArgumentException("The player's new name is null.");
    }
    newName = newName.trim();

    Player existing = getPlayerByName(newName);

    if (existing != null &&
        existing.getId() != player.getId()) {
        throw new IllegalArgumentException("The player's name exists.");
    }

    player.setName(newName);
    repository.updatePlayerData(player);
}

@Override
public void updatePlayerData(Player player) {
	if(player == null) 
	    throw new IllegalArgumentException(
	                     "The player was not identified, update not performed.");
	
	 repository.updatePlayerData(player);
}

@Override
public void removePlayer(Player player) {
	if(player == null) 
	    throw new IllegalArgumentException(
	                     "The player was not identified, player not removed");
	 repository.deletePlayer(player);
}
		
private long getNextId() {

    long id = 0;
    for(Player player : repository.findAllPlayers()) {
        if(player.getId() > id) 
            id = player.getId();
        }
    return id + 1;
}
}
