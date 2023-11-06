create table doctor_duty_schedule
(
    doctor_duty_id varchar(10) not null
        primary key,
    doctor_id      varchar(10)
        references doctor_info_table,
    duty_time      varchar(50),
    department_id  varchar(10)
        references department_info_table,
    duty_type      varchar(50)
)
    with (orientation = row, compression = no);

alter table doctor_duty_schedule
    owner to dboper;

