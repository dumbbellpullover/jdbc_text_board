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
select * from article;

create table member (
	id int unsigned not null primary key auto_increment,
    regDate datetime not null,
    updateDate datetime not null,
    loginId char(30) not null,
    loginPw char(30) not null,
    name char(20) not null
);

# 임시회원
insert into member (regDate, updateDate, loginId, loginPw, name)
values (now(), now(), 'testId1', 'testPw1', 'testName1');

insert into member (regDate, updateDate, loginId, loginPw, name)
values (now(), now(), 'testId1', 'testPw1', 'testName2');

# 게시물 테이블에 memberId 컬럼 추가
alter table article add column memberId int(10) unsigned not null after updateDate;

# 조인해서 작성자 찾기
select * from member as M join article as A on M.id = A.memberId;