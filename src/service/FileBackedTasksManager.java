package service;

import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public FileBackedTasksManager(){
        this.file = null;
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    protected void save() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            bufferedWriter.write("id,type,name,status,description,startTime,duration,epicId");
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
            throw new ManagerSaveException("Ошибка сохранения в файл.", e);
        }
    }

    public Task fromString(String value) {
        String[] str = value.split(",");
        int id = Integer.parseInt(str[0]);
        TaskEnum taskEnum = TaskEnum.valueOf(str[1]);
        String name = str[2];
        TaskStatus taskStatus = TaskStatus.valueOf(str[3]);
        String description = str[4];
        LocalDateTime startTime = null;
        long duration = 0;
        if (str.length > 7) {
            if (str[5] != null && !str[5].isEmpty() && str[6] != null) {
                startTime = LocalDateTime.parse(str[5], formatter);
                duration = Long.parseLong(str[6]);
            }
        }
            switch (taskEnum) {
                case TASK:
                    return new Task(id, name, taskStatus, description, startTime, duration);
                case EPIC_TASK:
                        return new Epic(id, name, taskStatus, description, startTime, duration);
                case SUB_TASK:
                        int epicId = Integer.parseInt(str[7]);
                        return new SubTask(id, name, taskStatus, description, startTime, duration, epicId);
                default:
                    throw new RuntimeException("Ошибка.");
            }
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

    protected void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            br.readLine();
            while (true) {
                String str = br.readLine();
                if (str != null && !str.isEmpty()) {
                    Task task = fromString(str);
                    switch (task.getTaskEnum()) {
                        case TASK:
                            createTask(task);
                            break;
                        case EPIC_TASK:
                            createEpic((Epic) task);
                            break;
                        case SUB_TASK:
                            createSubTask((SubTask) task);
                            break;
                    }
                } else {
                    String historyStr = br.readLine();
                    List<Integer> historyList = historyFromString(historyStr);
                    if (historyList != null) {
                        historyLoad(historyList);
                    }
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

    private String toString(Task task) {
        String idEpic;
        if (task.getTaskEnum().equals(TaskEnum.SUB_TASK)){
            idEpic = String.valueOf(((SubTask) task).getEpicId());
        } else {
            idEpic = "";
        }
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s", task.getId(), task.getTaskEnum(), task.getName(),
                    task.getStatus(), task.getDescription(), task.getStartTime(), task.getDuration(), idEpic);
    }

    private static String historyToString(HistoryManager manager) {
        StringBuilder sd = new StringBuilder();
        for (Task task : manager.getHistory()) {
            sd.append(task.getId());
            sd.append(",");
        }
        return sd.toString();
    }

    public File getFile() {
        return file;
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
}
