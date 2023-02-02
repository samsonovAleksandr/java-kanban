package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private final List<SubTask> subTaskId = new ArrayList<>();
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private long duration;

    public Epic(String name, TaskStatus status, String description) {
        super(name, status, description);
        this.startTime = getStartTime();
        this.duration = getDuration();
        this.endTime = getEndTime();
    }

    public Epic(int id, String name, TaskStatus status, String description) {
        super(id, name, status, description);
        this.endTime = null;
        this.startTime = null;
        this.duration = 0;
    }

    public Epic(int id, String name, TaskStatus status, String description, LocalDateTime startTime, long duration) {
        super(id, name, status, description, startTime, duration);
    }

    @Override
    public TaskEnum getTaskEnum() {
        return TaskEnum.EPIC_TASK;
    }

    public void addSubTaskId(SubTask subTask) {
        subTaskId.add(subTask);
    }

    public void removeSubTaskID(int subTaskId) {
        this.subTaskId.remove(subTaskId);
    }

    public List<SubTask> getSubTaskId() {
        return subTaskId;
    }

    public LocalDateTime getEndTime() {

        if (subTaskId != null && subTaskId.size() != 0) {
            return subTaskId.get(subTaskId.size() - 1).getEndTime();
        } else {
            return null;
        }
    }

    public LocalDateTime getStartTime() {
        LocalDateTime start = null;
        if (subTaskId != null && subTaskId.size() != 0) {
            for (SubTask task : subTaskId) {
                if (task.getStartTime() != null) {
                    start = task.getStartTime();
                    break;
                }
            }
            return start;
        } else {
            return null;
        }
    }

    public long getDuration() {
        if (getStartTime() != null && getEndTime() != null) {
            Duration time = Duration.between(getStartTime(), getEndTime());
            return time.toSeconds() / 60;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskId, epic.subTaskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskId);
    }

    @Override
    public String toString() {
        return "\n" + "Epic{" + "\n" +
                "Подзадачи = " + subTaskId + "\n" +
                " Время окончания = " + getEndTime() + "\n" +
                " id = " + id + "\n" +
                " Задача = '" + name + '\'' + "\n" +
                " Статус = " + status + "\n" +
                " Описание = '" + description + '\'' + "\n" +
                " Начало выполнения = " + getStartTime() + "\n" +
                " Продолжительность = " + getDuration() +
                '}';
    }
}