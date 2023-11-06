create table bunk_info_table
(
    bunk_number varchar(10) not null
        primary key,
    ward_number varchar(10)
        references ward_info_table,
    is_check_in varchar(2)
)
    with (orientation = row, compression = no);

alter table bunk_info_table
    owner to dboper;

