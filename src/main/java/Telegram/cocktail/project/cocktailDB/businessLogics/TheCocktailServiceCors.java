package Telegram.cocktail.project.cocktailDB.businessLogics;

import Telegram.cocktail.project.cocktailDB.entities.DrinkDTO;
import Telegram.cocktail.project.cocktailDB.entities.ListOfDrinksDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static jdk.javadoc.internal.tool.Main.execute;

@Service
public class TheCocktailServiceCors {
    public static List<DrinkDTO> getCocktailByName(String cocktailName) throws Exception {
    String apiString = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + cocktailName;
    RestTemplate template = new RestTemplate();
    ResponseEntity<ListOfDrinksDTO> response = template.getForEntity(apiString, ListOfDrinksDTO.class);
    Optional<List<DrinkDTO>> optionalDrinkDTOS = Optional.ofNullable(response.getBody().getDrinks());
    if(optionalDrinkDTOS.isEmpty())throw new Exception("non ho trovato alcun cocktail con questo nome");
    return optionalDrinkDTOS.get();
    }
    public static List<DrinkDTO> getCocktailByIngredient (String ingredientName) throws Exception {
        String apiString = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + ingredientName;
        RestTemplate template = new RestTemplate();
        ResponseEntity<ListOfDrinksDTO> response = template.getForEntity(apiString, ListOfDrinksDTO.class);
        Optional<List<DrinkDTO>> optionalDrinkDTOS = Optional.ofNullable(response.getBody().getDrinks());
        if(optionalDrinkDTOS.isEmpty())throw new Exception("non ho trovato alcun cocktail con questo ingrediente");
        return optionalDrinkDTOS.get();
    }
    public static HashMap<String, String> ingredientsAndMisures (DrinkDTO drink){
        HashMap<String, String> ingredientsAndMisures = new HashMap<>();
        List<String> ingredients = new ArrayList<>();
        ingredients.add(drink.getStrIngredient1());
        ingredients.add(drink.getStrIngredient2());
        ingredients.add(drink.getStrIngredient3());
        ingredients.add(drink.getStrIngredient4());
        ingredients.add(drink.getStrIngredient5());
        ingredients.add(drink.getStrIngredient6());
        ingredients.add(drink.getStrIngredient7());
        ingredients.add(drink.getStrIngredient8());
        ingredients.add(drink.getStrIngredient9());
        ingredients.add(drink.getStrIngredient10());
        ingredients.add(drink.getStrIngredient11());
        ingredients.add(drink.getStrIngredient12());
        ingredients.add(drink.getStrIngredient13());
        ingredients.add(drink.getStrIngredient14());
        ingredients.add(drink.getStrIngredient15());
        List<String> misures = new ArrayList<>();
        misures.add(drink.getStrMeasure1());
        misures.add(drink.getStrMeasure2());
        misures.add(drink.getStrMeasure3());
        misures.add(drink.getStrMeasure4());
        misures.add(drink.getStrMeasure5());
        misures.add(drink.getStrMeasure6());
        misures.add(drink.getStrMeasure7());
        misures.add(drink.getStrMeasure8());
        misures.add(drink.getStrMeasure9());
        misures.add(drink.getStrMeasure10());
        misures.add(drink.getStrMeasure11());
        misures.add(drink.getStrMeasure12());
        misures.add(drink.getStrMeasure13());
        misures.add(drink.getStrMeasure14());
        misures.add(drink.getStrMeasure15());
        for (int i = 0; i < 15; i++) {
            if(ingredients.get(i) != null) {
                ingredientsAndMisures.put(ingredients.get(i), misures.get(i));
        }
        }
    return ingredientsAndMisures;
    }
    public static SendMessage start(Update update) {
        SendMessage message = new SendMessage();
        message.setText("Ciao "+ update.getMessage().getFrom().getFirstName()+ "! Benvenuto nel bot \"TheCocktailBot\"! Digita il nome di un cocktail per ricevere le info relative oppure un ingrediente per ricevere alcuni cocktail che contengono quell'ingrediente");
        message.setChatId(update.getMessage().getChatId().toString());
        return message;
    }


}
