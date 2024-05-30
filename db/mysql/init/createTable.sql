-- 유저 테이블 생성

create table users(
    id varchar(10) primary key,
    name varchar(20) not null,
    password varchar(10) not null,
    email varchar(20) not null,
    level tinyint not null,
    login int not null,
    recommend int not null
);

create table member(
      id varchar(10) primary key,
      name varchar(20) not null,
      point float null
);

create table register(
    id int primary key auto_increment,
    name varchar(100) not null
);