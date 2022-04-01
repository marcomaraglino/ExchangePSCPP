package me.aushot.Exchpscppbot.Utils;

import me.aushot.Exchpscppbot.Bot.Exchangebot;
import me.aushot.Exchpscppbot.Profile.Profile;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

    private Exchangebot exchangebot;
    public Utils(Exchangebot exchangebot){
        this.exchangebot = exchangebot;
    }

    public Utils() {

    }

    public String getImportString(){
        return "Seleziona l'importo totale delle <b>PAYSAFECARD</b>, inserendo solamente la cifra senza valuta\n" +
                "<i>ESEMPIO: <b>100</b></i>";
    }
    public String getStartString(String username, LocalDateTime now){
        if (now.getHour() >= 6 && now.getHour() <= 12) {
            return ("Buongiorno \uD83C\uDF05, @" + username + "!\n" +
                    "\nQuesto è il bot per poter effettuare i tuoi exchange in sicurezza e velocità!" +
                    "\n\nDigita /exchange per iniziare una procedura di exchange" +
                    "\nDigita /faq per visualizzare le domande più comuni e per capire cosa vuol dire exchange" +
                    "\nDigita /commissioni per visualizzare le nostre commissioni" +
                    "\n\n" + exchangebot.getBotVersion() +" - ©-exchpscpp");
        } else if (now.getHour() > 12 && now.getHour() <= 16) {
            return  ("Buon pomeriggio ☀️, @" + username + "!" +
                    "\nQuesto è il bot per poter effettuare i tuoi exchange in sicurezza e velocità!" +
                    "\n\nDigita /exchange per iniziare una procedura di exchange" +
                    "\nDigita /faq per visualizzare le domande più comuni e per capire cosa vuol dire exchange" +
                    "\nDigita /commissioni per visualizzare le nostre commissioni" +
                    "\n\n" + exchangebot.getBotVersion() +" - ©-@exchpscpp");
        } else if (now.getHour() > 16 && now.getHour() <= 22) {
            return  ("Buona sera \uD83C\uDF07️, @" + username + "!\n" +
                    "\nQuesto è il bot per poter effettuare i tuoi exchange in sicurezza e velocità!" +
                    "\n\nDigita /exchange per iniziare una procedura di exchange" +
                    "\nDigita /faq per visualizzare le domande più comuni e per capire cosa vuol dire exchange" +
                    "\nDigita /commissioni per visualizzare le nostre commissioni" +
                    "\n\n" + exchangebot.getBotVersion() +" - ©-@exchpscpp");
        } else {
            return ("Buona notte \uD83C\uDF19, @" + username +
                    "\nQuesto è il bot per poter effettuare i tuoi exchange in sicurezza e velocità!" +
                    "\n\nDigita /exchange per iniziare una procedura di exchange" +
                    "\nDigita /faq per visualizzare le domande più comuni e per capire cosa vuol dire exchange" +
                    "\nDigita /commissioni per visualizzare le nostre commissioni" +
                    "\n\n" + exchangebot.getBotVersion() +" - ©-@exchpscpp");
        }
    }
    public void sendNewMessage(EditMessageText new_message, long chat_id, int msg_id, String answer){
        new_message.setParseMode("HTML");
        new_message.disableWebPagePreview();
        new_message.setChatId(String.valueOf(chat_id));
        new_message.setMessageId(msg_id);
        new_message.setText(answer);
    }
    public void sendMessage(SendMessage sendMessage, long chat_id, String msg){
        sendMessage.setParseMode("HTML");
        sendMessage.setText(msg);
        sendMessage.setChatId(String.valueOf(chat_id));
    }
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public void executeMessage(EditMessageText sendMessage){
        try {
            exchangebot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void executeMessage(ForwardMessage sendMessage){
        try {
            exchangebot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void executeMessage(SendMessage sendMessage){
        try {
            exchangebot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void removeUser(long chat_id){
        getProfile(chat_id).setPasso(false, false, false, false, false);
        getProfile(chat_id).setInizialized(false);
        getProfile(chat_id).setPsc(new ArrayList<>());
        getProfile(chat_id).setPscphoto(new ArrayList<>());
        getProfile(chat_id).setConfirmed(false);
        getProfile(chat_id).setCompleted(false);
        exchangebot.chat_ids.remove(chat_id);
        exchangebot.profile.remove(chat_id);
    }
    public void removeUserList(ArrayList arrayList, Object ob){
        arrayList.remove(ob);
    }
    public void removeUserList(HashMap arrayList, Object ob){
        arrayList.remove(ob);
    }
    public void addUserList(ArrayList arrayList, Object ob){
        arrayList.add(ob);
    }
    public void addUserList(HashMap arrayList, Object key, Object value){
        arrayList.put(key, value);
    }

    public Profile getProfile(long chat_id){
        return exchangebot.profile.get(chat_id);
    }
}
