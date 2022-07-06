package me.aushot.Exchpscppbot.JsonFile;

import me.aushot.Exchpscppbot.Bot.Exchangebot;
import me.aushot.Exchpscppbot.Profile.Profile;
import me.aushot.Exchpscppbot.Utils.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ReadFile extends Utils {

    private Exchangebot exchangebot;
    JSONParser parser = new JSONParser();

    JSONArray a = (JSONArray) parser.parse(new FileReader("changedirectoryhere"));

    public ReadFile(Exchangebot exchangebot) throws IOException, ParseException {
        this.exchangebot = exchangebot;
    }

    public void readfile() {
        for (Object o : a) {
            JSONObject jsonObject = (JSONObject) o;

            String username = (String) jsonObject.get("username");
            int importpsc = Math.toIntExact((Long) jsonObject.get("importpsc"));
            JSONArray psc = (JSONArray) jsonObject.get("psc");
            JSONArray pscphoto = (JSONArray) jsonObject.get("pscphoto");
            String email = (String) jsonObject.get("email");

            boolean inizialized = (Boolean) jsonObject.get("inizialized");
            boolean passo1 = (Boolean) jsonObject.get("passo1");
            boolean passo2 = (Boolean) jsonObject.get("passo2");
            boolean passo3 = (Boolean) jsonObject.get("passo3");
            boolean passo4 = (Boolean) jsonObject.get("passo4");
            boolean passo5 = (Boolean) jsonObject.get("passo5");
            boolean confirmed = (Boolean) jsonObject.get("confirmed");
            boolean completed = (Boolean) jsonObject.get("completed");
            int count = Math.toIntExact((Long) jsonObject.get("count"));

            long chat_id = (long) jsonObject.get("chat_id");


            Profile profile = new Profile(chat_id, username, importpsc, psc, pscphoto, email, inizialized, passo1, passo2, passo3, passo4, passo5, confirmed, completed, count);
            exchangebot.chat_ids.add(chat_id);

            exchangebot.profile.put(chat_id, profile);
            System.out.println(profile);
        }
    }
}
