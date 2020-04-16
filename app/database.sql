create table users (
  id serial primary key not null,
  username text not null unique,
  password text not null,
  created_date timestamp default now()
);

create table ideas (
  id serial primary key not null,
  title text not null,
  description text not null,
  created_date timestamp default now(),
  user_id integer references users(id)
);