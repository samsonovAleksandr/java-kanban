package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

public class Task {
    protected int id;
    protected String name;
    protected TaskStatus status;
    protected String description;
    protected LocalDateTime startTime;
    protected long duration;

    public Task(String name, TaskStatus status, String description, LocalDateTime startTime, long duration) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(int id, String name, TaskStatus status, String description) {
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public Task(String name, TaskStatus status, String description) {
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public Task(int id, String name, TaskStatus status, String description, LocalDateTime startTime, long duration) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public TaskEnum getTaskEnum() {
        return TaskEnum.TASK;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(Duration.ofMinutes(duration));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && status == task.status && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, description);
    }

    @Override
    public String toString() {
        return "\n" + "Task{" + "\n" +
                " id = " + id + "\n" +
                " Задача = '" + name + '\'' + "\n" +
                " Статус = " + status + "\n" +
                " Описание = '" + description + '\'' + "\n" +
                " Начало выполнения = " + getStartTime() + "\n" +
                " Продолжительность = " + getDuration() +
                '}';
    }
}
