create table _user (
   id SERIAL PRIMARY KEY,
   public_id varchar(100),
   email varchar(100),
   password varchar(100),
   role varchar(100)
);