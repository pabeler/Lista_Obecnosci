create table grupy
(
    id     int auto_increment
        primary key,
    termin date        null,
    nazwa  varchar(45) null,
    constraint grupy_id_uindex
        unique (id)
);

create table studenci
(
    id       int auto_increment
        primary key,
    imie     varchar(30) null,
    nazwisko varchar(30) null,
    grupa    int         null,
    constraint studenci_id_uindex
        unique (id)
);
