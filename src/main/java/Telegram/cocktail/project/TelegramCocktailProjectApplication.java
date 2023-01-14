package Telegram.cocktail.project;

import Telegram.cocktail.project.bot.TheCocktailBotLogics;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TelegramCocktailProjectApplication {

		public static void main(String[] args) {
			try {
				// Create the TelegramBotsApi object to register your bots
				TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

				// Register your newly created AbilityBot
				botsApi.registerBot(new TheCocktailBotLogics());
			} catch (TelegramApiException e) {
				e.printStackTrace();
		}
	}
}



