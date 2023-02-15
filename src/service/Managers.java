package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.HttpTaskManager;
import server.KVServer;
import server.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

public abstract class Managers implements TaskManager {

    public static TaskManager getDefault() {
        return new HttpTaskManager("http://localhost:8078");
    }

    public static KVServer getDefaultKVServer() throws IOException {
        return new KVServer();
    }

    public static FileBackedTasksManager getFileBTM(){
        return new FileBackedTasksManager();
    }

    public static Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
