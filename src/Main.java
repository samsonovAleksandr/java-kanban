public class Main extends TuskManager {
    public static void main(String[] args) {
        TuskManager tuskManager = new TuskManager();
        tuskManager.createTask(new Task("Погулять с собакой", TaskStatus.NEW, "Выйти в 6 вечера"));
        tuskManager.createTask(new Task("Купить инструмент", TaskStatus.NEW, "Гаечные ключи"));
        tuskManager.createEpic(new Epic("Генеральная уборка", TaskStatus.NEW, "Убрать квартиру"));
        tuskManager.createSubTask(new SubTask("Помыть ванную", TaskStatus.NEW, "Помыть полки",
                3));
        tuskManager.createSubTask(new SubTask("Убрать кухню",
                TaskStatus.NEW, "Разложить тарелки из посудомойки", 3));
        tuskManager.createEpic(new Epic("Подготовка к походу", TaskStatus.NEW, "Собрать вещи"));
        tuskManager.createSubTask(new SubTask("Собрать рюкзак", TaskStatus.NEW,
                "Положить одежду и посуду", 6));
        System.out.println(tuskManager.getTasks());
        System.out.println(tuskManager.getEpics());
        System.out.println(tuskManager.getSubtaskByID(5));
        tuskManager.updateTask(new Task("Погулять с собакой", TaskStatus.IN_PROGRESS, "Выйти в 4 часа"));
        tuskManager.updateEpic(new Epic("Подготовка к походу", TaskStatus.IN_PROGRESS, "Купить компас"));
        System.out.println(tuskManager.getEpics());
        tuskManager.updateSubtask(new SubTask("Собрать рюкзак", TaskStatus.DONE, "Положить полотенце",
                6));
        System.out.println(tuskManager.getEpics());
        tuskManager.deleteTaskById(1);
        tuskManager.deleteEpicById(3);

    }
}
