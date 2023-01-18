package model;

import java.util.Objects;

public class Task {
    protected int id;
    protected String name;
    protected TaskStatus status;
    protected String description;
    protected TaskEnum taskEnum;

    public Task(String name, TaskStatus status, String description) {
        this.name = name;
        this.status = status;
        this.description = description;
    }
        public Task(int id, String name, TaskStatus status, String description) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
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
        return "model.Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
