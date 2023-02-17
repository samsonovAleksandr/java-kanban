package test;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import service.TaskManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public abstract class TaskManagerTest<T extends TaskManager> {

    T taskManager;

    Task task;
    Task task1;
    Epic epic;
    SubTask subTask1;

    SubTask subTask3;

    @Test
    void createNewTask() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        Collection<Task> tasks = taskManager.getTasks();
        assertEquals(1, tasks.size());
    }

    @Test
    void createNewEpic() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        Collection<Epic> epics = taskManager.getEpics();
        assertEquals(1, epics.size());
    }

    @Test
    void createSubTask() throws IOException, InterruptedException {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        Collection<SubTask> subTasks = taskManager.getAllSubtasks();
        assertEquals(1, subTasks.size());
    }

    @Test
    void createAndGetNewEpicWithEmptyEpic() {
        taskManager.createEpic(null);
        assertTrue(taskManager.getEpics().isEmpty());
        assertEquals(0, taskManager.getEpics().size());
    }

    @Test
    void createNewAndGetSubTaskWithEmptySubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(null);
        assertNull(taskManager.getSubtaskByID(2));
        assertEquals(0, taskManager.getSubTasksByEpicId(1).size());
    }

    @Test
    void createAndGetNewTaskWithEmptyTask() {
        taskManager.createTask(null);
        assertTrue(taskManager.getTasks().isEmpty());
        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void getAllTask() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 10), 60));
        Collection<Task> tasks = new ArrayList<>();
        tasks.add(taskManager.getTaskByID(1));
        tasks.add(taskManager.getTaskByID(2));
        assertArrayEquals(tasks.toArray(), taskManager.getTasks().toArray());
    }

    @Test
    void getAllTaskWithEmptyTask() {
        taskManager.createTask(null);
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void getAllEpic() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        Collection<Epic> epics = new ArrayList<>();
        epics.add(taskManager.getEpicByID(1));
        assertArrayEquals(epics.toArray(), taskManager.getEpics().toArray());
    }

    @Test
    void getAllEpicWithEmptyEpic() {
        taskManager.createEpic(null);
        assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void getAllSubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        SubTask subTask1 = new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1);
        Collection<SubTask> subTasks = new ArrayList<>();
        subTasks.add(subTask1);
        assertEquals(subTasks.size(), taskManager.getAllSubtasks().size());
    }

    @Test
    void getAllSubTaskWithEmptySubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(null);
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    void getSubTaskByEpicId() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        SubTask subTask1 = new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1);
        Collection<SubTask> subTasks = new ArrayList<>();
        subTasks.add(subTask1);
        assertEquals(subTasks.size(), taskManager.getSubTasksByEpicId(1).size());
    }

    @Test
    void getSubTaskByEpicIdWithEmptyEpicId() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        Collection<SubTask> subTasks = taskManager.getSubTasksByEpicId(2);
        assertNull(subTasks);
    }

    @Test
    void getTaskById() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        assertEquals(1, taskManager.getTaskByID(1).getId());
    }

    @Test
    void getTaskByIdWithEmptyTask() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        Task task2 = taskManager.getTaskByID(2);
        assertNull(task2);
    }

    @Test
    void getEpicById() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        assertEquals(1, taskManager.getEpicByID(1).getId());
    }

    @Test
    void getEpicByIdWithEmptyEpic() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        Epic epic2 = taskManager.getEpicByID(2);
        assertNull(epic2);
    }

    @Test
    void getSubTaskById() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        assertEquals(2, taskManager.getSubtaskByID(2).getId());
    }

    @Test
    void getSubTaskByIdWithEmptySubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        SubTask subTask1 = taskManager.getSubtaskByID(1);
        assertNull(subTask1);

    }

    @Test
    void deleteAllTask() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 10), 60));
        taskManager.deleteAllTasks();
        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void deleteAllTaskWithEmptyTask() {
        taskManager.createTask(null);
        taskManager.createTask(null);
        taskManager.deleteAllTasks();
        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void deleteTaskById() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 10), 60));
        taskManager.deleteTaskById(1);
        assertNull(taskManager.getTaskByID(1));
        assertNotNull(taskManager.getTaskByID(2));
    }

    @Test
    void deleteTaskByIdWithEmptyTask() {
        taskManager.createTask(null);
        taskManager.deleteTaskById(1);
        assertNull(taskManager.getTaskByID(1));
    }

    @Test
    void deleteAllEpics() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        taskManager.deleteAllEpics();
        assertTrue(taskManager.getEpics().isEmpty());
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    void deleteAllEpicsWithEmptyEpic() {
        taskManager.createEpic(null);
        taskManager.createEpic(null);
        taskManager.createSubTask(null);
        taskManager.deleteAllEpics();
        assertTrue(taskManager.getEpics().isEmpty());
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    void deleteEpicById() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        taskManager.deleteEpicById(1);
        assertNull(taskManager.getEpicByID(1));
        assertNull(taskManager.getSubTasksByEpicId(1));
        assertNotNull(taskManager.getEpicByID(2));
    }

    @Test
    void deleteEpicByIdWithEmptyEpic() {
        taskManager.createEpic(null);
        taskManager.deleteEpicById(1);
        assertNull(taskManager.getEpicByID(1));
    }

    @Test
    void deleteSubTaskById() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        taskManager.deleteSubtaskById(2);
        assertTrue(taskManager.getSubTasksByEpicId(1).isEmpty());
    }

    @Test
    void deleteSubTaskByIdWithEmptySubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(null);
        taskManager.deleteSubtaskById(2);
        assertTrue(taskManager.getSubTasksByEpicId(1).isEmpty());
    }

    @Test
    void deleteSubTaskByIdWithIncorrectId() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        taskManager.deleteSubtaskById(3);
        assertFalse(taskManager.getSubTasksByEpicId(1).isEmpty());
    }

    @Test
    void deleteAllSubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 10), 60, 1));
        taskManager.deleteAllSubTasks();
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    void updateTask() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        taskManager.updateTask(new Task("test", TaskStatus.IN_PROGRESS, "TestDes",
                LocalDateTime.of(2022, 12, 4, 18, 01), 60), 1);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getTaskByID(1).getStatus());
        taskManager.updateTask(new Task("test", TaskStatus.DONE, "TestDes",
                LocalDateTime.of(2024, 01, 31, 18, 40), 60), 1);
        assertEquals(TaskStatus.DONE, taskManager.getTaskByID(1).getStatus());
    }

    @Test
    void updateTaskWithEmptyTask() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        taskManager.updateTask(null, 1);
        assertEquals(TaskStatus.NEW, taskManager.getTaskByID(1).getStatus());
    }

    @Test
    void updateTaskWithIncorrectId() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60));
        taskManager.updateTask(new Task("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60), 2);
        assertNull(taskManager.getTaskByID(2));
    }

    @Test
    void updateEpic() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 10), 60, 1));
        taskManager.updateEpic(new Epic("test", TaskStatus.IN_PROGRESS, "TestDes"), 1);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getEpicByID(1).getStatus());
    }

    @Test
    void updateEpicWithEmptyEpic() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 10), 60, 1));
        taskManager.updateEpic(new Epic("test", TaskStatus.NEW, "TestDes"), 1);
        taskManager.updateEpic(null, 1);
        assertEquals(TaskStatus.NEW, taskManager.getEpicByID(1).getStatus());
    }

    @Test
    void updateEpicWithIncorrectIdEpic() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.updateEpic(new Epic("test", TaskStatus.NEW, "TestDes"), 2);
        assertNull(taskManager.getEpicByID(2));
    }

    @Test
    void updateSubTaskInProgress() {
        taskManager.createEpic(new Epic("EPIC", TaskStatus.NEW, "EPIC1"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        taskManager.updateSubtask(new SubTask("test", TaskStatus.IN_PROGRESS, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 00), 60, 1), 2);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getEpicByID(1).getStatus());
    }

    @Test
    void updateSubTaskDone() {
        taskManager.createEpic(new Epic("EPIC", TaskStatus.NEW, "EPIC1"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        taskManager.updateSubtask(new SubTask("test", TaskStatus.DONE, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 00), 60, 1), 2);
        assertEquals(TaskStatus.DONE, taskManager.getEpicByID(1).getStatus());
    }

    @Test
    void updateSubTaskWithEmptySubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 10), 60, 1));
        taskManager.updateSubtask(null, 2);
        assertEquals(TaskStatus.NEW, taskManager.getEpicByID(1).getStatus());
    }

    @Test
    void updateSubTaskWithIncorrectIDSubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 00), 60, 1));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes",
                LocalDateTime.of(2022, 01, 31, 18, 10), 60, 1));
        taskManager.updateSubtask(new SubTask("test", TaskStatus.IN_PROGRESS, "TestDes",
                LocalDateTime.of(2023, 01, 31, 18, 10), 60, 1), 4);
        assertNull(taskManager.getSubtaskByID(4));
    }


}