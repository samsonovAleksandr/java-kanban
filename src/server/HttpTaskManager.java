package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskEnum;
import service.FileBackedTasksManager;
import service.Managers;

import java.util.List;


public class HttpTaskManager extends FileBackedTasksManager {

    private KVTaskClient kvTaskClient;
    Gson gson = Managers.getGson();
    String url;

    public HttpTaskManager(String url) {
        this.url = url;
        kvTaskClient = new KVTaskClient(url);
    }

    @Override
    public void save() {
        kvTaskClient.put("task", gson.toJson(this.tasks));
        kvTaskClient.put("epic", gson.toJson(this.epics));
        kvTaskClient.put("subtask", gson.toJson(this.subTasks));
        kvTaskClient.put("history", gson.toJson(this.historyManager.getHistory()));
    }

    @Override
    public void load() {
        try {
            List<Task> tasks = gson.fromJson(kvTaskClient.load("task"),
                    new TypeToken<List<Task>>() {
                    }.getType());
            for (Task task : tasks) {
                createTask(task);
            }
        } catch (NullPointerException e) {
            System.out.println("Список сохраненных задач пуст");
        }

        try {
            List<Epic> epics = gson.fromJson(kvTaskClient.load("epic"),
                    new TypeToken<List<Epic>>() {
                    }.getType());

            for (Epic epic : epics) {
                createEpic(epic);
            }
        } catch (NullPointerException e) {
            System.out.println("Список сохраненных эпиков пуст");
        }

        try {
            List<SubTask> subtasks = gson.fromJson(kvTaskClient.load("subtask"),
                    new TypeToken<List<SubTask>>() {
                    }.getType());
            for (SubTask subtask : subtasks) {
                createSubTask(subtask);
            }
        } catch (NullPointerException e) {
            System.out.println("Список сохраненных подзадач пуст");
        }
        try {
            List<Task> history = gson.fromJson(kvTaskClient.load("history"),
                    new TypeToken<List<Task>>() {
                    }.getType());
            for (Task task : history) {
                if (task.getTaskEnum() == TaskEnum.TASK) {
                    getTaskByID(task.getId());
                } else if (task.getTaskEnum() == TaskEnum.EPIC_TASK) {
                    getEpicByID(task.getId());
                } else {
                    getSubtaskByID(task.getId());
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Список сохраненной истории пуст");
        }
    }
}

