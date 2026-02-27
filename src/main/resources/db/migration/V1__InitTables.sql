CREATE TABLE Author (
  author_id SERIAL PRIMARY KEY,

  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL,
  username VARCHAR(100) NOT NULL
);
CREATE UNIQUE INDEX ON Author (email);

CREATE TABLE Book (
  book_id SERIAL PRIMARY KEY,

  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  published_on DATE NOT NULL,

  author_id INT,
  FOREIGN key (author_id) REFERENCES Author (author_id)
);
CREATE UNIQUE INDEX ON Book (title);

CREATE TABLE Comment (
  comment_id SERIAL PRIMARY KEY,

  name VARCHAR(100) NOT NULL,
  content TEXT NOT NULL,
  published_on TIMESTAMP NOT NULL,
  updated_on TIMESTAMP,

  book_id INT NOT NULL,
  FOREIGN key (book_id) REFERENCES Book (book_id)
);