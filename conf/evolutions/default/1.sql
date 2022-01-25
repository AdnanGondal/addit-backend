# --- !Ups

create table "people" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar not null,
  "age" int not null
);

# --- !Downs

drop table "people" if exists;
drop table "play_evolutions" if exists;