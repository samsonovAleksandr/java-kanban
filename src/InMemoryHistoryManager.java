import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    public static final int MAX_LENGTH_LIST = 10;
    private LinkedList<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (history.size() >= MAX_LENGTH_LIST) {
            history.pollFirst();
        }
        history.addLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "history=" + history +
                '}';
    }
}
