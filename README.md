# Kanban
## Менеджер задач.


>Оглавление:
>1) Описание.
>2) Возможности
>3) Диаграмма классов

## Описание
![Java](https://img.shields.io/badge/java-11-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-v5.9.1-red.svg?style=flat&logo=JUnit&logoColor=white)
![Intellj_Idea](https://img.shields.io/badge/intellj_Idea-v2022.12-blue.svg?style=flat&logo=intellij-idea&logoColor=white)
![GSON](https://img.shields.io/badge/gson-v2.10-green.svg?style=flat&logo=gson&logoColor=white)

Менеджер задач котрый умеет хранить задачи, хранить историю просмотра, делить задачи на большие и обычные.
Эпичная задача(большая) включает в себя подзадачи. Если все подзадачи в статусе **DONE**, то эпичная задача станет так же **DONE**. Если хоть одна подзадача не выполнена, то эпик остаётся в статусе **IN_PROGRESS**.
Обычные задачи имеют три статуса **NEW** --> **IN_PROGRESS** --> **DOWN**.

## Возможности

>### Хранение истории просмотра задач

Менеджер запоминает какие задачи вызывались.

<img src="src/resources/history_task_manager.png" width="80%"/>

>### Диаграмма классов

<img src="src/resources/diagram.png" width="200%"/>


