package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskManager;
import server.HttpTaskServer;
import server.KVServer;
import server.LocalDateTimeAdapter;
import service.Managers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest  {

    HttpTaskServer server;

    Gson gson;

    KVServer kvServer;

    HttpClient client;

    @BeforeEach
    void beforeEach() throws IOException {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        kvServer = new KVServer();
        kvServer.start();

        server = new HttpTaskServer();
        server.start();

        client = HttpClient.newHttpClient();
    }

    @AfterEach
    void afterEach(){
        kvServer.stop();
        server.stop();
    }

    @Test
    void createTask() throws IOException, InterruptedException {
        Task task = new Task("TEstTask", TaskStatus.NEW, "TestDescriotion", LocalDateTime.now(), 30);
        String api = kvServer.getApiToken();
        URI url = URI.create("http://localhost:8078/save/task?API_TOKEN="+api);
        String js = gson.toJson(task);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(js);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(bodyPublisher).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void createEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("TEstTask", TaskStatus.NEW, "TestDescriotion");
        String api = kvServer.getApiToken();
        URI url = URI.create("http://localhost:8078/save/epic?API_TOKEN="+api);
        String js = gson.toJson(epic);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(js);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(bodyPublisher).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void createSubTask() throws IOException, InterruptedException {
        Epic epic = new Epic("TEstTask", TaskStatus.NEW, "TestDescriotion");
        SubTask subTask = new SubTask("TEstTask", TaskStatus.NEW, "TestDescriotion", LocalDateTime.now(),
                30, 1);
        String api = kvServer.getApiToken();
        URI url = URI.create("http://localhost:8078/save/subtask?API_TOKEN="+api);
        String js = gson.toJson(subTask);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(js);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(bodyPublisher).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void getTask() throws IOException, InterruptedException {
        Task task = new Task("TEstTask", TaskStatus.NEW, "TestDescriotion", LocalDateTime.now(), 30);
        String api = kvServer.getApiToken();
        URI url1 = URI.create("http://localhost:8078/save/task?API_TOKEN="+api);
        String js = gson.toJson(task);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(js);
        HttpRequest request = HttpRequest.newBuilder().uri(url1).POST(bodyPublisher).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        URI url = URI.create("http://localhost:8078/load/task?API_TOKEN="+api);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response1 = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response1.statusCode());
    }

    @Test
    void getEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("TEstTask", TaskStatus.NEW, "TestDescriotion");
        String api = kvServer.getApiToken();
        URI url1 = URI.create("http://localhost:8078/save/epic?API_TOKEN="+api);
        String js = gson.toJson(epic);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(js);
        HttpRequest request = HttpRequest.newBuilder().uri(url1).POST(bodyPublisher).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        URI url = URI.create("http://localhost:8078/load/epic?API_TOKEN="+api);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response1 = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response1.statusCode());
    }

    @Test
    void getSubTask() throws IOException, InterruptedException {
        Epic epic = new Epic("TEstTask", TaskStatus.NEW, "TestDescriotion");
        SubTask subTask = new SubTask("TEstTask", TaskStatus.NEW, "TestDescriotion", LocalDateTime.now(),
                30, 1);
        String api = kvServer.getApiToken();
        URI url1 = URI.create("http://localhost:8078/save/subtask?API_TOKEN="+api);
        String js = gson.toJson(subTask);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(js);
        HttpRequest request = HttpRequest.newBuilder().uri(url1).POST(bodyPublisher).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        URI url = URI.create("http://localhost:8078/load/subtask?API_TOKEN="+api);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response1 = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response1.statusCode());
    }
}
