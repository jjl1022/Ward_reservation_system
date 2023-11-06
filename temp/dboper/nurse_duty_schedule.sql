create table nurse_duty_schedule
(
    nurse_duty_id varchar(10) not null
        primary key,
    nurse_id      varchar(10)
        references nurse_info_table,
    duty_time     varchar(50),
    department_id varchar(10)
        references department_info_table,
    duty_type     varchar(50)
)
    with (orientation = row, compression = no);

alter table nurse_duty_schedule
    owner to dboper;

