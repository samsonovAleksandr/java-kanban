import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private final List<Integer> subTaskId = new ArrayList<>();

    public Epic(String name, TaskStatus status, String description) {
        super(name, status, description);
    }

    public void addSubTaskId(SubTask subTask) {
        subTaskId.add(subTask.getId());
    }

    public void removeSubTaskID(int subTaskId) {
        this.subTaskId.remove(subTaskId);
    }

    public List<Integer> getSubTaskId() {
        return subTaskId;
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
        return "Epic{" +
                "subTaskId=" + subTaskId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}