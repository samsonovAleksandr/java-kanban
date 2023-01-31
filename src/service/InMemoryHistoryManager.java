package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private Node head;
    private Node tail;

    private HashMap<Integer, Node> hist = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task != null){
            remove(task.getId());
            linkLast(task);
        }

    }

    @Override
    public void remove(int id) {
        Node node = hist.get(id);
        if (node == null) {
            return;
        }
        removeNode(node);
    }

    private void removeNode(Node node) {
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

    private void linkLast(Task task) {
        Node node = new Node(task, null, tail);
        if (tail == null) {
            tail = node;
            head = node;
        } else {
            tail.next = node;
            tail = node;
        }
        hist.put(node.data.getId(), node);
    }

    private List<Task> getTasks() {
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

    private class Node {

        private Task data;
        private Node next;
        private Node prev;

        public Node(Task data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}
