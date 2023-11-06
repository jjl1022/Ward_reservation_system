create table department_info_table
(
    department_id      varchar(10) not null
        primary key,
    department_name    varchar(50),
    department_address varchar(50),
    department_phone   varchar(15)
)
    with (orientation = row, compression = no);

alter table department_info_table
    owner to dboper;

