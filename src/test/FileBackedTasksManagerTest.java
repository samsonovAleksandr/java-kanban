package test;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileBackedTasksManager;
import service.TaskManager;

import static service.FileBackedTasksManager.loadFromFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @BeforeEach
    void beforeEach() {
        Path path = Paths.get("src/resources/history.csv");
        taskManager = new FileBackedTasksManager(new File("src/resources/history.csv"));
        File file = taskManager.getFile();
        try {
            if (file.exists()) {
                Files.delete(path);
                file = Files.createFile(path).toFile();
            } else {
                file = new File("src/resources/history.csv");
            }
            taskManager = new FileBackedTasksManager(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void loadFromFileTest() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 10), 60));
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2024, 01, 31, 18, 20), 60, 3));
        taskManager.getTaskByID(1);
        taskManager.getEpicByID(3);
        TaskManager taskManager1 = loadFromFile(new File("src/resources/history.csv"));
        int allSize = taskManager.getTasks().size() + taskManager.getEpics().size() +
                taskManager.getAllSubtasks().size();
        int allSizeTS1 = taskManager1.getTasks().size() + taskManager1.getEpics().size() +
                taskManager1.getAllSubtasks().size();
        assertEquals(allSize, allSizeTS1);
    }

    @Test
    void loadFromFileNullFile() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 10), 60));
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2024, 01, 31, 18, 20), 60, 3));
        taskManager.getTaskByID(1);
        taskManager.getEpicByID(3);
        assertThrows(RuntimeException.class, () -> {
            loadFromFile(null);
        });
    }

    @Test
    void EmptyTasksListAfterLoadFile() {
        TaskManager taskManager1 = loadFromFile(new File("src/resources/history.csv"));
        assertEquals(0, taskManager1.getTasks().size());
    }

    @Test
    void EmptySubtaskListInEpicAfterLoadFile() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        TaskManager taskManager1 = loadFromFile(new File("src/resources/history.csv"));
        assertEquals(0, taskManager1.getEpicByID(1).getSubTaskId().size());
    }

    @Test
    void EmptyHistoryListAfterLoadFile() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 10), 60));
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2024, 01, 31, 18, 20), 60, 3));
        TaskManager taskManager1 = loadFromFile(new File("src/resources/history.csv"));
        assertEquals(0, taskManager1.getHistory().size());
    }

}