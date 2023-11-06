create table admin_info_table
(
    username varchar(20) not null
        primary key,
    password varchar(20)
)
    with (orientation = row, compression = no);

alter table admin_info_table
    owner to dboper;

