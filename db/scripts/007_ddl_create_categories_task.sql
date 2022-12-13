create table if not exists categories_tasks(
    id serial primary key,
    task_id int not null,
    category_id int not null
);