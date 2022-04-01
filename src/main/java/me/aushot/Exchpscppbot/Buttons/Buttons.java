package me.aushot.Exchpscppbot.Buttons;

import me.aushot.Exchpscppbot.Bot.Exchangebot;
import me.aushot.Exchpscppbot.Utils.Utils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Buttons extends Utils {

    private Exchangebot exchangebot;


    public Buttons(Exchangebot exchangebot){
        super(exchangebot);
        this.exchangebot = exchangebot;
    }
    public static synchronized void pricePSC(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        //Options
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        //First Line
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("10€"));
        keyboardFirstRow.add(new KeyboardButton("25€"));

        //Second Line
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("50€"));
        keyboardSecondRow.add(new KeyboardButton("100€"));

        // Add all of the keyboard rows to the list
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    public void commissionButtons(Update update, SendMessage sendMessage, ReplyKeyboardRemove removekey, long chat_id) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/commissioni")) {
                if (       getProfile(chat_id).isPasso1()
                        || getProfile(chat_id).isPasso2()
                        || getProfile(chat_id).isPasso3()
                        || getProfile(chat_id).isPasso4()) {

                    sendMessage(sendMessage, chat_id, "❌ La procedura di Exchange è stata cancellata! ❌");
                    sendMessage.setReplyMarkup(removekey);
                    executeMessage(sendMessage);

                    removeUser(chat_id);
                    exchangebot.chat_ids.remove(chat_id);
                    exchangebot.profile.remove(chat_id);
                }

                String text = "Le nostre commissioni sono del 10% vale a dire che: \n" +
                        "€10 PSC ▶️ €9.00 PAYPAL\n" +
                        "€25 PSC ▶️ €22.50 PAYPAL\n" +
                        "€50 PSC ▶️ €45.00 PAYPAL\n" +
                        "€100 PSC ▶️ €90.00 PAYPAL\n"+
                        "\nDigita /faq per maggiori informazioni";

                sendMessage(sendMessage, chat_id, text);
                sendMessage.disableWebPagePreview();

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

                List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

                InlineKeyboardButton btn1 = new InlineKeyboardButton();
                btn1.setText("\uD83C\uDFE0TORNA ALLA HOME\uD83C\uDFE0");
                btn1.setCallbackData("home");

                InlineKeyboardButton btn2 = new InlineKeyboardButton();
                btn2.setText("❓FAQ❓");
                btn2.setCallbackData("faq");

                rowInline1.add(btn1);
                rowInline1.add(btn2);

                rowsInline.add(rowInline1);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                sendMessage.setReplyMarkup(markupInline);

                executeMessage(sendMessage);
            }
        }
    }

    public void faqButtons(Update update, SendMessage sendMessage, ReplyKeyboardRemove removekey, long chat_id) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            if (update.getMessage().getText().equals("/faq")) {
                if (       getProfile(chat_id).isPasso1()
                        || getProfile(chat_id).isPasso2()
                        || getProfile(chat_id).isPasso3()
                        || getProfile(chat_id).isPasso4()) {

                    sendMessage(sendMessage, chat_id, "❌ La procedura di Exchange è stata cancellata! ❌");
                    sendMessage.setReplyMarkup(removekey);

                    executeMessage(sendMessage);

                    removeUser(chat_id);
                    exchangebot.chat_ids.remove(chat_id);
                    exchangebot.profile.remove(chat_id);
                }

                String text = "❓❓<b>FAQ</b>❓❓\n" +
                        "\n" +
                        "❓<b>Cosa significa Exchange</b>❓\n" +
                        "Per Exchange si intende dare qualcosa (in questo caso denaro), per ricevere in cambio qualcosa della stessa tipologia (sempre denaro).\n" +
                        "\n" +
                        "❓<b>Cosa facciamo noi?</b>❓\n" +
                        "Exchange fra <b>PAYSAFECARD e PayPal</b>*. \n" +
                        "Ciò significa che sarai tu a fornirci il denaro come PAYSAFECARD e noi successivamente con PAYPAL\n" +
                        "\n" +
                        "<a href=\"https://www.paysafecard.com/it-it/prodotti/paysafecard/\">Scopri cosa è PAYSAFECARD</a> e dove è possibile trovare un punto vendita\n" +
                        "<a href=\"https://www.paypal.com/it/webapps/mpp/personal\">Scopri cosa è PayPal</a>\n" +
                        "\n" +
                        "❓<b>A cosa serve questo tipo di servizio?</b>❓\n" +
                        "Con <b>PAYSAFECARD</b> è difficile poter pagare qualcuno in semplicità, o trovare qualcuno che sia disposto a utilizzarlo come metodo di pagamento per un bene o un servizio ed eventualmente spenderlo.\n" +
                        "<b>PayPal</b> è il metodo di pagamento più usato al mondo ed è utilizzando nella stragrande maggioranza del commercio elettronico\n" +
                        "\n" +
                        "❓<b>Come si effettua una transizione?</b>❓\n" +
                        "Se sei intenzionato a effettuare un EXCHANGE, dovrai utilizzare il nostro BOT ufficiale @exchpscpp_bot e seguire i passaggi indicati durante il processo.\n" +
                        "\n" +
                        "❓<b>Quali sono i tempi di processamento?</b>❓\n" +
                        "L'operazione di solito non richiede più di <b>pochi minuti</b>, per gli importi bassi. <b>Esempio 10€, 25€</b>\n" +
                        "In caso di operazioni con somme importanti o giorni di maggiore affluenza i tempi potrebbero allungarsi, ma non dovrebbero superare le 24h.\n" +
                        "\n" +
                        "❓<b>Perché fidarsi di noi?</b>❓\n" +
                        "Abbiamo diversi feedback nel nostro canale dedicato @exchpscppfeedbacks\n" +
                        "\n" +
                        "❓<b>Ci sono delle commissioni?</b>❓\n" +
                        "@exchpscpp applica una commissione del <b>10%</b> per ogni vostra transizione.\n" +
                        "Ciò significa che,<b> per esempio</b>, un cliente con una <b>PAYSAFECARD</b> da 10€ riceverà 9€ <b>PayPal</b>.\n" +
                        "\n" +
                        "❓<b>Perché applicate delle commissioni?</b>❓\n" +
                        "Applichiamo delle commissioni:\n" +
                        "- Per sostenere alcune spese durante il processo di EXCHANGE;\n" +
                        "- Il processo richiede tempo, la piccola commissione sarebbe una retribuzione del nostro tempo speso;\n" +
                        "- Sostenere alla crescita del progetto e di quelli affiliati;\n" +
                        "\n" +
                        "*sotto specifica richiesta del cliente è possibile ricevere il denaro su Hype";
                sendMessage(sendMessage, chat_id, text);
                sendMessage.disableWebPagePreview();

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

                InlineKeyboardButton btn1 = new InlineKeyboardButton();
                btn1.setText("\uD83C\uDFE0TORNA ALLA HOME\uD83C\uDFE0");
                btn1.setCallbackData("home");

                rowInline1.add(btn1);

                rowsInline.add(rowInline1);

                markupInline.setKeyboard(rowsInline);
                sendMessage.setReplyMarkup(markupInline);

                executeMessage(sendMessage);
            }
        }
    }
    public void homeButtons(Update update, long chat_id, String msg, int msg_id, String username, LocalDateTime now, SendMessage sendMessage, ReplyKeyboardRemove removekey, EditMessageText new_message) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            msg = update.getMessage().getText();
            chat_id = update.getMessage().getChatId();
            username = update.getMessage().getFrom().getUserName();

            if (msg.equals("/start")) {
                if(        getProfile(chat_id).isPasso1()
                        || getProfile(chat_id).isPasso2()
                        || getProfile(chat_id).isPasso3()
                        || getProfile(chat_id).isPasso4()){
                    sendMessage(sendMessage, chat_id, "❌ La procedura di Exchange è stata cancellata! ❌");
                    sendMessage.setReplyMarkup(removekey);
                    executeMessage(sendMessage);

                    removeUser(chat_id);
                    exchangebot.chat_ids.remove(chat_id);
                    exchangebot.profile.remove(chat_id);
                }

                String change;

                change = getStartString(username, now);

                sendMessage.setChatId(String.valueOf(chat_id));
                sendMessage.setText(change);

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

                List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
                List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
                List<InlineKeyboardButton> rowInLine3 = new ArrayList<>();

                InlineKeyboardButton btn1 = new InlineKeyboardButton();
                btn1.setText("\uD83D\uDD04EXCHANGE\uD83D\uDD04");
                btn1.setCallbackData("exchange");

                InlineKeyboardButton btn2 = new InlineKeyboardButton();
                btn2.setText("❓FAQ❓");
                btn2.setCallbackData("faq");

                InlineKeyboardButton btn3 = new InlineKeyboardButton();
                btn3.setText("\uD83D\uDCB6COMMISSIONI\uD83D\uDCB6");
                btn3.setCallbackData("commissioni");

                InlineKeyboardButton btn4 = new InlineKeyboardButton();
                btn4.setText("Contatti");
                btn4.setCallbackData("contatti");

                rowInline1.add(btn1);
                rowInline2.add(btn2);
                rowInline2.add(btn3);
                rowInLine3.add(btn4);
                // Set the keyboard to the markup
                rowsInline.add(rowInline1);
                rowsInline.add(rowInline2);
                rowsInline.add(rowInLine3);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                sendMessage.setReplyMarkup(markupInline);
                executeMessage(sendMessage);
            }
        } else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            msg_id = update.getCallbackQuery().getMessage().getMessageId();
            chat_id = update.getCallbackQuery().getMessage().getChatId();
            username = update.getCallbackQuery().getFrom().getUserName();

            if(call_data.equals("contatti")) {

                String answer = "Per assistenza contattaci:" +
                        "\nDeveloper/Founder @markinho22" +
                        "\nFounder @ImmagineRV";

                sendNewMessage(new_message, chat_id, msg_id, answer);

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

                InlineKeyboardButton btn1 = new InlineKeyboardButton();
                btn1.setText("\uD83C\uDFE0TORNA ALLA HOME\uD83C\uDFE0");
                btn1.setCallbackData("home");

                rowInline1.add(btn1);
                // Set the keyboard to the markup
                rowsInline.add(rowInline1);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                new_message.setReplyMarkup(markupInline);

                executeMessage(new_message);
            }

            if (call_data.equals("exchange")) {
                if (!exchangebot.banned.contains(chat_id)) {
                    if (!getProfile(chat_id).isInizialized()) {

                        getProfile(chat_id).setInizialized(true);

                        String answer = "\uD83D\uDD04 Procedura di <B>EXCHANGE</B> iniziata \uD83D\uDD04";

                        sendNewMessage(new_message, chat_id, msg_id, answer);

                        sendMessage(sendMessage, chat_id, getImportString());

                        executeMessage(sendMessage);
                        executeMessage(new_message);

                        getProfile(chat_id).setPasso(true, false, false, false, false);
                    } else {
                        String answer = "Hai già in corso un exchange! Devi attendere il completamento di quello!";
                        sendNewMessage(new_message, chat_id, msg_id, answer);

                        executeMessage(new_message);
                    }
                } else {
                    String answer = "Non sei più abilitato a usufruire i servizi di questo bot!";

                    sendNewMessage(new_message, chat_id, msg_id, answer);
                    executeMessage(new_message);
                }
            }


            if (call_data.equals("faq")) {
                String answer = "❓❓<b>FAQ</b>❓❓\n" +
                        "\n" +
                        "❓<b>Cosa significa Exchange</b>❓\n" +
                        "Per Exchange si intende dare qualcosa (in questo caso denaro), per ricevere in cambio qualcosa della stessa tipologia (sempre denaro).\n" +
                        "\n" +
                        "❓<b>Cosa facciamo noi?</b>❓\n" +
                        "Exchange fra <b>PAYSAFECARD e PayPal</b>*. \n" +
                        "Ciò significa che sarai tu a fornirci il denaro come PAYSAFECARD e noi successivamente con PAYPAL\n" +
                        "\n" +
                        "<a href=\"https://www.paysafecard.com/it-it/prodotti/paysafecard/\">Scopri cosa è PAYSAFECARD</a> e dove è possibile trovare un punto vendita\n" +
                        "<a href=\"https://www.paypal.com/it/webapps/mpp/personal\">Scopri cosa è PayPal</a>\n" +
                        "\n" +
                        "❓<b>A cosa serve questo tipo di servizio?</b>❓\n" +
                        "Con <b>PAYSAFECARD</b> è difficile poter pagare qualcuno in semplicità, o trovare qualcuno che sia disposto a utilizzarlo come metodo di pagamento per un bene o un servizio ed eventualmente spenderlo.\n" +
                        "<b>PayPal</b> è il metodo di pagamento più usato al mondo ed è utilizzando nella stragrande maggioranza del commercio elettronico\n" +
                        "\n" +
                        "❓<b>Come si effettua una transizione?</b>❓\n" +
                        "Se sei intenzionato a effettuare un EXCHANGE, dovrai utilizzare il nostro BOT ufficiale @exchpscpp_bot e seguire i passaggi indicati durante il processo.\n" +
                        "\n" +
                        "❓<b>Quali sono i tempi di processamento?</b>❓\n" +
                        "L'operazione di solito non richiede più di <b>pochi minuti</b>, per gli importi bassi. <b>Esempio 10€, 25€</b>\n" +
                        "In caso di operazioni con somme importanti o giorni di maggiore affluenza i tempi potrebbero allungarsi, ma non dovrebbero superare le 24h.\n" +
                        "\n" +
                        "❓<b>Perché fidarsi di noi?</b>❓\n" +
                        "Abbiamo diversi feedback nel nostro canale dedicato @exchpscppfeedbacks\n" +
                        "\n" +
                        "❓<b>Ci sono delle commissioni?</b>❓\n" +
                        "@exchpscpp applica una commissione del <b>10%</b> per ogni vostra transizione.\n" +
                        "Ciò significa che,<b> per esempio</b>, un cliente con una <b>PAYSAFECARD</b> da 10€ riceverà 9€ <b>PayPal</b>.\n" +
                        "\n" +
                        "❓<b>Perché applicate delle commissioni?</b>❓\n" +
                        "Applichiamo delle commissioni:\n" +
                        "- Per sostenere alcune spese durante il processo di EXCHANGE;\n" +
                        "- Il processo richiede tempo, la piccola commissione sarebbe una retribuzione del nostro tempo speso;\n" +
                        "- Sostenere alla crescita del progetto e di quelli affiliati;\n" +
                        "\n" +
                        "*sotto specifica richiesta del cliente è possibile ricevere il denaro su Hype";

                sendNewMessage(new_message, chat_id, msg_id, answer);


                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

                InlineKeyboardButton btn1 = new InlineKeyboardButton();
                btn1.setText("\uD83C\uDFE0TORNA ALLA HOME\uD83C\uDFE0");
                btn1.setCallbackData("home");

                rowInline1.add(btn1);
                // Set the keyboard to the markup
                rowsInline.add(rowInline1);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                new_message.setReplyMarkup(markupInline);
                executeMessage(new_message);
            }
            if (call_data.equals("commissioni")) {
                String answer = "Le nostre commissioni sono del 10% vale a dire che: \n" +
                        "€10 PSC ▶️ €9.00 PAYPAL\n" +
                        "€25 PSC ▶️ €22.50 PAYPAL\n" +
                        "€50 PSC ▶️ €45.00 PAYPAL\n" +
                        "€100 PSC ▶️ €90.00 PAYPAL\n" +
                        "\nDigita /faq per maggiori informazioni";

                sendNewMessage(new_message, chat_id, msg_id, answer);

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

                InlineKeyboardButton btn1 = new InlineKeyboardButton();
                btn1.setText("\uD83C\uDFE0TORNA ALLA HOME\uD83C\uDFE0");
                btn1.setCallbackData("home");

                InlineKeyboardButton btn2 = new InlineKeyboardButton();
                btn2.setText("❓FAQ❓");
                btn2.setCallbackData("faq");

                rowInline1.add(btn1);
                rowInline1.add(btn2);
                // Set the keyboard to the markup
                rowsInline.add(rowInline1);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                new_message.setReplyMarkup(markupInline);
                executeMessage(new_message);
            }
            if (call_data.equals("home")) {
                String change = getStartString(username, now);

                sendNewMessage(new_message, chat_id, msg_id, change);

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
                List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
                List<InlineKeyboardButton> rowInline3 = new ArrayList<>();

                InlineKeyboardButton btn1 = new InlineKeyboardButton();
                btn1.setText("\uD83D\uDD04EXCHANGE\uD83D\uDD04");
                btn1.setCallbackData("exchange");

                InlineKeyboardButton btn2 = new InlineKeyboardButton();
                btn2.setText("❓FAQ❓");
                btn2.setCallbackData("faq");

                InlineKeyboardButton btn3 = new InlineKeyboardButton();
                btn3.setText("\uD83D\uDCB6COMMISSIONI\uD83D\uDCB6");
                btn3.setCallbackData("commissioni");

                InlineKeyboardButton btn4 = new InlineKeyboardButton();
                btn4.setText("Contatti");
                btn4.setCallbackData("contatti");

                rowInline1.add(btn1);
                rowInline2.add(btn2);
                rowInline2.add(btn3);
                rowInline3.add(btn4);
                // Set the keyboard to the markup
                rowsInline.add(rowInline1);
                rowsInline.add(rowInline2);
                rowsInline.add(rowInline3);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                new_message.setReplyMarkup(markupInline);
                executeMessage(new_message);


            }
        }
    }
}
