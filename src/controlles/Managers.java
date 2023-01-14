package controlles;

import controlles.HistoryManager;
import controlles.InMemoryHistoryManager;
import controlles.InMemoryTaskManager;

public abstract class Managers implements TaskManager {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();

    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
