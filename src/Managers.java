public abstract class Managers implements TaskManager {

    private static TaskManager TaskManager = new InMemoryTaskManager();
    private static HistoryManager InMemoryHistoryManager = new InMemoryHistoryManager();

    public static TaskManager getDefault() {
        return TaskManager;

    }

    public static HistoryManager getDefaultHistory() {
        return InMemoryHistoryManager;
    }
}
