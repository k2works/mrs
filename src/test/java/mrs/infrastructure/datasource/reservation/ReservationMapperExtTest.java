package mrs.infrastructure.datasource.reservation;

import mrs.domain.model.reservation.*;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import mrs.domain.model.user.*;
import mrs.infrastructure.datasource.room.MeetingRoomMapperExt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ReservationMapperExtTest {
    @Autowired
    MeetingRoomMapperExt meetingRoomMapper;
    @Autowired
    ReservableRoomMapperExt reservableRoomMapper;
    @Autowired
    ReservationMapperExt reservationMapper;

    @BeforeEach
    void setUp() {
        // TODO 作った覚えのないデータができている
        reservationMapper.deleteByPrimaryKey(1);
    }

    @Test
    void 予約が登録できる() {
        ReservationId id = new ReservationId(1);
        ReservedDate date = new ReservedDate(LocalDate.now());
        ReservedTime time = new ReservedTime(LocalTime.of(10, 0), LocalTime.of(11, 0));
        MeetingRoom meetingRoom = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        ReservableRoom room = new ReservableRoom(new ReservableRoomId(new RoomId(1), date), meetingRoom);
        User user = new User(new UserId("Test"), new Password("Password"), new Name("山田", "太郎"), RoleName.USER);
        Reservation reservation = new Reservation(id, date, time, room, user);
        reservationMapper.insert(reservation);

        Reservation result = reservationMapper.selectByPrimaryKey(1);

        assertEquals(1, result.getReservationId());
    }

    // TODO 参照制約エラーを解決する
    void 予約を更新できる() {
        ReservationId id = new ReservationId(2);
        ReservedDate date = new ReservedDate(LocalDate.now().plusDays(1));
        ReservedTime time = new ReservedTime(LocalTime.of(10, 0), LocalTime.of(11, 0));
        MeetingRoom meetingRoom = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        ReservableRoom room = new ReservableRoom(new ReservableRoomId(new RoomId(1), date), meetingRoom);
        User user = new User(new UserId("Test"), new Password("Password"), new Name("山田", "太郎"), RoleName.USER);
        Reservation reservation = new Reservation(id, date, time, room, user);
        reservationMapper.insert(reservation);

        Reservation update = reservationMapper.selectByPrimaryKey(2);
        Reservation updateReservation = new Reservation(new ReservationId(update.getReservationId()), date, new ReservedTime(LocalTime.of(11, 0), LocalTime.of(12, 0)), room, user);
        reservationMapper.updateByPrimaryKey(updateReservation);
        Reservation result = reservationMapper.selectByPrimaryKey(2);

        assertEquals("11:00", result.getStartTime().toString());
    }

    // TODO 参照制約エラーを解決する
    void 予約を削除できる() {
        ReservationId id = new ReservationId(3);
        ReservedDate date = new ReservedDate(LocalDate.now().plusDays(2));
        ReservedTime time = new ReservedTime(LocalTime.of(10, 0), LocalTime.of(11, 0));
        MeetingRoom meetingRoom = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        ReservableRoom room = new ReservableRoom(new ReservableRoomId(new RoomId(1), date), meetingRoom);
        User user = new User(new UserId("Test"), new Password("Password"), new Name("山田", "太郎"), RoleName.USER);
        Reservation reservation = new Reservation(id, date, time, room, user);
        reservationMapper.insert(reservation);
        Reservation delete = reservationMapper.selectByPrimaryKey(3);

        reservationMapper.deleteByPrimaryKey(delete.getReservationId());
        Reservation result = reservationMapper.selectByPrimaryKey(3);

        assertNull(result);
    }

    // TODO 参照制約エラーを解決する
    void 開始時間順に予約可能会議室集合を取得できる() {
        ReservationId id = new ReservationId(4);
        ReservedDate date = new ReservedDate(LocalDate.now().plusDays(3));
        ReservedTime time = new ReservedTime(LocalTime.of(10, 0), LocalTime.of(11, 0));
        MeetingRoom meetingRoom = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        ReservableRoom room = new ReservableRoom(new ReservableRoomId(new RoomId(1), date), meetingRoom);
        User user = new User(new UserId("Test"), new Password("Password"), new Name("山田", "太郎"), RoleName.USER);
        Reservation reservation = new Reservation(id, date, time, room, user);
        reservationMapper.insert(reservation);

        ReservableRoomId reservableRoomId = new ReservableRoomId();
        List<Reservation> result = reservationMapper.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);

        assertNotNull(result);
    }
}
