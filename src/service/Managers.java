package service;

import server.HttpTaskManager;
import server.KVServer;

import java.io.File;
import java.io.IOException;


public abstract class Managers implements TaskManager {

    public static TaskManager getDefault() {
        return new HttpTaskManager("http://localhost:8078");
    }

    public static KVServer getDefaultKVServer() throws IOException {
        return new KVServer();
    }

    public static FileBackedTasksManager getFileBTM(){
        return new FileBackedTasksManager(new File("src/resources/history.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
