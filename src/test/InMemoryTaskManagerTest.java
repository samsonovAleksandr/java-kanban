package test;

import org.junit.jupiter.api.BeforeEach;
import service.InMemoryTaskManager;


public class InMemoryTaskManagerTest extends TaskManagerTest <InMemoryTaskManager> {

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }
}