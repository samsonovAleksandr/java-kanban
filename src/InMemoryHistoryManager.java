import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    public Node head;
    public Node tail;

    public HashMap<Integer, Node> hist = new HashMap<>();

    @Override
    public void add(Task task) {
        remove(task.getId());
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        Node node = hist.get(id);
        if (node == null) {
            return;
        }
        removeNode(node);
    }

    public void removeNode(Node node) {
        if (node.next == null && node.prev == null) {
            head = null;
            tail = null;
        }
        if (node.next == null && node.prev != null) {
            node.prev.next = null;
            tail = node.prev;
        }
        if (node.prev == null && node.next != null) {
            node.next.prev = null;
            head = node.next;
        }
        if (node.prev != null & node.next != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    void linkLast(Task task) {
        Node node = new Node(task, null, tail);
        if (tail == null) {
            tail = node;
            head = node;
        } else {
            tail = node;
            tail.prev.next = node;
        }
        hist.put(node.data.getId(), node);
    }

    public List<Task> getTasks() {
        List<Task> histTasks = new ArrayList<>();
        Node node = head;
        while (node != null) {
            histTasks.add(node.data);
            node = node.next;
        }
        return histTasks;
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "hist=" + hist +
                '}';
    }
}
