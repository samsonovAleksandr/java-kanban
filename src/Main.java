import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import service.FileBackedTasksManager;

import java.io.File;
import java.time.LocalDateTime;

import static service.FileBackedTasksManager.loadFromFile;

public class Main {
    public static void main(String[] args) {
       FileBackedTasksManager taskManager = new FileBackedTasksManager(new File("src/resources/history.csv"));
        taskManager.createTask(new Task("tset1", TaskStatus.NEW, "test1",
                LocalDateTime.of(2023, 1,9,10,0), 60));
     taskManager.createTask(new Task("tset1", TaskStatus.NEW, "test1",
             LocalDateTime.of(2022, 1,9,10,0), 60));
        taskManager.createEpic(new Epic("EPIC", TaskStatus.NEW, "EPIC_DESCRIPTION"));
        taskManager.createSubTask(new SubTask("SUBTASK3.1", TaskStatus.NEW, "SUBTASK_DESCRI3.1",
                LocalDateTime.of(2023, 2, 5,10, 0), 60, 3));
        taskManager.createSubTask(new SubTask("SUBTASK3.2", TaskStatus.NEW, "SUBTASK_DESCRI3.1",
                LocalDateTime.of(2023, 2, 5,12, 0), 60, 3));
        taskManager.createSubTask(new SubTask("SUBTASK3.3", TaskStatus.NEW, "SUBTASK_DESCRI3.1",
                LocalDateTime.of(2023, 2, 5,15, 0), 60, 3));
        taskManager.getTaskByID(1);
        taskManager.getEpicByID(2);
        taskManager.getSubTasksByEpicId(2);
        taskManager.getTaskByID(1);
        taskManager.getEpicByID(2);
        taskManager.getSubtaskByID(3);

        System.out.println(taskManager.getPrioritizedTasks());






       /*  FileBackedTasksManager taskManager = loadFromFile(new File("src/resources/history.csv"));


        System.out.println(taskManager.getPrioritizedTasks());

        System.out.println(taskManager.getHistory());*/

    }
}
      /*  TaskManager taskManager = new InMemoryTaskManager();
        taskManager.createTask(new Task("Погулять с собакой", TaskStatus.NEW, "Выйти в 6 вечера"));
        taskManager.createTask(new Task("Купить инструмент", TaskStatus.NEW, "Гаечные ключи"));
        taskManager.createEpic(new Epic("Генеральная уборка", TaskStatus.NEW, "Убрать квартиру"));
        taskManager.createSubTask(new SubTask("Помыть ванную", TaskStatus.NEW, "Помыть полки",
                3));
        taskManager.createSubTask(new SubTask("Убрать кухню",
                TaskStatus.NEW, "Разложить тарелки из посудомойки", 3));
        taskManager.createSubTask(new SubTask("Убрать Комнату",
                TaskStatus.NEW, "Пропылесосить", 3));
        taskManager.createEpic(new Epic("Подготовка к походу", TaskStatus.NEW, "Собрать вещи"));


        taskManager.getTaskByID(1);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getTaskByID(2);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getEpicByID(3);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getEpicByID(7);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getTaskByID(1);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getTaskByID(2);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getEpicByID(3);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getEpicByID(7);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getTaskByID(1);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getTaskByID(2);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getEpicByID(3);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getEpicByID(7);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.getTaskByID(1);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.deleteEpicById(3);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.deleteEpicById(7);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.deleteTaskById(1);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
        taskManager.deleteTaskById(2);
        System.out.println(taskManager.getHistory());
        System.out.println("**************************");
    }*/


