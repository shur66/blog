CREATE TABLE users (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор',
  LOGIN varchar(50)  NOT NULL COMMENT 'Логин',
  PASSWORD varchar(100)  NOT NULL COMMENT 'Пароль',
  NAME varchar(100)  NOT NULL COMMENT 'ФИО',
  EMAIL varchar(50)  DEFAULT NULL COMMENT 'Электронный адрес',
  PRIMARY KEY (ID),
  UNIQUE INDEX INDX_LOGIN (LOGIN)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = 'Пользователи';

CREATE TABLE user_roles (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор',
  USER_ID int(11) DEFAULT NULL COMMENT 'Пользователь',
  ROLE varchar(20) DEFAULT NULL COMMENT 'Код роли',
  PRIMARY KEY (ID),
  INDEX INDX_USER (USER_ID),
  UNIQUE INDEX UK_user_roles (USER_ID, ROLE),
  CONSTRAINT FK_USER_ROLE_USER_ID FOREIGN KEY (USER_ID)
  REFERENCES users (ID) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = 'Роли пользователей';

CREATE TABLE posts (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор',
  USER_ID int(11) NOT NULL COMMENT 'Пользователь',
  CAPTION varchar(255) NOT NULL COMMENT 'Заголовок',
  BODY varchar(1000) NOT NULL COMMENT 'Тело',
  TAGS varchar(255) DEFAULT NULL COMMENT 'Теги',
  DATE datetime NOT NULL COMMENT 'Дата',
  PRIMARY KEY (ID),
  CONSTRAINT FK_POST_USER_ID FOREIGN KEY (USER_ID)
  REFERENCES users (ID) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = 'Посты пользователей';

CREATE TABLE post_comments (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор',
  POST_ID int(11) NOT NULL COMMENT 'Пост',
  USER_ID int(11) NOT NULL COMMENT 'Пользователь',
  COMMENT varchar(500) NOT NULL COMMENT 'Комментарий',
  DATE datetime NOT NULL COMMENT 'Дата',
  PRIMARY KEY (ID),
  UNIQUE INDEX INDX_ID (ID),
  CONSTRAINT FK_POST_COM_POST FOREIGN KEY (POST_ID)
  REFERENCES posts (ID) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_POST_COM_USER FOREIGN KEY (USER_ID)
  REFERENCES users (ID) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = 'Комментарии поста';