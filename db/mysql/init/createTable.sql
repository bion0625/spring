
create table users(
    id varchar(10) primary key,
    name varchar(20) not null,
    password varchar(10) not null,
    email varchar(20) not null,
    level tinyint not null,
    login int not null,
    recommend int not null
);

create table MEMBER(
      id int(10) primary key,
      name varchar(20) not null,
      point float null
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

DELIMITER //

create function find_name(in_id INT)
returns varchar(255)
begin
    declare out_name varchar(255);
    select name
        into out_name
        from member
        where id = in_id;
    return out_name;
end; //

DELIMITER ;