package ru.nsu.martynov;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Loader {

    /**
     *
     * @param jsonPath — path to JSON file 👍
     * @return config 😎
     * @throws IOException — IOException 😖
     */
    public AppConfig loadConfig(String jsonPath) throws IOException {
        AppConfig config = new AppConfig();

        try (FileReader reader = new FileReader(jsonPath)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject root = new JSONObject(tokener);

            // Загружаем поваров
            JSONArray cookersArray = root.getJSONArray("cookers");
            Cooker[] cookers = new Cooker[cookersArray.length()];
            for (int i = 0; i < cookersArray.length(); i++) {
                int time = cookersArray.getInt(i);
                cookers[i] = new Cooker(time);
            }
            sillyCookerRevSort(cookers);
            config.setCookers(cookers);

            // Загружаем доставщиков
            JSONArray deliversArray = root.getJSONArray("delivers");
            Deliver[] delivers = new Deliver[deliversArray.length()];
            for (int i = 0; i < deliversArray.length(); i++) {
                JSONObject d = deliversArray.getJSONObject(i);
                int time = d.getInt("time");
                int capacity = d.getInt("capacity");
                delivers[i] = new Deliver(time, capacity);
            }
            sillyDeliverRevSort(delivers);
            config.setDelivers(delivers);

            // Загружаем склад
            int storageCap = root.getInt("storageCapacity");
            config.setStorage(new Storage(storageCap));
            config.setTimeDay(root.getInt("timeDay"));

        } catch (IOException e) {
            throw new IOException("Ошибка чтения конфигурации: " + e.getMessage(), e);
        }

        return config;
    }

    private void sillyCookerRevSort(Cooker[] cookers) {
        Arrays.sort(cookers, (a, b) -> Integer.compare(b.getTime(), a.getTime()));
    }

    private void sillyDeliverRevSort(Deliver[] delivers) {
        Arrays.sort(delivers, (a,b) -> Integer.compare(b.getTime(), a.getTime()));
    }
}
