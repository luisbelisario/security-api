create table _user (
   id INT NOT NULL AUTO_INCREMENT,
   public_id varchar(100),
   login varchar(100),
   password varchar(100),
   role varchar(100),
   PRIMARY KEY ( id )
);