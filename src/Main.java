public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        taskManager.createTask(new Task("Погулять с собакой", TaskStatus.NEW, "Выйти в 6 вечера"));
        taskManager.createTask(new Task("Купить инструмент", TaskStatus.NEW, "Гаечные ключи"));
        taskManager.createEpic(new Epic("Генеральная уборка", TaskStatus.NEW, "Убрать квартиру"));
        taskManager.createSubTask(new SubTask("Помыть ванную", TaskStatus.NEW, "Помыть полки",
                3));
        taskManager.createSubTask(new SubTask("Убрать кухню",
                TaskStatus.NEW, "Разложить тарелки из посудомойки", 3));
        taskManager.createEpic(new Epic("Подготовка к походу", TaskStatus.NEW, "Собрать вещи"));
        taskManager.createSubTask(new SubTask("Собрать рюкзак", TaskStatus.NEW,
                "Положить одежду и посуду", 6));
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtaskByID(5));
        taskManager.updateTask(new Task("Погулять с собакой", TaskStatus.IN_PROGRESS, "Выйти в 4 часа"), 1);
        taskManager.updateEpic(new Epic("Подготовка к походу", TaskStatus.IN_PROGRESS, "Купить компас"), 6);
        System.out.println(taskManager.getTaskByID(1));
        System.out.println(taskManager.getEpics());
        taskManager.updateSubtask(new SubTask("Собрать рюкзак", TaskStatus.DONE, "Положить полотенце",
                6), 7);
        System.out.println(taskManager.getEpics());
        taskManager.deleteAllSubTasks();
        System.out.println(taskManager.getSubtaskByID(4));
        taskManager.deleteTaskById(1);
        taskManager.deleteEpicById(3);

    }
}
