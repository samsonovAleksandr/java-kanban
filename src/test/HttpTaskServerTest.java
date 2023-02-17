package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import model.Epic;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.*;
import server.HttpTaskManager;
import server.HttpTaskServer;
import server.KVServer;
import server.LocalDateTimeAdapter;
import service.FileBackedTasksManager;
import service.InMemoryTaskManager;
import service.Managers;
import service.TaskManager;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest extends TaskManagerTest<HttpTaskManager>{
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    private static HttpTaskServer server;
    private static KVServer kvServer;

    @BeforeEach
    void beforeEach() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        server = new HttpTaskServer();
        server.start();

        taskManager = new HttpTaskManager("http://localhost:8078");
    }

    @AfterEach
    void afterEach(){
        kvServer.stop();
        server.stop();
    }




    @Test
    void createTask12121() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task2 = new Task("test", TaskStatus.NEW, "TestDes", LocalDateTime.now(), 60);
        taskManager.createTask(task2);
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }
}
