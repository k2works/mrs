DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS reservable_room CASCADE;
DROP TABLE IF EXISTS meeting_room CASCADE;
DROP TABLE IF EXISTS usr CASCADE;

CREATE TABLE meeting_room
(
    room_id   INT          NOT NULL AUTO_INCREMENT comment '会議室ID',
    room_name VARCHAR(255) NOT NULL comment '会議室名',
    PRIMARY KEY (room_id)
) comment='会議室';
CREATE TABLE reservable_room
(
    reserved_date DATE NOT NULL comment '予約日',
    room_id       INT  NOT NULL comment '会議室ID',
    PRIMARY KEY (reserved_date, room_id),
    FOREIGN KEY fk_meeting_room (room_id) REFERENCES meeting_room (room_id)
) comment='予約可能会議室';
CREATE TABLE usr
(
    user_id    VARCHAR(255) NOT NULL comment 'ユーザーID',
    first_name VARCHAR(255) NOT NULL comment '姓',
    last_name  VARCHAR(255) NOT NULL comment '名',
    password   VARCHAR(255) NOT NULL comment 'パスワード',
    role_name  VARCHAR(255) NOT NULL comment '役割',
    PRIMARY KEY (user_id)
) comment='ユーザー';
CREATE TABLE reservation
(
    reservation_id INT          NOT NULL AUTO_INCREMENT comment '予約ID',
    end_time       TIME         NOT NULL comment '終了時間',
    start_time     TIME         NOT NULL comment '開始時間',
    reserved_date  DATE         NOT NULL comment '予約日',
    room_id        INT          NOT NULL comment '会議室ID',
    user_id        VARCHAR(255) NOT NULL comment 'ユーザーID',
    PRIMARY KEY (reservation_id),
    FOREIGN KEY kf_reservable_room (reserved_date, room_id) REFERENCES reservable_room (reserved_date, room_id),
    FOREIGN KEY fk_usr (user_id) REFERENCES usr (user_id)
) comment='予約';

