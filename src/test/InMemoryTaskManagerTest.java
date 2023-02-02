package test;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import service.InMemoryTaskManager;

import java.time.LocalDateTime;


public class InMemoryTaskManagerTest extends TaskManagerTest <InMemoryTaskManager> {

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
        task = new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01,31,18,00), 60);
        task1 = new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01,31,18,00), 60);
        epic = new Epic("test", TaskStatus.NEW, "TestDes");
        subTask1 = new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01,31,18,00), 60, 1);
    }
}