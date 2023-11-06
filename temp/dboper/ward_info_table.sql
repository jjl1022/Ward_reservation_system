create table ward_info_table
(
    ward_number   varchar(10) not null
        primary key,
    department_id varchar(10)
        references department_info_table
)
    with (orientation = row, compression = no);

alter table ward_info_table
    owner to dboper;

