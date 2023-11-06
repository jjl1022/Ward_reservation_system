create table ward_booking_table
(
    booking_number varchar(10) not null
        primary key,
    patient_id     varchar(10)
        references patient_info_table,
    bunk_number    varchar(10)
        references bunk_info_table,
    is_check_in    varchar(2)
)
    with (orientation = row, compression = no);

alter table ward_booking_table
    owner to dboper;

