drop table if exists customer_v2;
drop table if exists customer_t

create table if not exists customer_t (c_id tinyint not null,c_d_id smallint not null,c_w_id smallint not null,c_first_name  varchar(100),c_last_name  varchar(100), c_street varchar(100), c_city varchar(20), c_state char(2), c_zip char(9), primary key (c_id, c_w_id, c_d_id) ) Engine=InnoDB;
create table if not exists customer_v2 (id int not null auto_increment primary key, c_id tinyint not null,c_d_id smallint not null,c_w_id smallint not null,c_first_name  varchar(100),c_last_name  varchar(100), c_street varchar(100), c_city varchar(20), state char(2), c_zip char(9) ) Engine=InnoDB;



load data local infile '/tmp/customer.txt' into table customer_t;

insert into customer_v2 (c_id, c_d_id, c_w_id, c_first_name, c_last_name, c_street, c_city, state, c_zip) select c_id, c_d_id, c_w_id, c_first_name, c_last_name, c_street, c_city, c_state, c_zip from customer_t;
