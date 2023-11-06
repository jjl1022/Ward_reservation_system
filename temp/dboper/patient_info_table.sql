create table patient_info_table
(
    patient_id   varchar(10) not null
        primary key,
    patient_name varchar(50),
    patient_sex  varchar(2),
    patient_age  varchar(3)
)
    with (orientation = row, compression = no);

alter table patient_info_table
    owner to dboper;

