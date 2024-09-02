package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameBot extends TelegramLongPollingBot {

    private Map<Long, Integer> userNumberMap = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "Viktor_43343_Bot";
    }

    @Override
    public String getBotToken() {
        return "7445390731:AAGV_Es6AaBapBL58xjrfXolTj3tYKQ9ZD8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String receivedMessage = update.getMessage().getText();

            if (!userNumberMap.containsKey(chatId)) {
                startGame(chatId);
            } else {
                processUserGuess(chatId, receivedMessage);
            }
        }
    }

    private void startGame(long chatId) {
        Random random = new Random();
        int numberToGuess = random.nextInt(10) + 1;
        userNumberMap.put(chatId, numberToGuess);

        sendMessage(chatId, "Я загадал число от 1 до 10. Попробуй угадать!");
    }

    private void processUserGuess(long chatId, String receivedMessage) {
        try {
            int userGuess = Integer.parseInt(receivedMessage);
            int numberToGuess = userNumberMap.get(chatId);

            if (userGuess == numberToGuess) {
                sendMessage(chatId, "Поздравляю! Ты угадал число.");
                userNumberMap.remove(chatId);  // Игра закончена
            } else {
                sendMessage(chatId, "Неправильно. Попробуй еще раз.");
            }
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Пожалуйста, введи число.");
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
