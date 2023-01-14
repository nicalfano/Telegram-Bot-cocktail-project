package Telegram.cocktail.project.cocktailDB.entities;

import lombok.Data;

import java.util.List;

@Data
public class ListOfDrinksDTO {
    List<DrinkDTO> drinks;
}
