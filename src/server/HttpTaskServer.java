package server;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.Epic;
import model.SubTask;
import model.Task;
import service.Managers;
import service.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class HttpTaskServer {

    private static TaskManager manager;
    private final Gson gson;
    private final HttpServer httpServer;
    private static final int PORT = 8080;


    public HttpTaskServer() throws IOException {
        manager = Managers.getDefault();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.createContext("/tasks/task", new TaskHandler());
        httpServer.createContext("/tasks/subtask", new SubtaskHandler());
        httpServer.createContext("/tasks/epic", new EpicHandler());
        httpServer.createContext("/tasks/history", new HistoryHandler());
        httpServer.createContext("/tasks/subtask/epic", new SubtasksByEpicHandler());
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }


    class TaskHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            InputStream inputStream = httpExchange.getRequestBody();
            String path = httpExchange.getRequestURI().getPath();
            String pathRequest = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            int pathLength = path.split("/").length;
            int id = 0;
            if (pathRequest != null){
                id = Integer.parseInt(pathRequest.split("=")[1]);
            }
            switch (requestMethod) {
                case "GET":
                    if (path.endsWith("task") && (pathLength == 3)) {
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            outputStream.write(("Список всех Task").getBytes(StandardCharsets.UTF_8));
                            outputStream.write(gson.toJson(manager.getTasks()).getBytes(StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            e.printStackTrace();
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    } else if (pathRequest.startsWith("id=") && (pathLength == 3)) {
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            outputStream.write(gson.toJson(manager.getTaskByID(id)).getBytes(StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            e.printStackTrace();
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    } else {
                        httpExchange.sendResponseHeaders(404, 0);
                        OutputStream outputStream = httpExchange.getResponseBody();
                        outputStream.close();
                    }
                    break;
                case "POST":
                    try (inputStream) {
                        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                        JsonElement jsonElement = JsonParser.parseString(body);
                        if (!jsonElement.isJsonObject()) {
                            throw new RuntimeException("Это не jsonObject");
                        }
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        Task task = gson.fromJson(jsonObject, Task.class);
                        if (path.endsWith("task") && (pathLength == 3)) {
                            if (id != 0) {
                                manager.updateTask(task, id);
                                httpExchange.sendResponseHeaders(201, 0);
                            } else {
                                manager.createTask(task);
                                httpExchange.sendResponseHeaders(201, 0);
                            }
                            OutputStream outputStream = httpExchange.getResponseBody();
                            outputStream.write((gson.toJson(task).getBytes()));
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        httpExchange.sendResponseHeaders(400, 0);
                    }
                    break;
                case "DELETE":
                    if (path.endsWith("task") && (pathLength == 3)) {
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            manager.deleteAllTasks();
                        } catch (IOException e) {
                            e.printStackTrace();
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    } else if (pathRequest.startsWith("id=") && (pathLength == 3)) {
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            manager.deleteTaskById(id);
                        } catch (IOException e) {
                            e.printStackTrace();
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    } else {
                        httpExchange.sendResponseHeaders(404, 0);
                        OutputStream outputStream = httpExchange.getResponseBody();
                        outputStream.close();
                    }
                    break;
                default:
                    httpExchange.sendResponseHeaders(404, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(("Данный метод не можем обработать.\n" +
                                "Используйте методы 'GET', 'POST', 'DELETE'").getBytes());
                        break;
                    }
            }
        }
    }

    class EpicHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            InputStream inputStream = httpExchange.getRequestBody();
            String path = httpExchange.getRequestURI().getPath();
            String pathRequest = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            int pathLength = path.split("/").length;
            int id = 0;
            if (pathRequest != null){
                id = Integer.parseInt(pathRequest.split("=")[1]);
            }
            switch (requestMethod) {
                case "GET":
                    if (path.endsWith("epic") && (pathLength == 3)) {
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            outputStream.write(gson.toJson(manager.getEpics()).getBytes(StandardCharsets.UTF_8));
                        }
                    } else if (pathRequest.startsWith("id=") && (pathLength == 3)) {
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            outputStream.write(gson.toJson(manager.getEpicByID(id)).getBytes(StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            e.printStackTrace();
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    } else {
                        httpExchange.sendResponseHeaders(406, 0);
                        OutputStream outputStream = httpExchange.getResponseBody();
                        outputStream.close();
                    }
                    break;
                case "POST":
                    try (inputStream) {
                        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                        if (path.endsWith("epic") && (pathLength == 3)) {
                            Epic epic = gson.fromJson(body, Epic.class);
                            if (id != 0) {
                                manager.updateEpic(epic, id);
                                httpExchange.sendResponseHeaders(201, 0);
                            } else {
                                manager.createEpic(epic);
                                httpExchange.sendResponseHeaders(201, 0);
                            }
                            OutputStream outputStream = httpExchange.getResponseBody();
                            outputStream.write((gson.toJson(epic)).getBytes());
                            outputStream.close();
                        }
                    }
                    break;
                case "DELETE":
                    if (path.endsWith("epic") && (pathLength == 3)) {

                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            manager.deleteAllEpics();
                            httpExchange.sendResponseHeaders(200, 0);
                        } catch (IOException e) {
                            e.printStackTrace();
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    } else if (pathRequest.startsWith("id=") && (pathLength == 3)) {
                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            manager.deleteEpicById(id);
                            httpExchange.sendResponseHeaders(200, 0);
                        } catch (IOException e) {
                            e.printStackTrace();
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    } else {
                        httpExchange.sendResponseHeaders(406, 0);
                        OutputStream outputStream = httpExchange.getResponseBody();
                        outputStream.close();
                    }
                    break;
                default:
                    httpExchange.sendResponseHeaders(404, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(("Данный метод не можем обработать.\n" +
                                "Используйте методы 'GET', 'POST', 'DELETE'").getBytes());
                    }
            }
        }
    }

    class SubtaskHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            InputStream inputStream = httpExchange.getRequestBody();
            String path = httpExchange.getRequestURI().getPath();
            String pathRequest = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            int pathLength = path.split("/").length;
            int id = 0;
            if (pathRequest != null){
                id = Integer.parseInt(pathRequest.split("=")[1]);
            }
            switch (requestMethod) {
                case "POST":
                    try (inputStream) {
                        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                        if (path.endsWith("subtask") && (pathLength == 3)) {
                            SubTask subtask = gson.fromJson(body, SubTask.class);
                            if (id != 0) {
                                manager.updateSubtask(subtask, id);
                                httpExchange.sendResponseHeaders(201, 0);
                            } else {
                                manager.createSubTask(subtask);
                                httpExchange.sendResponseHeaders(201, 0);
                            }
                            OutputStream outputStream = httpExchange.getResponseBody();
                            outputStream.write((gson.toJson(subtask).getBytes()));
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        httpExchange.sendResponseHeaders(400, 0);
                    }
                    break;
                case "GET":
                    if (path.endsWith("subtask") && (pathLength == 3)) {
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            outputStream.write(gson.toJson(manager.getAllSubtasks()).getBytes(StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            e.printStackTrace();
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    } else if (pathRequest.startsWith("id=") && (pathLength == 3)) {
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            outputStream.write(gson.toJson(manager.getSubtaskByID(id)).getBytes(StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            e.printStackTrace();
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    } else {
                        httpExchange.sendResponseHeaders(406, 0);
                        OutputStream outputStream = httpExchange.getResponseBody();
                        outputStream.close();
                    }
                    break;
                case "DELETE":
                    if (path.endsWith("subtask") && (pathLength == 3)) {
                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            manager.deleteAllSubTasks();
                            httpExchange.sendResponseHeaders(200, 0);
                        } catch (IOException e) {
                            e.printStackTrace();
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    } else if (pathRequest.startsWith("id=") && (pathLength == 3)) {
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream outputStream = httpExchange.getResponseBody()) {
                            manager.deleteSubtaskById(id);
                            httpExchange.sendResponseHeaders(200, 0);
                        } catch (IOException e) {
                            e.printStackTrace();
                            httpExchange.sendResponseHeaders(404, 0);
                        }
                    } else {
                        httpExchange.sendResponseHeaders(406, 0);
                        OutputStream outputStream = httpExchange.getResponseBody();
                        outputStream.close();
                    }
                    break;

                default:
                    httpExchange.sendResponseHeaders(404, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(("Данный метод не можем обработать.\n" +
                                "Используйте методы 'GET', 'POST', 'DELETE'")
                                .getBytes());
                    }
            }
        }
    }

    class SubtasksByEpicHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String path = httpExchange.getRequestURI().getPath();
            String pathRequest = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            int pathLength = path.split("/").length;
            int  id = Integer.parseInt(pathRequest.split("=")[1]);

            if (requestMethod.equals("GET")) {
                if (pathRequest.startsWith("id=") && pathLength == 4) {
                    httpExchange.sendResponseHeaders(200, 0);
                    try (OutputStream outputStream = httpExchange.getResponseBody()) {
                        outputStream.write(gson.toJson(manager.getEpicByID(id).getSubTaskId().toArray()).getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        e.printStackTrace();
                        httpExchange.sendResponseHeaders(404, 0);
                    }
                } else {
                    httpExchange.sendResponseHeaders(406, 0);
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.close();
                }
            } else {
                httpExchange.sendResponseHeaders(404, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(("Данный метод не можем обработать.\n" +
                            "Используйте метод 'GET'").getBytes());
                }
            }
        }
    }

    class HistoryHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String path = httpExchange.getRequestURI().getPath();
            String requestMethod = httpExchange.getRequestMethod();
            int pathLength = path.split("/").length;
            if (requestMethod.equals("GET")) {
                if (path.endsWith("history") && (pathLength == 3)) {
                    httpExchange.sendResponseHeaders(200, 0);
                    try (OutputStream outputStream = httpExchange.getResponseBody()) {
                        outputStream.write(gson.toJson(manager.getHistory()).getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        e.printStackTrace();
                        httpExchange.sendResponseHeaders(404, 0);
                    }
                } else {
                    httpExchange.sendResponseHeaders(404, 0);
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.close();
                }
            } else {
                httpExchange.sendResponseHeaders(404, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(("Данный метод не можем обработать.\n" +
                            "Используйте методы 'GET'").getBytes());
                }
            }
        }
    }

    class TasksHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String path = httpExchange.getRequestURI().getPath();
            String requestMethod = httpExchange.getRequestMethod();
            int pathLength = path.split("/").length;

            if (requestMethod.equals("GET")) {
                if (path.endsWith("tasks") && (pathLength == 2)) {
                    httpExchange.sendResponseHeaders(200, 0);
                    try (OutputStream outputStream = httpExchange.getResponseBody()) {
                        outputStream.write(gson.toJson(manager.getPrioritizedTasks()).getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        e.printStackTrace();
                        httpExchange.sendResponseHeaders(404, 0);
                    }
                } else {
                    httpExchange.sendResponseHeaders(404, 0);
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.close();
                }
            } else {
                httpExchange.sendResponseHeaders(404, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(("Данный метод не можем обработать.\n" +
                            "Используйте методы 'GET'").getBytes());
                }
            }
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        httpServer.start();
    }

    public void stop() {
        System.out.println("Останавливаем сервер на порту " + PORT);
        httpServer.stop(0);
    }
}
