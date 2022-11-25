## Проект JOB4J_TODO
### Сервис для постановки и выполнения задач
### Стек технологий:
- Java
- Maven
- Spring boot 
- Thymeleaf
- Bootstrap
- Hibernate
- Liquibase

### Запуск приложения:

Создаем базу данных командой 
```shell
create database todo_db;
```

Создаем таблицы с помощью Liquibase, далее производим запуска самого приложения командой 
```shell
mvn spring-boot:run
```

### Как использовать:

Всего есть три вида с разными задачами

#### Вид со всеми задачами

![](image/all_tasks.png)

### Вид с выполненными задачами

![](image/comleted_tasks.png)

### Вид с невыполненными задачами

![](image/not_completed_tasks.png)

### Детализация выполненной задачи

![](image/detail_page.png)

### Детализация невыполненной задачи

![](image/detail_not_completed.png)

Нажатие на кнопку "Выполнил" переведет статус на соответствующий.
На кнопку "Удалить" задача будет удалена.

### Обновление описания задачи

![](image/update.png)

### Для работы с приложением необходимо авторизоваться

![](image/login_page.png)

### Или зарегистрироваться

![](image/registration_page.png)

### Контакты: kshift84@gmail.com