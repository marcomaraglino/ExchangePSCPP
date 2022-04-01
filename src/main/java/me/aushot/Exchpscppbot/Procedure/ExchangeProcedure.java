package me.aushot.Exchpscppbot.Procedure;

import me.aushot.Exchpscppbot.Bot.Exchangebot;
import me.aushot.Exchpscppbot.Utils.Utils;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;


public class ExchangeProcedure extends Utils {

    private Exchangebot exchangebot;

    public ExchangeProcedure(Exchangebot exchangebot){
        super(exchangebot);
        this.exchangebot = exchangebot;
    }
    public void exchangeCommand(SendMessage sendMessage, String msg, long chat_id){
        if(msg.equals("/exchange")) {
            if (!exchangebot.banned.contains(chat_id)) {
                    if (!getProfile(chat_id).isInizialized()) {

                        getProfile(chat_id).setInizialized(true);

                        sendMessage(sendMessage, chat_id, getImportString());

                        executeMessage(sendMessage);

                        getProfile(chat_id).setPasso(true, false, false, false, false);
                    } else {
                        sendMessage(sendMessage, chat_id, "Hai già in corso un exchange! Devi attendere il completamento di quello!");
                        executeMessage(sendMessage);
                    }
                } else {
                    sendMessage(sendMessage, chat_id, "Non sei più abilitato a usufruire i servizi di questo bot!");
                    executeMessage(sendMessage);
                }
            }
    }
    public void selectPrice(SendMessage sendMessage, String msg, ReplyKeyboardRemove removekey, long chat_id){
        if(exchangebot.profile.containsKey(chat_id)) {
            if (getProfile(chat_id).isPasso1()) {
                try {
                    getProfile(chat_id).setImportpsc(Integer.parseInt(msg));

                    sendMessage(sendMessage, chat_id, "Ora devi inserire il codice di una <b> PAYSAFECARD</b>\n" +
                            "\nIl codice deve essere inserito senza trattini - <i>(ES: 0123456789012345)</i>\n" +
                            "\nInserisci il codice - <b>Inserisci 0 quando hai terminato</b>:");
                    sendMessage.setReplyMarkup(removekey);

                    executeMessage(sendMessage);
                    getProfile(chat_id).setPasso(false, true, false, false, false);
                } catch (NumberFormatException e) {

                    sendMessage(sendMessage, chat_id, "<b>❌ - ERRORE</b> - Devi inserire un numero\n" +
                            "<i>ESEMPIO: <b>100</b></i>\n" +
                            "Inserisci la cifra totale del tuo exchange:");

                    executeMessage(sendMessage);
                    return;
                }
            }
        }
    }
    public void controlloPSC(String msg, SendMessage sendMessage, long chat_id){
        if(exchangebot.profile.containsKey(chat_id)) {
            if (getProfile(chat_id).isPasso2()) {
                sendMessage.setChatId(String.valueOf(chat_id));
                sendMessage.setParseMode("HTML");
                if ((msg.equals("/start")
                        || msg.equals("/exchange")
                        || msg.equals("/commissioni")
                        || msg.equals("/faq"))) {
                    return;
                }
                if (msg.equals("0")) {
                    if (!getProfile(chat_id).getPsc().isEmpty()) {
                        sendMessage.setText("✅ - I codici <b>" + getProfile(chat_id).getPsc() + "</b> sono stati inseriti <b>CORRETTAMENTE!</b>" +
                                "\n\nOra devi inviare le foto degli scontrini/foglietti delle PAYSAFECARD" +
                                "\n- Tutti i dati degli scontrini dovranno essere ben visibili!" +
                                "\n- I dati che fornisci durante l'exchange, saranno utilizzati solo per garantire un exchange eseguito correttamente e in modo sicuro, successivamente i dati verranno eliminati permanentemente in modo automatico" +
                                "\n\nInvia le foto degli scontrini PAYSAFECARD - <b>Inserisci 0 quando hai terminato</b>:");
                        executeMessage(sendMessage);
                        getProfile(chat_id).setPasso(false, false, true, false, false);

                    } else {
                        return;
                    }
                } else if (!isNumeric(msg)) {
                    sendMessage.setText("<b>❌ - ERRORE</b> - Il codice PAYSAFECARD deve essere formato solo da numeri" +
                            "\n" +
                            "\nInserisci nuovamente il codice:");
                    executeMessage(sendMessage);
                    return;
                } else if (!(msg.length() == 16)) {
                    sendMessage.setText("<b>❌ - ERRORE</b> - Il codice PAYSAFECARD deve essere formato da 16 numeri" +
                            "\n" +
                            "\nInserisci nuovamente il codice:");
                    executeMessage(sendMessage);
                    return;
                } else if (msg.length() == 16 && isNumeric(msg)) {
                    if (!msg.equals("0")) {
                        getProfile(chat_id).getPsc().add(msg);
                        sendMessage(sendMessage, chat_id, "<b>Codice inviato con successo!</b>" +
                                "\nQuando hai finito di inviare i codici invia il messaggio '<b>0</b>'\n" +
                                "Inserisci un altro codice <b>PSC</b>, altrimenti '<b>0</b>' se hai terminato:");
                        executeMessage(sendMessage);
                        return;
                    }
                }
            }
        }
    }


