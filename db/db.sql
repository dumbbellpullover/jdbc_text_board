drop database if exists text_board;
create database text_board;
use text_board;

create table article (
	id int unsigned not null primary key auto_increment,
    regDate datetime not null,
    updateDate datetime not null,
    title char(100) not null,
    body text not null
);