package test;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import service.TaskManager;

import java.util.ArrayList;
import java.util.Collection;

public abstract class TaskManagerTest<T extends TaskManager> {

    T taskManager;


    @Test
    void createNewTask() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
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
    void createSubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
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
        Task task1 = new Task("test", TaskStatus.NEW, "TestDes");
        Task task2 = new Task("test", TaskStatus.NEW, "TestDes");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        Collection<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        assertArrayEquals(tasks.toArray(), taskManager.getTasks().toArray());
    }

    @Test
    void getAllTaskWithEmptyTask() {
        Task task = null;
        taskManager.createTask(task);
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void getAllEpic() {
        Epic epic1 = new Epic("test", TaskStatus.NEW, "TestDes");
        SubTask subTask = new SubTask("test", TaskStatus.NEW, "TestDes", 1);
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask);
        Collection<Epic> epics = new ArrayList<>();
        epics.add(epic1);
        assertArrayEquals(epics.toArray(), taskManager.getEpics().toArray());
    }

    @Test
    void getAllEpicWithEmptyEpic() {
        Epic epic = null;
        taskManager.createEpic(epic);
        assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void getAllSubTask() {
        Epic epic1 = new Epic("test", TaskStatus.NEW, "TestDes");
        SubTask subTask = new SubTask("test", TaskStatus.NEW, "TestDes", 1);
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask);
        Collection<SubTask> subTasks = new ArrayList<>();
        subTasks.add(subTask);
        assertArrayEquals(subTasks.toArray(), taskManager.getAllSubtasks().toArray());
    }

    @Test
    void getAllSubTaskWithEmptySubTask() {
        Epic epic1 = new Epic("test", TaskStatus.NEW, "TestDes");
        SubTask subTask = null;
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask);
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    void getSubTaskByEpicId() {
        Epic epic1 = new Epic("test", TaskStatus.NEW, "TestDes");
        SubTask subTask = new SubTask("test", TaskStatus.NEW, "TestDes", 1);
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask);
        Collection<SubTask> subTasks = new ArrayList<>();
        subTasks.add(subTask);
        assertArrayEquals(subTasks.toArray(), taskManager.getSubTasksByEpicId(1).toArray());
    }

    @Test
    void getSubTaskByEpicIdWithEmptyEpicId() {
        Epic epic1 = new Epic("test", TaskStatus.NEW, "TestDes");
        SubTask subTask = new SubTask("test", TaskStatus.NEW, "TestDes", 1);
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask);
        Collection<SubTask> subTasks = taskManager.getSubTasksByEpicId(2);
        assertNull(subTasks);
    }

    @Test
    void getTaskById() {
        Task task1 = new Task("test", TaskStatus.NEW, "TestDes");
        taskManager.createTask(task1);
        assertEquals(task1, taskManager.getTaskByID(1));
    }

    @Test
    void getTaskByIdWithEmptyTask() {
        Task task1 = new Task("test", TaskStatus.NEW, "TestDes");
        taskManager.createTask(task1);
        Task task2 = taskManager.getTaskByID(2);
        assertNull(task2);
    }

    @Test
    void getEpicById() {
        Epic epic1 = new Epic("test", TaskStatus.NEW, "TestDes");
        taskManager.createEpic(epic1);
        assertEquals(epic1, taskManager.getEpicByID(1));
    }

    @Test
    void getEpicByIdWithEmptyEpic() {
        Epic epic1 = new Epic("test", TaskStatus.NEW, "TestDes");
        taskManager.createEpic(epic1);
        Epic epic2 = taskManager.getEpicByID(2);
        assertNull(epic2);
    }

    @Test
    void getSubTaskById() {
        Epic epic1 = new Epic("test", TaskStatus.NEW, "TestDes");
        SubTask subTask = new SubTask("test", TaskStatus.NEW, "TestDes", 1);
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask);
        assertEquals(subTask, taskManager.getSubtaskByID(2));
    }

    @Test
    void getSubTaskByIdWithEmptySubTask() {
        Epic epic1 = new Epic("test", TaskStatus.NEW, "TestDes");
        SubTask subTask = new SubTask("test", TaskStatus.NEW, "TestDes", 1);
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask);
        SubTask subTask1 = taskManager.getSubtaskByID(1);
        assertNull(subTask1);

    }

    @Test
    void deleteAllTask() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
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
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
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
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
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
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
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
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
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
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.deleteSubtaskById(3);
        assertFalse(taskManager.getSubTasksByEpicId(1).isEmpty());
    }

    @Test
    void deleteAllSubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.deleteAllSubTasks();
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    void updateTask() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
        taskManager.updateTask(new Task("test", TaskStatus.IN_PROGRESS, "TestDes"), 1);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getTaskByID(1).getStatus());
        taskManager.updateTask(new Task("test", TaskStatus.DONE, "TestDes"), 1);
        assertEquals(TaskStatus.DONE, taskManager.getTaskByID(1).getStatus());
    }

    @Test
    void updateTaskWithEmptyTask() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
        taskManager.updateTask(null, 1);
        assertEquals(TaskStatus.NEW, taskManager.getTaskByID(1).getStatus());
    }

    @Test
    void updateTaskWithIncorrectId() {
        taskManager.createTask(new Task("test", TaskStatus.NEW, "TestDes"));
        taskManager.updateTask(new Task("test", TaskStatus.NEW, "TestDes"), 2);
        assertNull(taskManager.getTaskByID(2));
    }

    @Test
    void updateEpic() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.updateEpic(new Epic("test", TaskStatus.IN_PROGRESS, "TestDes"), 1);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getEpicByID(1).getStatus());
    }

    @Test
    void updateEpicWithEmptyEpic() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
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
    void updateSubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.updateSubtask(new SubTask("test", TaskStatus.IN_PROGRESS, "TestDes", 1), 2);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getEpicByID(1).getStatus());
        taskManager.updateSubtask(new SubTask("test", TaskStatus.DONE, "TestDes", 1), 2);
        taskManager.updateSubtask(new SubTask("test", TaskStatus.DONE, "TestDes", 1), 3);
        assertEquals(TaskStatus.DONE, taskManager.getEpicByID(1).getStatus());
    }

    @Test
    void updateSubTaskWithEmptySubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.updateSubtask(null, 2);
        assertEquals(TaskStatus.NEW, taskManager.getEpicByID(1).getStatus());
    }

    @Test
    void updateSubTaskWithIncorrectIDSubTask() {
        taskManager.createEpic(new Epic("test", TaskStatus.NEW, "TestDes"));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.createSubTask(new SubTask("test", TaskStatus.NEW, "TestDes", 1));
        taskManager.updateSubtask(new SubTask("test", TaskStatus.IN_PROGRESS, "TestDes", 1), 4);
        assertNull(taskManager.getSubtaskByID(4));
    }


}