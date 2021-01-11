\connect appdb
DROP TABLE IF EXISTS meeting_room CASCADE;
DROP TABLE IF EXISTS reservable_room CASCADE;
DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS usr CASCADE;
DROP TABLE IF EXISTS todo CASCADE;

CREATE TABLE meeting_room
(
    room_id   SERIAL       NOT NULL,
    room_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (room_id)
);
CREATE TABLE reservable_room
(
    reserved_date DATE NOT NULL,
    room_id       INT4 NOT NULL,
    PRIMARY KEY (reserved_date, room_id),
    FOREIGN KEY (room_id) REFERENCES meeting_room
);
CREATE TABLE usr
(
    user_id    VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role_name  VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id)
);
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

CREATE TABLE todo
(
    id       SERIAL  NOT NULL,
    title    TEXT    NOT NULL,
    details  TEXT,
    finished BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);
