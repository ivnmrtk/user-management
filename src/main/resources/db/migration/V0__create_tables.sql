CREATE TABLE IF NOT EXISTS user(
  id LONG PRIMARY KEY,
  login varchar(20) NOT NULL,
  password varchar(10) NOT NULL,
  blocked BOOLEAN NOT NULL DEFAULT FALSE
);