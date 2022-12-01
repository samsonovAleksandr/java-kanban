import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    HistoryManager historyManager = new InMemoryHistoryManager();
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private int i = 0;


    @Override
    public int newId() {
        return ++i;
    }


    @Override
    public void createTask(Task task) {
        task.setId(newId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(newId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubTask(SubTask subTask) {
        subTask.setId(newId());
        subTasks.put(subTask.getId(), subTask);

        int epicIdOfSubTask = subTask.getEpicId();

        Epic epic = epics.get(epicIdOfSubTask);
        if (epic != null) {
            epic.addSubTaskId(subTask);
            this.updateEpicStatus(epics.get(subTask.getEpicId()));
        }
    }

    public Collection<Task> getTasks() {
        return tasks.values();
    }

    @Override
    public Collection<Epic> getEpics() {
        return epics.values();
    }

    @Override
    public Collection<SubTask> getSubTasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<SubTask> subTaskArrayList = new ArrayList<>();
        List<Integer> subtasksFromEpic = epic.getSubTaskId();

        for (Integer integer : subtasksFromEpic) {
            subTaskArrayList.add(subTasks.get(integer));
        }
        return subTaskArrayList;
    }


    @Override
    public Task getTaskByID(int taskId) {
        historyManager.add(tasks.get(taskId));
        return tasks.get(taskId);
    }

    @Override
    public Epic getEpicByID(int epicId) {
     historyManager.add(epics.get(epicId));
        return epics.get(epicId);
    }

    @Override
    public SubTask getSubtaskByID(int subtaskId) {
      historyManager.add(subTasks.get(subtaskId));
        return subTasks.get(subtaskId);
    }

    @Override
    public Collection<SubTask> getAllSubtasks() {
        return subTasks.values();
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {
        int epicIdOfSubTask = subTasks.get(subtaskId).getEpicId();

        Epic epic = epics.get(epicIdOfSubTask);
        subTasks.remove(subtaskId);
        epic.getSubTaskId().remove(Integer.valueOf(subtaskId));
        updateEpicStatus(epic);
    }

    @Override
    public void deleteAllSubTasks() {
        for (Epic epic : getEpics()) {
            epic.getSubTaskId().clear();
        }
        subTasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    @Override
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

    @Override
    public void updateTask(Task task, int i) {
        if (tasks.containsKey(i)) {
            task.setId(i);
            tasks.put(i, task);
        }
    }

    @Override
    public void updateEpic(Epic epic, int i) {
        if (epics.containsKey(i)) {
            epic.setId(i);
            tasks.put(i, epic);
        }
    }

    @Override
    public void updateSubtask(SubTask subTask, int i) {
        if (subTasks.containsKey(i)) {
            subTask.setId(i);
            subTasks.put(i, subTask);
            updateEpicStatus(epics.get(subTask.getEpicId()));
        }
    }

    @Override
    public void updateEpicStatus(Epic epic) {
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

        if ((subTasksUpd.size() == 0) || (counterNew == subTasksUpd.size())) {
            epic.setStatus(TaskStatus.NEW);
        } else if (counterDone == subTasksUpd.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryTaskManager that = (InMemoryTaskManager) o;
        return i == that.i && tasks.equals(that.tasks) && epics.equals(that.epics) && subTasks.equals(that.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, epics, subTasks, i);
    }

    @Override
    public String toString() {
        return "InMemoryTaskManager{" +
                ", tasks=" + tasks +
                ", epics=" + epics +
                ", subTasks=" + subTasks +
                ", i=" + i +
                '}';
    }
}
