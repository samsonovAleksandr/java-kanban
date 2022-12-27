import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private HistoryManager historyManager = Managers.getDefaultHistory();
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private int i = 0;


    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


    private int newId() {
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
            updateEpicStatus(epic);
        }
    }

    @Override
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
        Task task = tasks.get(taskId);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicByID(int epicId) {
        Epic epic = epics.get(epicId);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public SubTask getSubtaskByID(int subtaskId) {
        SubTask subTask = subTasks.get(subtaskId);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public Collection<SubTask> getAllSubtasks() {
        return subTasks.values();
    }

    @Override
    public void deleteAllTasks() {
        for (Integer del : tasks.keySet()) {
            historyManager.remove(del);
        }
        tasks.clear();
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {
        int epicIdOfSubTask = subTasks.get(subtaskId).getEpicId();

        Epic epic = epics.get(epicIdOfSubTask);
        subTasks.remove(subtaskId);
        epic.getSubTaskId().remove(Integer.valueOf(subtaskId));
        updateEpicStatus(epic);
        historyManager.remove(subtaskId);
    }

    @Override
    public void deleteAllSubTasks() {
        for (Integer del : subTasks.keySet()) {
            historyManager.remove(del);
        }
        subTasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        for (Integer del : epics.keySet()) {
            historyManager.remove(del);
        }
        for (Integer del : subTasks.keySet()) {
            historyManager.remove(del);
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void deleteEpicById(int epicId) {
        if (epics.containsKey(epicId)) {
            for (Integer subTask : epics.get(epicId).getSubTaskId()) {
                historyManager.remove(subTask);
                subTasks.remove(subTask);
            }
        }
        epics.remove(epicId);
        historyManager.remove(epicId);
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

    private void updateEpicStatus(Epic epic) {
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
