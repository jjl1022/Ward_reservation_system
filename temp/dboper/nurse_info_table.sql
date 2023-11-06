create table nurse_info_table
(
    nurse_id      varchar(10) not null
        primary key,
    department_id varchar(50)
        references department_info_table,
    nurse_name    varchar(50),
    nurse_sex     varchar(2),
    nurse_age     varchar(3)
)
    with (orientation = row, compression = no);

alter table nurse_info_table
    owner to dboper;

