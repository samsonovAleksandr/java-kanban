import java.util.*;

public interface TaskManager {
    List<Task> getHistory();

    int newId();

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubTask(SubTask subTask);

    Collection<Task> getTasks();

    Collection<Epic> getEpics();

    Collection<SubTask> getSubTasksByEpicId(int epicId);

    Task getTaskByID(int taskId);

    Epic getEpicByID(int epicId);

    SubTask getSubtaskByID(int subtaskId);

    Collection<SubTask> getAllSubtasks();

    void deleteAllTasks();

    void deleteSubtaskById(int subtaskId);

    void deleteAllSubTasks();

    void deleteAllEpics();

    void deleteTaskById(int taskId);

    void deleteEpicById(int epicId);

    void updateTask(Task task, int i);

    void updateEpic(Epic epic, int i);

    void updateSubtask(SubTask subTask, int i);

    void updateEpicStatus(Epic epic);

}