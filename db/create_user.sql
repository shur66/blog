insert into users (ID, LOGIN, PASSWORD, NAME, EMAIL) values (1, 'admin', '356A192B7913B04C54574D18C28D46E6395428AB', 'Администратор', 'blog@mail.ru');
insert into user_roles (USER_ID, ROLE) values (1, 'ADMIN');
insert into user_roles (USER_ID, ROLE) values (1, 'USER');

insert into users (ID, LOGIN, PASSWORD, NAME, EMAIL) values (2, 'user', '356A192B7913B04C54574D18C28D46E6395428AB', 'Иванов Иван Иванович', 'ivan@mail.ru');
insert into user_roles (USER_ID, ROLE) values (2, 'USER');

commit;

