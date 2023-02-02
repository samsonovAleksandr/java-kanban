package test;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    TaskManager taskManager;
    HistoryManager historyManager;
    Task task;
    Task task1;
    Epic epic;
    Epic epic1;
    SubTask subTask;


    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
        historyManager = Managers.getDefaultHistory();
        task = new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01,31,18,00), 60);
        task1 = new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01,31,18,00), 60);
        epic = new Epic("test", TaskStatus.NEW, "TestDes");
        epic1 = new Epic("test", TaskStatus.NEW, "TestDes");
        subTask = new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01,31,18,00), 60, 3);
        taskManager.createTask(task);
        taskManager.createTask(task1);
        taskManager.createEpic(epic);
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask);
    }

    @Test
    void addTest() {
        taskManager.getTaskByID(1);
        taskManager.getTaskByID(2);
        taskManager.getEpicByID(3);
        taskManager.getEpicByID(4);
        taskManager.getSubtaskByID(5);
        assertEquals(5, taskManager.getHistory().size());
    }

    @Test
    void addTaskIncorrectId() {
        taskManager.getTaskByID(6);
        assertEquals(0, taskManager.getHistory().size());
    }

    @Test
    void addTaskNull() {
        historyManager.add(null);
        assertEquals(0, taskManager.getHistory().size());
    }

    @Test
    void removeTest() {
        taskManager.getTaskByID(1);
        taskManager.getTaskByID(2);
        taskManager.getEpicByID(3);
        taskManager.getEpicByID(4);
        taskManager.getSubtaskByID(5);
        taskManager.deleteAllTasks();
        assertEquals(3, taskManager.getHistory().size());
    }

    @Test
    void removeIncorredIdTask(){
        taskManager.getTaskByID(1);
        taskManager.getTaskByID(2);
        taskManager.deleteTaskById(3);
        assertEquals(2, taskManager.getHistory().size());
    }
}