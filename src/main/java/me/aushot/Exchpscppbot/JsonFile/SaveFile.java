package me.aushot.Exchpscppbot.JsonFile;

import com.google.gson.Gson;
import me.aushot.Exchpscppbot.Bot.Exchangebot;
import me.aushot.Exchpscppbot.Profile.Profile;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveFile{
    private static FileWriter file;
    private Exchangebot exchangebot;
    public void save() throws IOException {
        List<Profile> list = new ArrayList<>();

        for (Long chat_id: exchangebot.chat_ids){
            list.add(exchangebot.profile.get(chat_id));
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        System.out.println(list);

        file = new FileWriter("/home/ubuntu/data.json");
        file.write(jsonString + "\n");
        file.close();
    }

    public SaveFile(Exchangebot exchangebot){
        this.exchangebot = exchangebot;
    }

}
