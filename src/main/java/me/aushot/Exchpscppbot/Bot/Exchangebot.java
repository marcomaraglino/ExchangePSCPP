package me.aushot.Exchpscppbot.Bot;

import me.aushot.Exchpscppbot.Buttons.Buttons;
import me.aushot.Exchpscppbot.Commands.AdminCommand;
import me.aushot.Exchpscppbot.Commands.OtherCommands;
import me.aushot.Exchpscppbot.JsonFile.SaveFile;
import me.aushot.Exchpscppbot.Procedure.ExchangeProcedure;
import me.aushot.Exchpscppbot.Profile.Profile;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Exchangebot extends TelegramLongPollingBot {
    public ArrayList<Long> staffer = new ArrayList<>(Arrays.asList(224791679L, 236831699L));

    public Map<Long, Profile> profile = new HashMap<>();
    public List<Long> chat_ids = new ArrayList<>();
    public List<Long> banned = new ArrayList<>();


    Buttons buttons = new Buttons(this);
    ExchangeProcedure exchangeprocedure = new ExchangeProcedure(this);
    AdminCommand adminCommand = new AdminCommand(this);
    OtherCommands otherCommands = new OtherCommands(this);

    public String getBotUsername() {
        return "exchangepscpp_bot";
    }

    public String getBotVersion(){
        return "V1.3.3";
    }

    public String getBotToken() {
        return "putyourtokenhere";
    }
    
    public void onUpdateReceived(Update update) {

        ForwardMessage forwardMessage = new ForwardMessage();
        ReplyKeyboardRemove removekey = new ReplyKeyboardRemove();
        removekey.setRemoveKeyboard(true);
        SendMessage sendMessage = new SendMessage();
        EditMessageText new_message = new EditMessageText();

        ZoneId europe = ZoneId.of("Europe/Rome");
        LocalDateTime now = LocalDateTime.now(europe);

        long chat_id = 0;
        int msg_id = 0;
        String username = null;
        String msg = null;

        if(update.hasMessage() && update.getMessage().hasText()){
            if(!chat_ids.contains(update.getMessage().getChatId())){
                chat_ids.add(update.getMessage().getChatId());
            }
            if(!profile.containsKey(update.getMessage().getChatId())){
                profile.put(update.getMessage().getChatId(), new Profile());
                profile.get(update.getMessage().getChatId()).setId(update.getMessage().getChatId());
            }
        }

        // We check if the update has a message and the message has text
        //*
        // **********************                 HOME PAGE / BOT STARTATO
        //*+
        buttons.homeButtons(update, chat_id, msg, msg_id, username, now, sendMessage, removekey, new_message);

        //*************************************************************************************************************************************

        //*
        // INVIO FOTO
        //*

        exchangeprocedure.photoPSC(sendMessage, update);
        //***************************************************************

        if(update.hasMessage() && !update.getMessage().hasPhoto()) {



            chat_id = update.getMessage().getChatId();
            msg_id = update.getMessage().getMessageId();
            username = update.getMessage().getFrom().getUserName();
            msg = update.getMessage().getText();

            if(staffer.contains(chat_id) && msg.equals("/quit")) {
                SaveFile saveFile = new SaveFile(this);
                try {
                    saveFile.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(profile.toString());
                System.exit(0);
            }

            //*
            //          QUANDO UNO STAFFER /PENDING E VISUALIZZA GLI EXCHANGE ANCORA IN CORSO
            //*
            adminCommand.pendingCommand(chat_id, sendMessage, msg);

            //*********************************************************

            // *
            //                MOVE TO PENDING EXCHANGE - AFTER
            // *
            exchangeprocedure.pendingExchange(sendMessage, msg, forwardMessage, chat_id, username);

            //**********************************************************************************************************************************

            //***************************************************************************

            // *
            //                   DOPO CHE INVIA IL MESSAGGIO PER IL FEEDBACK
            // *
            exchangeprocedure.afterFeedback(sendMessage, forwardMessage, msg, chat_id, msg_id, username);

            //****************************************************************************************************************************************
            //*
            //*                                * PER LO STAFF *
            //*                       CONFERMARE LA FINE DELLA TRANSIZIONE
            //*                    *******************************************

            adminCommand.confirmCommand(username, sendMessage, msg, chat_id);

            //***************************************************************************************************************************************



            //*
            //*                    * PER LO STAFF *
            //*                  RIFIUTARE UNA TRASIZIONE
            //*                  ***************************

            adminCommand.refuseExchange(chat_id, sendMessage, msg);

            //***************************************************************************************************************************************

            //*
            //*                    * PER LO STAFF *
            //*                  UNBANNARE UN UTENTE
            //*                  ***************************

            adminCommand.unBanUser(chat_id, sendMessage, msg);

            //***************************************************************************************************************************************

            //*
            //*                    * PER LO STAFF *
            //*                  BANNARE UN UTENTE
            //*                  ***************************

            adminCommand.banUser(update, sendMessage, msg);

            //***************************************************************************************************************************************


            //*
            //CONTROLLO PAYSAFECARD / CARATTERI = 16 / TUTTI NUMERI
            //*
            exchangeprocedure.controlloPSC(msg, sendMessage, chat_id);

            //*************************************************************************************

            //*
            //MESSAGGIO PER LE FAQ - DA MODIFICARE
            //*

            buttons.faqButtons(update, sendMessage, removekey, chat_id);

            //***********************************************************************************************************************

            //*
            //MESSAGGIO PER LE COMMISSIONI
            //*

            buttons.commissionButtons(update, sendMessage, removekey, chat_id);
            //***********************************************************************************************************************
//*
            //  SELEZIONA IL PREZZO TRA 10, 25, 50, 100
            //*

            exchangeprocedure.selectPrice(sendMessage, msg, removekey, chat_id);
            //*******************************************************
            //*
            //INIZIO PROCEDURA EXCHANGE - COMANDO EXCHANGE          -/exchange
            //*
            exchangeprocedure.exchangeCommand(sendMessage, msg, chat_id);

            //*************************************************************************************************************



            //*
            //DEBUG/AMDIN COMMAND - ATTENTION
            //*
            adminCommand.adminDebugCommand(update, sendMessage, msg);
            //************************************************************

            otherCommands.otherCommands(update, sendMessage, removekey, now, chat_id, msg, username, new_message);
        }
        }
    }
