create table if not exists short_link (
        id int not null primary key,
        url varchar(1000),
        create_time DATE );
