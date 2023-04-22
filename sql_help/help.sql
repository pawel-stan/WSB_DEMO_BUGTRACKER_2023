insert into person(id, email, password, real_name, username)
VALUES (nextval('person_seq'), 'email@email.com', 'haslo', 'Kasia D', 'kasia-admin');

select *
from person;

insert into project(id, date_created, description, enabled, name, creator_id)
VALUES (nextval('project_seq'), now(), 'Słaby projekt!! 0/10', true, 'Kiepski projekt', 1),
       (nextval('project_seq'), now(), 'Projekt AAAAAA', true, 'Projekt A', 1),
       (nextval('project_seq'), now(), 'Projekt X', true, 'Projekt X', 1),
       (nextval('project_seq'), now(), 'Trackowanie błędów w aplikacji', true, 'BugTracker', 1);

select *
from project;

insert into project(id, date_created, description, enabled, name, creator_id)
select nextval('project_seq'),
       now(),
       'Opis ' || number,
       true,
       'Nazwa ' || number,
       (number % 4) * 50 +1
from generate_series(1, 1000) as number;