package mrs.infrastructure.datasource.reservation;

import mrs.MrsDBTest;
import mrs.domain.model.facility.room.MeetingRoom;
import mrs.domain.model.facility.room.RoomId;
import mrs.domain.model.facility.room.RoomName;
import mrs.domain.model.reservation.Reservation;
import mrs.domain.model.reservation.ReservationId;
import mrs.domain.model.reservation.datetime.ReservedDate;
import mrs.domain.model.reservation.datetime.ReservedDateTime;
import mrs.domain.model.reservation.datetime.ReservedTime;
import mrs.domain.model.reservation.reservable.room.ReservableRoom;
import mrs.domain.model.reservation.reservable.room.ReservableRoomId;
import mrs.domain.model.user.*;
import mrs.infrastructure.datasource.room.MeetingRoomMapperExt;
import mrs.infrastructure.datasource.user.UsrMapperExt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@MrsDBTest
@DisplayName("予約マッパー")
public class ReservationMapperExtTest {
    @Autowired
    MeetingRoomMapperExt meetingRoomMapper;
    @Autowired
    ReservableRoomMapperExt reservableRoomMapper;
    @Autowired
    ReservationMapperExt reservationMapper;
    @Autowired
    UsrMapperExt usrMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void 予約が登録できる() {
        ReservationId id = new ReservationId(1);
        ReservedDate date = new ReservedDate(LocalDate.now());
        ReservedTime time = new ReservedTime(LocalTime.of(10, 0), LocalTime.of(11, 0));
        MeetingRoom meetingRoom = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(meetingRoom);
        ReservableRoom room = new ReservableRoom(new ReservableRoomId(new RoomId(1), date), meetingRoom);
        reservableRoomMapper.insert(room);
        User user = new User(new UserId("Test"), new Password("Password"), new Name("山田", "太郎"), RoleName.USER);
        usrMapper.insert(user);
        ReservedDateTime dateTime = new ReservedDateTime(date, time);
        Reservation reservation = new Reservation(id, dateTime, room, user);
        reservationMapper.insert(reservation);

        Reservation result = reservationMapper.findById(1);

        assertEquals(1, result.reservationId().value());
    }

    @Test
    void 予約を更新できる() {
        ReservationId id = new ReservationId(2);
        ReservedDate date = new ReservedDate(LocalDate.now().plusDays(1));
        ReservedTime time = new ReservedTime(LocalTime.of(10, 0), LocalTime.of(11, 0));
        MeetingRoom meetingRoom = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(meetingRoom);
        ReservableRoom room = new ReservableRoom(new ReservableRoomId(new RoomId(1), date), meetingRoom);
        reservableRoomMapper.insert(room);
        User user = new User(new UserId("Test"), new Password("Password"), new Name("山田", "太郎"), RoleName.USER);
        usrMapper.insert(user);
        ReservedDateTime dateTime = new ReservedDateTime(date, time);
        Reservation reservation = new Reservation(id, dateTime, room, user);
        reservationMapper.insert(reservation);

        Reservation update = reservationMapper.findById(2);
        Reservation updateReservation = new Reservation(new ReservationId(update.reservationId().value()), new ReservedDateTime(date, new ReservedTime(LocalTime.of(11, 0), LocalTime.of(12, 0))), room, user);
        reservationMapper.updateByPrimaryKey(updateReservation);
        Reservation result = reservationMapper.findById(2);

        assertEquals("11:00", result.startTime().toString());
    }

    @Test
    void 予約を削除できる() {
        ReservationId id = new ReservationId(3);
        ReservedDate date = new ReservedDate(LocalDate.now().plusDays(2));
        ReservedTime time = new ReservedTime(LocalTime.of(10, 0), LocalTime.of(11, 0));
        MeetingRoom meetingRoom = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(meetingRoom);
        ReservableRoom room = new ReservableRoom(new ReservableRoomId(new RoomId(1), date), meetingRoom);
        reservableRoomMapper.insert(room);
        User user = new User(new UserId("Test"), new Password("Password"), new Name("山田", "太郎"), RoleName.USER);
        usrMapper.insert(user);
        ReservedDateTime dateTime = new ReservedDateTime(date, time);
        Reservation reservation = new Reservation(id, dateTime, room, user);
        reservationMapper.insert(reservation);
        Reservation delete = reservationMapper.findById(3);

        reservationMapper.deleteByPrimaryKey(delete.reservationId().value());
        Reservation result = reservationMapper.findById(3);

        assertNull(result);
    }

    @Test
    void 開始時間順に予約可能会議室集合を取得できる() {
        ReservationId id = new ReservationId(4);
        ReservedDate date = new ReservedDate(LocalDate.now().plusDays(3));
        ReservedTime time = new ReservedTime(LocalTime.of(10, 0), LocalTime.of(11, 0));
        MeetingRoom meetingRoom = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(meetingRoom);
        ReservableRoomId reservableRoomId = new ReservableRoomId(new RoomId(1), date);
        ReservableRoom room = new ReservableRoom(reservableRoomId, meetingRoom);
        reservableRoomMapper.insert(room);
        User user = new User(new UserId("Test"), new Password("Password"), new Name("山田", "太郎"), RoleName.USER);
        usrMapper.insert(user);
        ReservedDateTime dateTime = new ReservedDateTime(date, time);
        Reservation reservation = new Reservation(id, dateTime, room, user);
        reservationMapper.insert(reservation);

        List<Reservation> result = reservationMapper.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);

        assertNotNull(result);
    }
}
