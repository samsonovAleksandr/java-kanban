public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();
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

        taskManager.getTaskByID(1);
        System.out.println(taskManager.getHistory());
        taskManager.getTaskByID(2);
        System.out.println(taskManager.getHistory());
        taskManager.getEpicByID(3);
        System.out.println(taskManager.getHistory());
        taskManager.getSubtaskByID(4);
        System.out.println(taskManager.getHistory());
        taskManager.getSubtaskByID(5);
        System.out.println(taskManager.getHistory());
        taskManager.getEpicByID(6);
        System.out.println(taskManager.getHistory());
        taskManager.getSubtaskByID(7);
        System.out.println(taskManager.getHistory());
        taskManager.getTaskByID(1);
        System.out.println(taskManager.getHistory());
        taskManager.getTaskByID(2);
        System.out.println(taskManager.getHistory());
        taskManager.getEpicByID(3);
        System.out.println(taskManager.getHistory());
        taskManager.getSubtaskByID(4);
        System.out.println(taskManager.getHistory());


    }
}
