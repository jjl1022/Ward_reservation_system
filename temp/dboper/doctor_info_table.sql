create table doctor_info_table
(
    doctor_id     varchar(10) not null
        primary key,
    department_id varchar(10)
        references department_info_table,
    doctor_name   varchar(50),
    doctor_sex    varchar(50),
    doctor_age    varchar(3)
)
    with (orientation = row, compression = no);

alter table doctor_info_table
    owner to dboper;

