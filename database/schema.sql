create table user(
user_id VARCHAR(8) NOT NULL PRIMARY KEY,
username VARCHAR(50),
name VARCHAR(50),
CONSTRAINT unique_username UNIQUE (username)
);

insert into user(user_id, username, name) VALUES
('aaaabbbb', 'ncl', 'chin ling'),
('ccccdddd', 'sws', 'wenshan'),
('eeeeffff', 'machu', 'pikachu'),
('gggghhhh', 'coolio', 'LL Cool J');


create table task(
task_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
description VARCHAR(255) NOT NULL,
priority INT NOT NULL,
due_date DATE NOT NULL,
username VARCHAR(50),
FOREIGN KEY (username) REFERENCES user(username)
);
