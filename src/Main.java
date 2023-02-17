import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import server.HttpTaskServer;
import server.KVServer;
import service.FileBackedTasksManager;
import service.Managers;
import service.TaskManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static service.FileBackedTasksManager.loadFromFile;

public class Main {
    public static void main(String[] args) throws IOException {
        KVServer kvServer = new KVServer();
        kvServer.start();


        HttpTaskServer taskServer = new HttpTaskServer();
        taskServer.start();


    }
}


