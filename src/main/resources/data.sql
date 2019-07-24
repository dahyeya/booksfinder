DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS keywords;
DROP TABLE IF EXISTS top10;

CREATE TABLE user (
  id INT AUTO_INCREMENT,
  account VARCHAR(64) PRIMARY KEY,
  password VARCHAR(256) NOT NULL
);

CREATE TABLE keywords(
  id INT NOT NULL,
  keyword VARCHAR(64) NOT NULL,
  issue DATETIME NOT NULL,
  search INT NOT NULL,
  PRIMARY KEY(id, keyword)
);

CREATE TABLE top10(
  keyword VARCHAR(64) PRIMARY KEY,
  search INT NOT NULL
);

INSERT INTO user (account, password) VALUES
('mschu', HASH('SHA256', '1234', 64)),
('test', HASH('SHA256', '1234', 64));

INSERT INTO keywords (id, keyword, issue, search) VALUES
(1, 'books', now(), 30),
(2, 'books', now(), 30),
(1, 'finder', now(), 44),
(2, 'finder', now(), 44);


INSERT INTO top10 (keyword, search) VALUES
('books', 60),
('finder', 88);
