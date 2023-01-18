package controlles;

import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {

    File file;


    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        FileBackedTasksManager taskManager = new FileBackedTasksManager(new File("src/resources/history.csv"));
        taskManager.createTask(new Task("Погулять с собакой", TaskStatus.NEW, "Выйти в 6 вечера"));
        taskManager.createTask(new Task("Купить инструмент", TaskStatus.NEW, "Гаечные ключи"));
        taskManager.createEpic(new Epic("Генеральная уборка", TaskStatus.NEW, "Убрать квартиру"));
        taskManager.createSubTask(new SubTask("Помыть ванную", TaskStatus.NEW, "Помыть полки",
                3));
        taskManager.createSubTask(new SubTask("Убрать кухню",
                TaskStatus.NEW, "Разложить тарелки из посудомойки", 3));
        taskManager.createSubTask(new SubTask("Убрать Комнату",
                TaskStatus.NEW, "Пропылесосить", 3));
        taskManager.createEpic(new Epic("Подготовка к походу", TaskStatus.NEW, "Собрать вещи"));
        taskManager.getTaskByID(1);
        taskManager.getEpicByID(3);
        taskManager.getSubTasksByEpicId(3);
        taskManager.getTaskByID(2);
        taskManager.getTaskByID(1);


      /* FileBackedTasksManager taskManager = loadFromFile(new File("src/resources/history.csv"));


        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getHistory());*/

    }

    private void save() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            bufferedWriter.write("id,type,name,status,description,epic");
            bufferedWriter.newLine();
            for (Map.Entry<Integer, Task> taskEntry : tasks.entrySet()) {
                bufferedWriter.write(toString(taskEntry.getValue()));
                bufferedWriter.newLine();
            }
            for (Map.Entry<Integer, Epic> taskEntry : epics.entrySet()) {
                bufferedWriter.write(toString(taskEntry.getValue()));
                bufferedWriter.newLine();
            }
            for (Map.Entry<Integer, SubTask> taskEntry : subTasks.entrySet()) {
                bufferedWriter.write(toString(taskEntry.getValue()));
                bufferedWriter.newLine();
            }

            bufferedWriter.newLine();

            bufferedWriter.append(historyToString(historyManager));

        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Ошибка сохранения в файл.");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public Task fromString(String value) {
        String[] str = value.split(",");
        switch (str[1]) {
            case ("TASK"):
                return new Task(Integer.parseInt(str[0]), str[2], TaskStatus.valueOf(str[3]), str[4]);
            case ("EPIC_TASK"):
                return new Epic(Integer.parseInt(str[0]), str[2], TaskStatus.valueOf(str[3]), str[4]);
            case ("SUB_TASK"):
                return new SubTask(Integer.parseInt(str[0]), str[2], TaskStatus.valueOf(str[3]), str[4],
                        Integer.parseInt(str[5]));
        }
        return null;
    }

    static List<Integer> historyFromString(String value) {
        if (value != null) {
            String[] id = value.split(",");
            List<Integer> history = new ArrayList<>();
            for (String str : id) {
                history.add(Integer.parseInt(str));
            }
            return history;
        } else {
            return null;
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.load();
        return fileBackedTasksManager;
    }

    private void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            br.readLine();
            while (true) {
                String str = br.readLine();
                if (!str.isEmpty()) {
                    Task task = fromString(str);
                    switch (task.getTaskEnum()) {
                        case TASK:
                            tasks.put(task.getId(), task);
                            break;
                        case EPIC_TASK:
                            epics.put(task.getId(), (Epic) task);
                            break;
                        case SUB_TASK:
                            subTasks.put(task.getId(), (SubTask) task);
                            break;
                    }
                } else {
                    String historyStr = br.readLine();
                    List<Integer> historyList = historyFromString(historyStr);
                    historyLoad(historyList);
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void historyLoad(List<Integer> historyList) {
        for (Integer task : historyList) {
            if (tasks.containsKey(task)) {
                historyManager.add(tasks.get(task));
            } else if (epics.containsKey(task)) {
                historyManager.add(epics.get(task));
            } else {
                historyManager.add(subTasks.get(task));
            }
        }
    }


    public File getFile() {
        return file;
    }

    private String toString(Task task) {
        String[] str = new String[6];
        str[0] = Integer.toString(task.getId());
        str[1] = task.getTaskEnum().toString();
        str[2] = task.getName();
        str[3] = task.getStatus().toString();
        str[4] = task.getDescription();
        if (str[1].equals("SUB_TASK")) {
            SubTask subTask = (SubTask) task;
            str[5] = Integer.toString(subTask.getEpicId());
        } else {
            str[5] = "";
        }
        return String.join(",", str);
    }

    private static String historyToString(HistoryManager manager) {
        StringBuilder sd = new StringBuilder();
        for (Task task : manager.getHistory()) {
            sd.append(task.getId());
            sd.append(",");
        }
        return sd.toString();
    }


    @Override
    public List<Task> getHistory() {
        super.getHistory();
        save();
        return super.getHistory();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
    }

    @Override
    public Collection<Task> getTasks() {
        super.getTasks();
        save();
        return super.getTasks();
    }

    @Override
    public Collection<Epic> getEpics() {
        super.getEpics();
        save();
        return super.getEpics();
    }

    @Override
    public Collection<SubTask> getSubTasksByEpicId(int epicId) {
        super.getSubTasksByEpicId(epicId);
        save();
        return super.getSubTasksByEpicId(epicId);
    }

    @Override
    public Task getTaskByID(int taskId) {
        super.getTaskByID(taskId);
        save();
        return super.getTaskByID(taskId);
    }

    @Override
    public Epic getEpicByID(int epicId) {
        super.getEpicByID(epicId);
        save();
        return super.getEpicByID(epicId);
    }

    @Override
    public SubTask getSubtaskByID(int subtaskId) {
        super.getSubtaskByID(subtaskId);
        save();
        return super.getSubtaskByID(subtaskId);
    }

    @Override
    public Collection<SubTask> getAllSubtasks() {
        super.getAllSubtasks();
        save();
        return super.getAllSubtasks();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {
        super.deleteSubtaskById(subtaskId);
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteTaskById(int taskId) {
        super.deleteTaskById(taskId);
        save();
    }

    @Override
    public void deleteEpicById(int epicId) {
        super.deleteEpicById(epicId);
        save();
    }

    @Override
    public void updateTask(Task task, int i) {
        super.updateTask(task, i);
        save();
    }

    @Override
    public void updateEpic(Epic epic, int i) {
        super.updateEpic(epic, i);
        save();
    }

    @Override
    public void updateSubtask(SubTask subTask, int i) {
        super.updateSubtask(subTask, i);
        save();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
