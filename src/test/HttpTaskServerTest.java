package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskManager;
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

public class HttpTaskServerTest extends TaskManagerTest<HttpTaskManager>{
    HttpTaskServer server;

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    HttpClient client;


    @BeforeEach
    void beforeEach() throws IOException {
        client = HttpClient.newHttpClient();
        server = new HttpTaskServer();
        server.start();

    }

   @AfterEach
    void afterEach() {
        server.stop();

    }

    @Test
    void createTask() throws IOException, InterruptedException {
        Task task = new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60);
        URI url = URI.create("http://localhost:8080/tasks/task");
        String json = gson.toJson(task);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_2)
                .POST(bodyPublisher)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

    }
}
