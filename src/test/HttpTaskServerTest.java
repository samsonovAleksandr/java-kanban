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
import server.HttpTaskServer;
import server.KVServer;
import server.LocalDateTimeAdapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTest {
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


    }

    @AfterEach
    void afterEach(){
        kvServer.stop();
        server.stop();
    }




    @Test
    void createAndGetTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task2 = new Task("test", TaskStatus.NEW, "TestDes", LocalDateTime.now(), 60);
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response1.statusCode());
    }

    @Test
    void updateTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task?=1");
        Task task2 = new Task("test", TaskStatus.NEW, "TestDes", LocalDateTime.now(), 60);
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
    }

    @Test
    void deleteTaskID() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task2 = new Task("test", TaskStatus.NEW, "TestDes", LocalDateTime.now(), 60);
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        URI url2 = URI.create("http://localhost:8080/tasks/task?=1");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url2)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response1.statusCode());
    }



    @Test
    void createAndGetEpic() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic task2 = new Epic("test", TaskStatus.NEW, "TestDes");
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response1.statusCode());
    }

    @Test
    void updateEpic() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic?=1");
        Epic task2 = new Epic("test", TaskStatus.NEW, "TestDes");
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
    }

    @Test
    void deleteEpicID() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic task2 = new Epic("test", TaskStatus.NEW, "TestDes");
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        URI url2 = URI.create("http://localhost:8080/tasks/epic?=1");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url2)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response1.statusCode());
    }

    @Test
    void createAndGetSubTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        Epic epic = new Epic("test", TaskStatus.NEW, "TestDes");
        SubTask task2 = new SubTask("test", TaskStatus.NEW, "TestDes", LocalDateTime.now()
                , 60, 1);
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response1.statusCode());
    }

    @Test
    void updateSubTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask?=2");
        Epic epic = new Epic("test", TaskStatus.NEW, "TestDes");
        SubTask task2 = new SubTask("test", TaskStatus.NEW, "TestDes", LocalDateTime.now()
                , 60, 1);
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
    }

    @Test
    void deleteSubTaskID() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        Epic epic = new Epic("test", TaskStatus.NEW, "TestDes");
        SubTask task2 = new SubTask("test", TaskStatus.NEW, "TestDes", LocalDateTime.now()
                , 60, 1);
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        URI url2 = URI.create("http://localhost:8080/tasks/subtask?=2");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url2)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response1.statusCode());
    }

    @Test
    void getTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task2 = new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60);
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        Task task3 = new Task("test", TaskStatus.NEW, "TestDes", LocalDateTime.now(), 60);
        String json1 = gson.toJson(task3);
        HttpRequest.BodyPublisher bodyPublisher1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher1)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response1.statusCode());

        URI url1 = URI.create("http://localhost:8080/tasks");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response2.statusCode());
    }

    @Test
    void getHistory() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task2 = new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60);
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        Task task3 = new Task("test", TaskStatus.NEW, "TestDes", LocalDateTime.now(), 60);
        String json1 = gson.toJson(task3);
        HttpRequest.BodyPublisher bodyPublisher1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher1)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response1.statusCode());

        URI url1 = URI.create("http://localhost:8080/tasks/history");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response2.statusCode());
    }

    @Test
    void getSubTaskEpicID() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic task2 = new Epic("test", TaskStatus.NEW, "TestDes");
        String json = gson.toJson(task2);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        URI url2 = URI.create("http://localhost:8080/tasks/subtask");
        SubTask task3 = new SubTask("test", TaskStatus.NEW, "TestDes", LocalDateTime.now()
                , 60, 1);
        String json1 = gson.toJson(task3);
        HttpRequest.BodyPublisher bodyPublisher1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url2)
                .POST(bodyPublisher1)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response1.statusCode());

        URI url3 = URI.create("http://localhost:8080/tasks/subtask/epic?id=1");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url3)
                .GET()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response2.statusCode());
    }



}
