create schema split_string;
create table string_model(
                             str_id int generated by default as identity primary key ,
                             str varchar (100) not null
);