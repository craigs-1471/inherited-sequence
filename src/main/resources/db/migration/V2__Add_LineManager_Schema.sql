CREATE TABLE line_managers
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    age        INTEGER                                 NOT NULL,
    username   VARCHAR(255),
    password   VARCHAR(255),
    CONSTRAINT pk_line_managers PRIMARY KEY (id)
);

ALTER TABLE employees
    ADD line_manager_id BIGINT;

ALTER TABLE employees
    ADD CONSTRAINT FK_EMPLOYEES_ON_LINE_MANAGER FOREIGN KEY (line_manager_id) REFERENCES line_managers (id);