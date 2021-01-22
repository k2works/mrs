\connect appdb
CREATE TABLE meeting_room
(
    room_id   SERIAL       NOT NULL,
    room_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (room_id)
);
comment
on table public.meeting_room is '会議室';
comment
on column public.meeting_room.room_id is '会議室ID';
comment
on column public.meeting_room.room_name is '会議室名';

CREATE TABLE reservable_room
(
    reserved_date DATE NOT NULL,
    room_id       INT4 NOT NULL,
    PRIMARY KEY (reserved_date, room_id),
    FOREIGN KEY (room_id) REFERENCES meeting_room
);
comment
on table public.reservable_room is '予約可能会議室';
comment
on column public.reservable_room.reserved_date is '予約日';
comment
on column public.reservable_room.room_id is '会議室ID';

CREATE TABLE usr
(
    user_id    VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role_name  VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id)
);
comment
on table public.usr is 'ユーザー';
comment
on column public.usr.user_id is 'ユーザーID';
comment
on column public.usr.first_name is '姓';
comment
on column public.usr.last_name is '名';
comment
on column public.usr.password is 'パスワード';
comment
on column public.usr.role_name is '役割';

CREATE TABLE reservation
(
    reservation_id SERIAL       NOT NULL,
    end_time       TIME         NOT NULL,
    start_time     TIME         NOT NULL,
    reserved_date  DATE         NOT NULL,
    room_id        INT4         NOT NULL,
    user_id        VARCHAR(255) NOT NULL,
    PRIMARY KEY (reservation_id),
    FOREIGN KEY (reserved_date, room_id) REFERENCES reservable_room,
    FOREIGN KEY (user_id) REFERENCES usr
);
comment
on table public.reservation is '予約';
comment
on column public.reservation.reservation_id is '予約ID';
comment
on column public.reservation.end_time is '予約終了時間';
comment
on column public.reservation.start_time is '予約開始時間';
comment
on column public.reservation.reserved_date is '予約日';
comment
on column public.reservation.room_id is '会議室ID';
comment
on column public.reservation.user_id is 'ユーザーID';

