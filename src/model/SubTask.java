package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Task {
    private final int epicId;
    protected TaskEnum taskEnum;

    public SubTask(String name, TaskStatus status, String description, LocalDateTime startTime, long duration,
                   int epicId) {
        super(name, status, description, startTime, duration);
        this.epicId = epicId;
    }

    public SubTask(int id, String name, TaskStatus status, String description, LocalDateTime startTime, long duration,
                   int epicId) {
        super(id, name, status, description, startTime, duration);
        this.epicId = epicId;
    }

    @Override
    public TaskEnum getTaskEnum() {
        return TaskEnum.SUB_TASK;
    }

    public int getEpicId() {
        return epicId;
    }

    public LocalDateTime getEndTime(){
        return startTime.plus(Duration.ofMinutes(duration));
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public long getDuration(){
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicId == subTask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", taskEnum=" + TaskEnum.SUB_TASK +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
