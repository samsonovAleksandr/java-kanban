package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskEnum;
import service.FileBackedTasksManager;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;


public class HttpTaskManager extends FileBackedTasksManager {

    private final KVTaskClient kvTaskClient;
    private final Gson gson;

    private static final String TASK = "task";
    private static final String EPIC = "epic";
    private static final String SUBTASK = "subtask";
    private static final String HISTORY = "history";

    private static final Type TYPE_TOKEN_TASK = new TypeToken<List<Task>>() {
    }.getType();
    private static final Type TYPE_TOKEN_EPIC = new TypeToken<List<Epic>>() {
    }.getType();
    private static final Type TYPE_TOKEN_SUB_TASK = new TypeToken<List<SubTask>>() {
    }.getType();


    public HttpTaskManager(String url) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        this.kvTaskClient = new KVTaskClient(url);
        load();
    }


    @Override
    public void save() {
        kvTaskClient.put(TASK, gson.toJson(this.tasks));
        kvTaskClient.put(EPIC, gson.toJson(this.epics));
        kvTaskClient.put(SUBTASK, gson.toJson(this.subTasks));
        kvTaskClient.put(HISTORY, gson.toJson(this.historyManager.getHistory()));
    }

    @Override
    public void load() {
        if (!tasks.isEmpty()) {
            List<Task> tasks = gson.fromJson(kvTaskClient.load(TASK), TYPE_TOKEN_TASK);

            for (Task task : tasks) {
                createTask(task);
            }
        } else {
            System.out.println("Список сохраненных задач пуст");
        }

        if (!epics.isEmpty()) {
            List<Epic> epics = gson.fromJson(kvTaskClient.load(EPIC), TYPE_TOKEN_EPIC);

            for (Epic epic : epics) {
                createEpic(epic);
            }
        } else {
            System.out.println("Список сохраненных эпиков пуст");
        }

        if (!subTasks.isEmpty()) {
            List<SubTask> subtasks = gson.fromJson(kvTaskClient.load(SUBTASK), TYPE_TOKEN_SUB_TASK);
            for (SubTask subtask : subtasks) {
                createSubTask(subtask);
            }
        } else {
            System.out.println("Список сохраненных подзадач пуст");
        }


        if (!getHistory().isEmpty()) {
            List<Task> hist = gson.fromJson(kvTaskClient.load(HISTORY), TYPE_TOKEN_TASK);
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