    public void photoPSC(SendMessage sendMessage, Update update){
            if (update.hasMessage()) {

                long chat_id = update.getMessage().getChatId();
                int msg_id = update.getMessage().getMessageId();

                if(exchangebot.profile.containsKey(chat_id)) {
                    if (getProfile(chat_id).isPasso3()) {
                        if (update.getMessage().hasText()) {
                            if (update.getMessage().getText().equals("0")) {
                                if (!getProfile(chat_id).getPscphoto().isEmpty()) {
                                    sendMessage(sendMessage, chat_id, "✅ - Foto inviate con successo!" +
                                            "\nOra devi inserire la tua <b>Email PayPal</b> a cui invieremo il denaro - " +
                                            "\n<i>(ES: mariorossi@gmail.com)</i>\n" +
                                            "\nInserisci la tua email <b>PayPal</b>:");
                                    executeMessage(sendMessage);

                                    Thread setpasso4 = new Thread(() -> {
                                        getProfile(chat_id).setPasso(false, false, false, true, false);
                                        try {
                                            Thread.sleep(20);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    });

                                    setpasso4.start();
                                } else {
                                    return;
                                }
                            }
                        }
                        if (update.getMessage().hasPhoto()) {
                            getProfile(chat_id).getPscphoto().add(msg_id);

                            sendMessage(sendMessage, chat_id, "<b>Foto Inviata con successo!</b>" +
                                    "\nQuando hai finito di inviare le foto invia il messaggio '<b>0</b>'");
                            executeMessage(sendMessage);
                            return;
                        } else if (!(update.getMessage().getText().equals("0"))) {
                            sendMessage.setParseMode("HTML");
                            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                            sendMessage.setText("<b>❌ - ERRORE</b> - Devi inviare una foto!");
                            executeMessage(sendMessage);
                        }
                    }
                }
            }
    }
    public void pendingExchange(SendMessage sendMessage, String msg, ForwardMessage forwardMessage, long chat_id, String username) {
        if (exchangebot.profile.containsKey(chat_id)) {
            if (getProfile(chat_id).isPasso4()) {
                if (msg.contains("@")) {
                    float importoPaypal;
                    float importoUtente = exchangebot.profile.get(chat_id).getImportpsc();

                    getProfile(chat_id).setEmail(msg);
                    getProfile(chat_id).setUsername(username);

                    importoPaypal = importoUtente - (importoUtente * 10) / 100;
                    sendMessage.setParseMode("HTML");
                    sendMessage.setChatId(String.valueOf(chat_id));
                    sendMessage.setText("Hai effettuato l'exchange!\n"
                            + "Il repilogo dei tuoi dati:\n" +
                            "\n\uD83D\uDEB9 Username: @" + username +
                            "\n\uD83D\uDCB6 Importo da convertire: " + getProfile(chat_id).getImportpsc() + "€ PSC -> " + (importoPaypal) + "€" +
                            "\n\uD83D\uDCB3 Codice PAYSAFECARD: " + getProfile(chat_id).getPsc() +
                            "\n\uD83D\uDCE9 Email PayPal: " + getProfile(chat_id).getEmail() +
                            "\n\nOra dovrai attendere che un operatore effettui l'exchange e quando verrà effettuato ti invieremo la conferma!");
                    executeMessage(sendMessage);

                    for (Long staffer : exchangebot.staffer) {
                        sendMessage(sendMessage, staffer, "@" + username + " ha effettuato exchange di " + exchangebot.profile.get(chat_id).getImportpsc()
                                + "€\nCodice PAYSAFECARD: " + "<b>" + exchangebot.profile.get(chat_id).getPsc() +
                                "</b>\nIndirizzo email: " + msg +
                                "\n\nDigita /conferma" + chat_id + " quando hai finito l'exchange" + "" +
                                "\nDigita /rifiuta" + chat_id + " [motivo] se vuoi rifiutare l'exchange dell'utente" +
                                "\nDigita /ban" + chat_id + " per bannare l'utente" + "" +
                                "\n\nFoto PAYSAFECARD dell'utente:");
                        executeMessage(sendMessage);
                        for (int i = 0; i < getProfile(chat_id).getPscphoto().size(); i++) {
                            forwardMessage.setChatId(String.valueOf(staffer));
                            forwardMessage.setFromChatId(String.valueOf(chat_id));
                            forwardMessage.setMessageId(getProfile(chat_id).getPscphoto().get(i));
                            executeMessage(forwardMessage);
                        }
                    }

                    getProfile(chat_id).setPasso(false, false, false, false, true);

                } else {
                    sendMessage.setText("<b>❌ - ERRORE</b> - Email non corretta! Riprova");
                    sendMessage.setParseMode("HTML");
                    sendMessage.setChatId(String.valueOf(chat_id));
                    executeMessage(sendMessage);
                }
            }
        }
    }

    public void afterFeedback(SendMessage sendMessage, ForwardMessage forwardMessage, String msg, long chat_id,
                              int msg_id, String username) {
        if (exchangebot.profile.containsKey(chat_id)) {
            if (getProfile(chat_id).isConfirmed()) {
                if (msg.contains("+rep @exchpscpp")) {

                    getProfile(chat_id).setCompleted(true);

                    sendMessage.setChatId(String.valueOf(chat_id));
                    sendMessage.setText("Grazie per il tuo feedback!\n" +
                            "La vostra soddisfazione è il nostro guadagno!");
                    executeMessage(sendMessage);

                    for (Long staffer : exchangebot.staffer) {
                        sendMessage.setText("⬇️ Il feedback dell'utente @" + username + " ⬇️");
                        sendMessage.setChatId(String.valueOf(staffer));
                        forwardMessage.setChatId(String.valueOf(staffer));
                        forwardMessage.setFromChatId(String.valueOf(chat_id));
                        forwardMessage.setMessageId(msg_id);

                        executeMessage(sendMessage);
                        executeMessage(forwardMessage);
                    }
                } else {
                    sendMessage.setChatId(String.valueOf(chat_id));
                    sendMessage.setText("Devi inserire '+rep @exchpscpp' nel tuo feedback!");
                    executeMessage(sendMessage);
                    return;
                }
            }
        }
    }
}
