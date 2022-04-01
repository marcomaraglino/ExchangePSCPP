package me.aushot.Exchpscppbot.Commands;

import me.aushot.Exchpscppbot.Bot.Exchangebot;
import me.aushot.Exchpscppbot.Utils.Utils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AdminCommand extends Utils {

    private Exchangebot exchangebot;

    public AdminCommand(Exchangebot exchangebot) {
        super(exchangebot);
        this.exchangebot = exchangebot;
    }

    public void unBanUser(Long chat_id, SendMessage sendMessage, String msg) {
        if (exchangebot.staffer.contains(chat_id) && msg.startsWith("/unban")) {

            final Long target = Long.parseLong(msg.substring(6));

            if (exchangebot.banned.contains(target)) {
                sendMessage(sendMessage, chat_id, "Hai unbannato l'utente " + target);
                executeMessage(sendMessage);

                exchangebot.banned.remove(target);
            } else {
                sendMessage(sendMessage, chat_id, "L'utente " + target + " non è bannato");
                executeMessage(sendMessage);

            }
        }
    }

    public void banUser(Update update, SendMessage sendMessage, String msg) {
        if (exchangebot.staffer.contains(update.getMessage().getChatId())
                && msg.startsWith("/ban")) {

            Long chat_id = update.getMessage().getChatId();
            Long target = Long.parseLong(msg.substring(4));

            if (!exchangebot.banned.contains(target)) {
                sendMessage.setText("Hai bannato l'utente " + target);
                sendMessage.setChatId(String.valueOf(chat_id));
                executeMessage(sendMessage);


                exchangebot.banned.add(target);
                exchangebot.profile.remove(target);
                exchangebot.chat_ids.remove(target);

            } else {
                sendMessage.setText("L'utente " + target + " è già bannato");
                sendMessage.setChatId(String.valueOf(chat_id));
                executeMessage(sendMessage);
            }
        }
    }

    public void refuseExchange(Long chat_id, SendMessage sendMessage, String msg) {

        if (exchangebot.staffer.contains(chat_id)
                && msg.startsWith("/rifiuta")) {

            String[] msgs;

            if (msg.contains(" ")) {
                msgs = msg.split(" ", 2);
            } else {
                sendMessage(sendMessage, chat_id, "Devi inserire una motivazione al tuo rifiuto");
                executeMessage(sendMessage);
                return;
            }

            Long target = Long.parseLong(msgs[0].substring(8));
            if (getProfile(target).isPasso5()) {

                sendMessage(sendMessage, target, "La tua transizione è stata rifiutata" +
                        "\n\nMotivo: " + msgs[1]);
                executeMessage(sendMessage);

                sendMessage(sendMessage, chat_id, "Hai cancellato l'exchange dell'utente!");
                executeMessage(sendMessage);

                removeUser(target);

            } else {
                sendMessage(sendMessage, chat_id, "Non c'è nessuna transizione di questo utente in corso");
                executeMessage(sendMessage);
            }
        }
    }


    public void pendingCommand(Long chat_id, SendMessage sendMessage, String msg) {
        if (exchangebot.staffer.contains(chat_id) && msg.equals("/pending")) {

            sendMessage(sendMessage, chat_id, "Exchange in attesa...");
            executeMessage(sendMessage);

            //FINIRE IL TESTO RENDERLO PIU' COMPLETO
            if (exchangebot.chat_ids.isEmpty()) {
                sendMessage.setText("Non ci sono exchange in attesa");
            } else {
                String exchanger = "";
                for (int i = 0; i < exchangebot.profile.size(); i++) {
                    exchanger = exchanger +
                            getProfile(exchangebot.chat_ids.get(i)).toString() + "\n\n";
                }
                sendMessage.setText(exchanger);
                if(!exchanger.contains("Username= @")){
                    sendMessage.setText("Non ci sono exchange in attesa");
                }
            }
            sendMessage.setChatId(String.valueOf(chat_id));
            executeMessage(sendMessage);
        }
    }

    public void confirmCommand(String username,SendMessage sendMessage, String msg, long chat_id) {
        if (exchangebot.staffer.contains(chat_id) && msg.contains("/conferma")) {

            final Long target_id = Long.parseLong(msg.substring(9));

            if (getProfile(target_id).isPasso5() &&
                    !(getProfile(target_id).isConfirmed())) {

                sendMessage(sendMessage, target_id, "La transizione è avvenuta con successo!\n" +
                        "Ora devi scrivere un feedback della tua esperienza:\n" +
                        "Includendo nel tuo feedback '+rep @exchpscpp'");
                executeMessage(sendMessage);
                sendMessage(sendMessage, chat_id, "Hai confermato la transizione dell'id = " + msg.substring(9) + " con successo!");
                executeMessage(sendMessage);
                getProfile(target_id).setConfirmed(true);


                for (Long staffer : exchangebot.staffer) {
                    sendMessage(sendMessage, staffer, "@" + username + " ha confermato l'exchange di @" +
                            getProfile(target_id).getUsername() + "(" + target_id + ")");
                    executeMessage(sendMessage);
                }

            } else {
                sendMessage(sendMessage, chat_id, "Non c'è nessuna transizione di questo utente in corso");
                executeMessage(sendMessage);
            }
        }
    }

    public void adminDebugCommand(Update update, SendMessage sendMessage, String msg) {

        Long chat_id = update.getMessage().getChatId();

        if (msg.equals("/admin 1072886310:AAG73mbE3dVtOzLSW8YPXYicFZcP6uHK_gk")) {
            addUserList(exchangebot.staffer, chat_id);

            sendMessage.setText("Sei diventato uno staffer!");
            sendMessage.setChatId(String.valueOf(chat_id));
            executeMessage(sendMessage);
        }

    }
}
