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

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @BeforeEach
    void beforeEach() {
        taskManager = new FileBackedTasksManager(new File("src/resources/history.csv"));
    }

    @Test
    void loadFromFileTest() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 3));
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
    void loadFromFileNullFile(){
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 3));
        taskManager.getTaskByID(1);
        taskManager.getEpicByID(3);
        assertThrows(RuntimeException.class, () -> {loadFromFile(null);});
    }
}