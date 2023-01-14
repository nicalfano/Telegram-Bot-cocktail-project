package Telegram.cocktail.project.bot;

import Telegram.cocktail.project.cocktailDB.businessLogics.TheCocktailServiceCors;

import Telegram.cocktail.project.cocktailDB.entities.DrinkDTO;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;


public class TheCocktailBotLogics extends TelegramLongPollingBot {

    public static String BOT_TOKEN = "5937728821:AAGBjJAIBK94wm4_E5cpZCl7zdfVHjchUr0";
    public static String BOT_USERNAME = "TheCocktailsInstructionsBot";





    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/start")) {
                try {
                execute(TheCocktailServiceCors.start(update));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            } else {
                try {
                    if
                        (TheCocktailServiceCors.getCocktailByIngredient(update.getMessage().getText()).get(0) !=null){
                            List<DrinkDTO> drinksByIngredient = TheCocktailServiceCors.getCocktailByIngredient(update.getMessage().getText());
                            for (DrinkDTO drink : drinksByIngredient) {
                                SendMessage message = new SendMessage();
                                SendPhoto photo = new SendPhoto();
                                message.setChatId(update.getMessage().getChatId());
                                message.setText(drink.getStrDrink());
                                photo.setChatId(update.getMessage().getChatId());
                                photo.setPhoto(new InputFile(drink.getStrDrinkThumb()));
                                try {
                                    execute(message);
                                    execute(photo);
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                } catch (Exception e) {
                    SendMessage message = new SendMessage();
                    message.setText("Non ho trovato alcun cocktail con questo ingrediente, controllo se esistono cocktail che si chiamano così...");
                    message.setChatId(update.getMessage().getChatId());
                    try {
                        execute(message);
                    } catch (TelegramApiException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        if(TheCocktailServiceCors.getCocktailByName(update.getMessage().getText()).get(0) !=null){
                            try {

                                for (DrinkDTO drink : (TheCocktailServiceCors.getCocktailByName(update.getMessage().getText()))) {
                                HashMap<String,String> mappaRicetta = TheCocktailServiceCors.ingredientsAndMisures(drink);
                                StringBuilder sb = new StringBuilder();
                                String ricetta = "gli ingredienti: \n";
                                mappaRicetta.forEach((s, s2) -> {
                                    sb.append(s2);
                                    sb.append(" of ");
                                    sb.append(s);
                                    sb.append("\n");
                                    });
                                ricetta += sb.toString()+"\n il bicchiere da utilizzare: \n";
                                ricetta += drink.getStrGlass()+"\n";
                                ricetta += "\n la categoria IBA: \n";
                                ricetta += drink.getStrIBA();



                                SendMessage ingredientiMisure = new SendMessage();
                                ingredientiMisure.setChatId(update.getMessage().getChatId());
                                ingredientiMisure.setText(ricetta);
                                SendMessage nomeCocktail = new SendMessage();
                                nomeCocktail.setChatId(update.getMessage().getChatId());
                                nomeCocktail.setText(drink.getStrDrink());
                                SendMessage istruzioni = new SendMessage();
                                SendPhoto photo = new SendPhoto();
                                photo.setChatId(update.getMessage().getChatId().toString());
                                photo.setPhoto(new InputFile(drink.getStrDrinkThumb()));
                                istruzioni.setChatId(update.getMessage().getChatId().toString());
                                Optional<String> videoLink = Optional.ofNullable(TheCocktailServiceCors.getCocktailByName(update.getMessage().getText()).get(0).getStrVideo());
                                istruzioni.setText(drink.getStrInstructionsIT());
                                SendMessage videoLinkOnChat = new SendMessage();
                                videoLinkOnChat.setChatId(update.getMessage().getChatId().toString());
                                if (videoLink.isPresent()) {
                                    videoLinkOnChat.setText(videoLink.get());
                                } else {
                                    videoLinkOnChat.setText("There is no video link aviable!");
                                }
                                execute(nomeCocktail);
                                execute(photo);
                                execute(ingredientiMisure);
                                execute(istruzioni);
                                execute(videoLinkOnChat);
                                }
                                } catch (TelegramApiException a) {
                                a.printStackTrace();
                            }
                        }
                    } catch (Exception b) {
                        SendMessage message4 = new SendMessage();
                        message4.setText("Non ho trovato alcun cocktail con questo nome");
                        message4.setChatId(update.getMessage().getChatId());
                        try {
                            execute(message4);
                        } catch (TelegramApiException ex) {
                            throw new RuntimeException(ex);
                        }
                        throw new RuntimeException(e);
                    }

                }
            }


        }
        }

    @Override
    public void onClosing() {
        super.onClosing();
    }

    /**
     * Return username of this bot
     */
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    /**
     * Is called when bot gets registered
     */
    @Override
    public void onRegister() {
        super.onRegister();
    }
}
