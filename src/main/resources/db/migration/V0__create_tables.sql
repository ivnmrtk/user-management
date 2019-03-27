CREATE TABLE IF NOT EXISTS user(
  id LONG NOT NULL,
  login varchar(20) NOT NULL,
  password varchar(10) NOT NULL,
  blocked BOOLEAN NOT NULL DEFAULT FALSE
);