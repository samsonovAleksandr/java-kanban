package service;

import model.*;

import java.time.LocalDateTime;
import java.util.*;

import static model.TaskStatus.*;

public class InMemoryTaskManager implements TaskManager {
    protected HistoryManager historyManager = Managers.getDefaultHistory();
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    protected int i = 0;

    protected TreeSet<Task> prioritizedTasks = new TreeSet<>(new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2) {
            if (task1.getStartTime() != null && task2.getStartTime() != null) {
                return task1.getStartTime().compareTo(task2.getStartTime());
            } else {
                return 1;
            }
        }
    });

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private int newId() {
        return ++i;
    }

    @Override
    public void createTask(Task task) {
        if (task != null){
            task.setId(newId());
            validatorTimeTasks(task);
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);

        }
    }

    @Override
    public void createEpic(Epic epic) {
        if (epic!=null){
            epic.setId(newId());
            epics.put(epic.getId(), epic);
            prioritizedTasks.add(epic);
        }

    }

    @Override
    public void createSubTask(SubTask subTask) {
        if (subTask!=null){
            subTask.setId(newId());
            validatorTimeTasks(subTask);
            subTasks.put(subTask.getId(), subTask);
            prioritizedTasks.add(subTask);


            int epicIdOfSubTask = subTask.getEpicId();
            Epic epic = epics.get(epicIdOfSubTask);
            if (epic != null) {
                epic.addSubTaskId(subTask);
                updateEpicStatus(epic);
            }
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
        if(epic != null){
            ArrayList<SubTask> subTaskArrayList = new ArrayList<>();
            List<SubTask> subtasksFromEpic = epic.getSubTaskId();

            for (SubTask subTask : subtasksFromEpic) {
                subTaskArrayList.add(subTask);
            }
            return subTaskArrayList;
        } else {
            return null;
        }

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
        Iterator<Task> iterator = prioritizedTasks.iterator();
        while (iterator.hasNext()){
            Task task = iterator.next();
            if (task.getTaskEnum().equals(TaskEnum.TASK)){
                iterator.remove();
            }
        }
        tasks.clear();
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {
            if(subTasks.containsKey(subtaskId)){
                int epicIdOfSubTask = subTasks.get(subtaskId).getEpicId();

                Epic epic = epics.get(epicIdOfSubTask);
                if(epic!=null){
                    epic.getSubTaskId().remove(subTasks.get(subtaskId));
                    subTasks.remove(subtaskId);
                    updateEpicStatus(epic);
                    historyManager.remove(subtaskId);
                    prioritizedTasks.remove(subTasks.get(subtaskId));
                }
            }
    }

    @Override
    public void deleteAllSubTasks() {
        for (Integer del : subTasks.keySet()) {
            historyManager.remove(del);
        }
        for (Epic epic : epics.values()) {
            epic.getSubTaskId().clear();
        }
        Iterator<Task> iterator = prioritizedTasks.iterator();
        while (iterator.hasNext()){
            Task task = iterator.next();
            if (task.getTaskEnum().equals(TaskEnum.SUB_TASK)){
                iterator.remove();
            }
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
        Iterator<Task> iterator = prioritizedTasks.iterator();
        while (iterator.hasNext()){
            Task task = iterator.next();
            if (task.getTaskEnum().equals(TaskEnum.EPIC_TASK)){
                iterator.remove();
            }
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
        historyManager.remove(taskId);
        prioritizedTasks.remove(taskId);
    }

    @Override
    public void deleteEpicById(int epicId) {
        if (epics.containsKey(epicId)) {
            for (SubTask subTask : epics.get(epicId).getSubTaskId()) {
                historyManager.remove(subTask.getId());
                subTasks.remove(subTask.getId());
            }
        }
        epics.remove(epicId);
        historyManager.remove(epicId);
        prioritizedTasks.remove(epicId);
    }

    @Override
    public void updateTask(Task task, int i) {
        if(task!=null){
            if (tasks.containsKey(i)) {
                task.setId(i);
                validatorTimeTasks(task);
                tasks.put(i, task);
            }
        }

    }

    @Override
    public void updateEpic(Epic epic, int i) {
        if(epic!=null){
            if (epics.containsKey(i)) {
                epic.setId(i);
                epics.put(i, epic);
            }
        }

    }

    @Override
    public void updateSubtask(SubTask subTask, int i) {
        if (subTask != null){
            if (subTasks.containsKey(i)) {
                subTask.setId(i);
                validatorTimeTasks(subTask);
                subTasks.put(i, subTask);
                updateEpicStatus(epics.get(subTask.getEpicId()));
            }
        }

    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> subTasksUpd = new ArrayList<>();
        for (int i = 0; i < epic.getSubTaskId().size(); i++) {
            subTasksUpd.add(subTasks.get(epic.getSubTaskId().get(i).getId()));
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

   private void validatorTimeTasks(Task task) {
        if (task != null){
            for (Task task1 : prioritizedTasks) {
                if (task1.getStartTime() != null) {
                    LocalDateTime t = task.getEndTime().minusMinutes(task.getStartTime().getMinute());
                    LocalDateTime t1 = task1.getEndTime().minusMinutes(task1.getStartTime().getMinute());
                    if (t.isEqual(t1)) {
                        throw new IllegalArgumentException("Одинаковое время у : " + task1.getId() + " и " + task.getId());
                    }
                }
            }
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
}
