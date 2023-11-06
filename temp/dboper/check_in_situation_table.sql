create table check_in_situation_table
(
    check_in_number varchar(10) not null
        primary key,
    patient_id      varchar(10)
        references patient_info_table,
    bunk_number     varchar(4)
        references bunk_info_table
)
    with (orientation = row, compression = no);

alter table check_in_situation_table
    owner to dboper;

