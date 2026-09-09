package repositories;

import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Interface.IPlayerRepository;
import player.Player;

public class PlayerRepository implements IPlayerRepository {
	
     private final Map<Long, Player> players;

     private final Gson gson;
     private static final String FILE_PATH = 
    		                     "src/resources/data/players.json";
            
public PlayerRepository(){
	
    this.players = new HashMap<>();
    gson = new GsonBuilder().setPrettyPrinting().create();
    loadPlayerData();
}

@Override
public List<Player> findAllPlayers() {
    return Collections.unmodifiableList(
    		this.players.values().stream().toList());
}

@Override
public void CreatePlayer(Player player)  {
	players.put(player.getId(), player);
	savePlayerData();
}

@Override
public void deletePlayer(Player player) {
	 players.remove(player.getId());
	 savePlayerData();
}

@Override
public void updatePlayerData(Player player) {
    players.put(player.getId(), player);
    savePlayerData();
}

private void savePlayerData() {
	 try {
          File file = new File(FILE_PATH);
          File parent = file.getParentFile();
         
          if(parent != null && !parent.exists()){
             parent.mkdirs();
             }
          
      try (FileWriter writer = new FileWriter(file)){
    	  gson.toJson(players, writer);
         }

     } catch(Exception e) {

         e.printStackTrace();
     }
}

private void loadPlayerData()  {

	File file = new File(FILE_PATH);
	if (!file.exists()) {
		try {
		 file.createNewFile();
	     try (FileWriter write = new FileWriter(file)) {
			write.write("{}");
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
   }

try (FileReader reader =  new FileReader(file)) {

     Type type = new TypeToken<Map<Long, Player>>() {}
                     .getType();

     Map<Long, Player> loaded = gson.fromJson(reader, type);

      if(loaded != null) {
         players.clear();
         players.putAll(loaded);
          }
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

}
