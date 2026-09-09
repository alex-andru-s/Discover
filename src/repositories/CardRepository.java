package repositories;

   import java.util.List;
   import java.util.Map;

   import com.google.gson.Gson;
   import com.google.gson.GsonBuilder;
   import com.google.gson.reflect.TypeToken;

import Interface.ICardRepository;
import cards.Card;
   import cards.CardData;

   import java.io.File;
   import java.io.FileReader;
   import java.io.FileWriter;
   import java.io.IOException;
   import java.lang.reflect.Type;
   import java.util.Collections;
   import java.util.HashMap;

public class CardRepository implements ICardRepository {
	
   private Map<Long, Card> cards;
   private final Gson gson;
   private static final String FILE_PATH = "src/resources/data"
                                               + "/cards.json";

public CardRepository(){
   cards = new HashMap<>();
   this.gson = new GsonBuilder().setPrettyPrinting().create();
   loadCardData();
   }

@Override
public Card findCard(Card card) {
   return this.cards.get(card.getCardId());
   } 

@Override
public List<Card> findAllCards(){
  return Collections.unmodifiableList(
		   this.cards.values().stream().toList());
   }

@Override
public void CreateCard(Card card) {
	cards.put(card.getCardId(), card);
	saveCardData();
   }

@Override
public void deleteCard(Card card) {
	cards.remove(card.getCardId());
	saveCardData();
   }

@Override
public void updateCard(Card card, CardData cardData) {
	this.cards.get(card.getCardId()).addCardData(cardData);
	saveCardData();
   }

private void saveCardData() {
	    	
   try{
	            
	   File file = new File(FILE_PATH);          
	   File parent = file.getParentFile();
	            
	       if(parent != null && !parent.exists())
	                   parent.mkdirs();
	                
	       try(FileWriter writer = new FileWriter(file)){     
	        	      gson.toJson(cards, writer);
	                 }
	 }catch(Exception e) {
	     e.printStackTrace();
	    }
}

private void loadCardData()  {

   File file = new File(FILE_PATH);
   if (!file.exists()) {
	    try{
	    	 file.createNewFile();
	    		      
	    	 try (FileWriter write = new FileWriter(file)){ 
	    			  write.write("{}");}
	    		
	    }catch (IOException e) {
	         e.printStackTrace();
	    }}

    try(FileReader reader =  new FileReader(file)) {
        
       Type type = new TypeToken<Map<Long, Card>>() {}
	                                              .getType();

	   Map<Long, Card>  loaded = gson.fromJson(reader, type);

	   if(loaded != null) {
		   cards.clear();
		   cards.putAll(loaded);
	    }  
	 }catch (Exception e) {
	          e.printStackTrace();
	       }
  }


}
