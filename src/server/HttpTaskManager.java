package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskEnum;
import service.FileBackedTasksManager;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;


public class HttpTaskManager extends FileBackedTasksManager {

    private KVTaskClient kvTaskClient;
    private static Gson gson;
    private String url;

    private static final String task = "task";
    private static final String epic = "epic";
    private static final String subtask = "subtask";
    private static final String history = "history";

    TypeToken<List<Task>> typeTokenTask;
    TypeToken<List<Epic>> typeTokenEpic;
    TypeToken<List<SubTask>> typeTokenSubTask;



    public HttpTaskManager(String url) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        this.url = url;
        this.kvTaskClient = new KVTaskClient(url);
    }


    @Override
    public void save() {
        kvTaskClient.put(task, gson.toJson(this.tasks));
        kvTaskClient.put(epic, gson.toJson(this.epics));
        kvTaskClient.put(subtask, gson.toJson(this.subTasks));
        kvTaskClient.put(history, gson.toJson(this.historyManager.getHistory()));
    }

    @Override
    public void load() {
        if (!tasks.isEmpty()) {
            List<Task> tasks = gson.fromJson(kvTaskClient.load(task), typeTokenTask.getType());

            for (Task task : tasks) {
                createTask(task);
            }
        } else {
            System.out.println("Список сохраненных задач пуст");
        }

        if (!epics.isEmpty()) {
            List<Epic> epics = gson.fromJson(kvTaskClient.load(epic), typeTokenEpic.getType());

            for (Epic epic : epics) {
                createEpic(epic);
            }
        } else {
            System.out.println("Список сохраненных эпиков пуст");
        }

        if (!subTasks.isEmpty()) {
            List<SubTask> subtasks = gson.fromJson(kvTaskClient.load(subtask), typeTokenSubTask.getType());
            for (SubTask subtask : subtasks) {
                createSubTask(subtask);
            }
        } else {
            System.out.println("Список сохраненных подзадач пуст");
        }


        if (!getHistory().isEmpty()) {
            List<Task> hist = gson.fromJson(kvTaskClient.load(history), typeTokenTask.getType());
            for (Task task : hist) {
                if (task.getTaskEnum() == TaskEnum.TASK) {
                    getTaskByID(task.getId());
                } else if (task.getTaskEnum() == TaskEnum.EPIC_TASK) {
                    getEpicByID(task.getId());
                } else {
                    getSubtaskByID(task.getId());
                }
            }
        } else {
            System.out.println("Список сохраненной истории пуст");
        }
    }
}

