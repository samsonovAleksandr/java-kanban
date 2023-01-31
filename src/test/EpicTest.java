package test;

import model.Epic;
import model.SubTask;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

        TaskManager taskManager;
    @BeforeEach
    public void beforeEach(){
         taskManager = new InMemoryTaskManager();
    }

    @Test
    public void shouldShowNewForEpicWithNewSubtasks(){
        Epic epic = new Epic("TestEpic", TaskStatus.NEW, "DiscriptTest");
        taskManager.createEpic(epic);
        taskManager.createSubTask(new SubTask("TestSub", TaskStatus.NEW, "DisTestSub", 1));
        taskManager.createSubTask(new SubTask("TestSub1", TaskStatus.NEW, "DisTestSub1", 1));
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void shouldShowInProgressForEpicWithNewAndDoneSubTask(){
        Epic epic = new Epic("TestEpic", TaskStatus.NEW, "DiscriptTest");
        taskManager.createEpic(epic);
        taskManager.createSubTask(new SubTask("TestSub", TaskStatus.NEW, "DisTestSub", 1));
        taskManager.createSubTask(new SubTask("TestSub1", TaskStatus.DONE, "DisTestSub1", 1));
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldShowDoneForEpicWithDoneSubTask(){
        Epic epic = new Epic("TestEpic", TaskStatus.NEW, "DiscriptTest");
        taskManager.createEpic(epic);
        taskManager.createSubTask(new SubTask("TestSub", TaskStatus.DONE, "DisTestSub", 1));
        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    public void shouldShowNewForEpicWithNoSubTask(){
        Epic epic = new Epic("TestEpic", TaskStatus.NEW, "DiscriptTest");
        taskManager.createEpic(epic);
        assertTrue(taskManager.getSubTasksByEpicId(1).isEmpty());
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void shouldShowInProgressForEpicWithInProgressSubTask(){
        Epic epic = new Epic("TestEpic", TaskStatus.NEW, "DiscriptTest");
        taskManager.createEpic(epic);
        taskManager.createSubTask(new SubTask("TestSub", TaskStatus.IN_PROGRESS, "DisTestSub", 1));
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }
}