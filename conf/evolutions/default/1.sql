# --- !Ups

drop table if exists votes;
drop table if exists stories;
drop table if exists sessions;
drop table if exists users;


CREATE TABLE stories (
  id SERIAL PRIMARY KEY,
  title TEXT NOT NULL,
  url TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  email TEXT UNIQUE NOT NULL,
  password_encrypted TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  ) ;


CREATE TABLE votes (
    id SERIAL PRIMARY KEY,
    direction TEXT NOT NULL DEFAULT 'up',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    story_id INTEGER,
    user_id INTEGER,
    FOREIGN KEY(story_id) REFERENCES stories(id),
    FOREIGN KEY(user_id) REFERENCES users(id)
) ;


CREATE TABLE sessions (
  uuid TEXT PRIMARY KEY,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  user_id INTEGER,
  FOREIGN KEY(user_id) REFERENCES users(id)
) ;



# --- !Downs

drop table if exists votes;
drop table if exists stories;
drop table if exists sessions;
drop table if exists users;

