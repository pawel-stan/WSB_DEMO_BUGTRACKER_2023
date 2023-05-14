INSERT INTO person(id, email, password, real_name, username)
VALUES (NEXTVAL('person_seq'), 'email@email.com', 'haslo', 'Kasia D', 'kasia-admin');

INSERT INTO person(id, email, password, real_name, username)
VALUES (NEXTVAL('person_seq'), 'pawel@email.com', 'haslo', 'Paweł S', 'pawel-admin');

SELECT *
FROM person;

INSERT INTO project(id, date_created, description, enabled, name, creator_id)
VALUES (NEXTVAL('project_seq'), NOW(), 'Słaby projekt!! 0/10', TRUE, 'Kiepski projekt', 1),
       (NEXTVAL('project_seq'), NOW(), 'Projekt AAAAAA', TRUE, 'Projekt A', 1),
       (NEXTVAL('project_seq'), NOW(), 'Projekt X', TRUE, 'Projekt X', 1),
       (NEXTVAL('project_seq'), NOW(), 'Trackowanie błędów w aplikacji', TRUE, 'BugTracker', 1);

SELECT *
FROM project;

INSERT INTO project(id, date_created, description, enabled, name, creator_id)
SELECT NEXTVAL('project_seq'),
       NOW(),
       'Opis ' || number,
       TRUE,
       'Nazwa ' || number,
       (number % 4) * 50 + 1
FROM GENERATE_SERIES(1, 1000) AS number;