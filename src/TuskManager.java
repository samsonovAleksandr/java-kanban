import java.util.*;

public class TuskManager {

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private int i = 0;

    private int newId() {
        return ++i;
    }


    public void createTask(Task task) {
        task.setId(newId());
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(newId());
        epics.put(epic.getId(), epic);
    }

    public void createSubTask(SubTask subTask) {
        int subTaskId = this.newId();

        subTask.setId(subTaskId);
        subTasks.put(subTaskId, subTask);

        int epicIdOfSubTask = subTask.getEpicId();

        Epic epic = epics.get(epicIdOfSubTask);
        if (epic != null) {
            epic.addSubTaskId(subTask);
            this.setEpicStatus(epics.get(subTask.getEpicId()));
        }
    }

    public Collection<Task> getTasks() {
        return tasks.values();
    }

    public Collection<Epic> getEpics() {
        return epics.values();
    }

    public ArrayList<SubTask> getSubTask(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<SubTask> subTaskArrayList = new ArrayList<>();
        List<Integer> subtasksFromEpic = epic.getSubTaskId();

        for (Integer integer : subtasksFromEpic) {
            subTaskArrayList.add(subTasks.get(integer));
        }
        return subTaskArrayList;
    }

    public Task getTaskByID(int taskId) {
        return tasks.get(taskId);
    }

    public Epic getEpicByID(int epicId) {
        return epics.get(epicId);
    }

    public SubTask getSubtaskByID(int subtaskId) {
        return subTasks.get(subtaskId);
    }

    public Collection<SubTask> getAllSubtasks() {
        return subTasks.values();
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteSubtaskById(int subtaskId) {
        int epicIdOfSubTask = subTasks.get(subtaskId).getEpicId();

        Epic epic = epics.get(epicIdOfSubTask);
        subTasks.remove(subtaskId);
        List<Integer> subtasksFromEpic = epic.getSubTaskId();
        for (int i = 0; i < subtasksFromEpic.size(); i++) {
            if (subtasksFromEpic.get(i) == subtaskId) {
                epic.removeSubTaskID(i);
            }

        }

        this.setEpicStatus(epic);
    }

    public void deleteAllSubtasks() {
        subTasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    public void deleteEpicById(int epicId) {
        epics.remove(epicId);
        ArrayList<Integer> idDelSubtasks = new ArrayList<>();

        for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
            if (entry.getValue().getEpicId() == epicId)
                idDelSubtasks.add(entry.getValue().getId());
        }
        for (Integer idDelSubtask : idDelSubtasks) {
            subTasks.remove(idDelSubtask);
        }
    }

    public void updateTask(Task task) {
        int idUpdTask = -1;
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            if (Objects.equals(entry.getValue().getName(), task.getName()))
                idUpdTask = entry.getValue().getId();
        }
        if (idUpdTask >= 0) {
            task.setId(idUpdTask);
            tasks.put(idUpdTask, task);
        }
    }


    public void updateEpic(Epic epic) {
        int idEpic = -1;
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            if (Objects.equals(entry.getValue().getName(), epic.getName()))
                idEpic = entry.getValue().getId();
        }
        if (idEpic >= 0) {
            epic.setId(idEpic);
            for (int i = 0; i < epics.get(idEpic).getSubTaskId().size(); i++) {
                SubTask subTask = subTasks.get(epics.get(idEpic).getSubTaskId().get(i));
                epic.addSubTaskId(subTask);
            }
            epics.put(idEpic, epic);
        }
    }

    public void updateSubtask(SubTask subTask) {
        int idUpdSubtask = -1;
        for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
            if (Objects.equals(entry.getValue().getName(), subTask.getName()))
                idUpdSubtask = entry.getValue().getId();
        }
        if (idUpdSubtask >= 0) {
            subTask.setId(idUpdSubtask);
            subTasks.put(idUpdSubtask, subTask);
            this.setEpicStatus(epics.get(subTask.getEpicId()));
        }
    }

    private void setEpicStatus(Epic epic) {
        TaskStatus oldTaskStatus = epic.getStatus();
        ArrayList<SubTask> subTasksUpd = new ArrayList<>();
        for (int i = 0; i < epic.getSubTaskId().size(); i++) {
            subTasksUpd.add(subTasks.get(epic.getSubTaskId().get(i)));
        }
        int counterDone = 0;
        int counterNew = 0;

        for (SubTask subTask : subTasksUpd) {
            switch (subTask.getStatus()) {
                case NEW:
                    counterNew++;
                    break;
                case IN_PROGRESS:
                    break;
                case DONE:
                    counterDone++;
                    break;
            }
        }

        if (subTasksUpd.size() == 0) {
            epic.setStatus(TaskStatus.NEW);
        } else if (counterDone == subTasksUpd.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else if (counterNew == subTasksUpd.size()) {
            epic.setStatus(oldTaskStatus);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TuskManager that = (TuskManager) o;
        return i == that.i && tasks.equals(that.tasks) && epics.equals(that.epics) && subTasks.equals(that.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, epics, subTasks, i);
    }

    @Override
    public String toString() {
        return "TuskManager{" +
                "tasks=" + tasks +
                ", epics=" + epics +
                ", subTasks=" + subTasks +
                ", i=" + i +
                '}';
    }
}