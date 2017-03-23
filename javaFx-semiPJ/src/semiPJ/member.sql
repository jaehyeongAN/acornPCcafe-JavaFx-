drop table member;

create table member(
code int primary key,
name varchar(25) not null,
id varchar(25) not null,
birth varchar(25) not null,
phone varchar(25) not null,
times double null
);

insert into member values(1, '안재형', 'wogud', '91/07/26', '010-111-1111', 1.51);
insert into member values(16, '저승이', 'wldms', '92/05/24', '010-222-2222', 2.44);
insert into member values(4, '고은정', 'dmswjd', '93/01/03', '010-333-3333', 8.27);
insert into member values(18, '도깨비', 'Roql', '94/04/30', '010-444-4444', 5.13);
insert into member values(7, '유지은', 'tmddl', '95/08/04', '010-555-5555', 6.40); 
insert into member values(13, '김사부', 'tkqn', '96/06/11', '010-666-6666', 2.45);
insert into member values(14, '써니', 'Sunhee', '89/11/18', '010-777-7777', 5.50);
insert into member values(20, '홍길동', 'hong', '22/10/01', '010-888-8888', 10);
insert into member values(9, '이순신', 'leess', '18/03/28', '010-999-9999', 4.20);
insert into member values(22, '강감찬', 'Gang', '14/02/15', '010-123-1234', 7.43);
insert into member values(11, '유관순', 'Yoogs', '78/10/09', '010-456-7890', 3.21);
insert into member values(25, '김태희', 'Kimth', '89/10/11', '010-643-0670', 5.32);

select * from member;
commit work;