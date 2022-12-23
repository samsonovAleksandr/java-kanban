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
    }
}
