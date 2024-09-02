package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем экземпляр TelegramBotsApi с использованием DefaultBotSession
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new GameBot()); // Регистрируем вашего бота
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

