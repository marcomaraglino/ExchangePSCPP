package me.aushot.Exchpscppbot.Main;

import me.aushot.Exchpscppbot.Bot.Exchangebot;
import me.aushot.Exchpscppbot.JsonFile.ReadFile;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Exchangebot exchangebot = new Exchangebot();
        ReadFile readFile = new ReadFile(exchangebot);
        readFile.readfile();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(exchangebot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
