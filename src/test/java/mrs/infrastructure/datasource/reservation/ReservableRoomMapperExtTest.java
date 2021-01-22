package mrs.infrastructure.datasource.reservation;

import mrs.MrsDBTest;
import mrs.domain.model.reservation.datetime.ReservedDate;
import mrs.domain.model.reservation.reservable.room.ReservableRoom;
import mrs.domain.model.reservation.reservable.room.ReservableRoomId;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import mrs.infrastructure.datasource.room.MeetingRoomMapperExt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MrsDBTest
@DisplayName("予約可能会議室マッパー")
public class ReservableRoomMapperExtTest {
    @Autowired
    ReservationMapperExt reservationMapper;

    @Autowired
    MeetingRoomMapperExt meetingRoomMapper;

    @Autowired
    ReservableRoomMapperExt reservableRoomMapper;

    @Test
    void 予約可能会議室を登録できる() {
        MeetingRoom record = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(record);
        ReservableRoomId id = new ReservableRoomId(new RoomId(1), new ReservedDate(LocalDate.now()));
        ReservableRoom room = new ReservableRoom(id);
        reservableRoomMapper.insert(room);

        ReservableRoom result = reservableRoomMapper.selectByPrimaryKey(id);
        assertNotNull(result);
    }

    @Test
    void 予約可能会議室を削除できる() {
        MeetingRoom record = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(record);
        ReservableRoomId id = new ReservableRoomId(new RoomId(1), new ReservedDate(LocalDate.now()));
        ReservableRoom room = new ReservableRoom(id);
        reservableRoomMapper.insert(room);

        reservableRoomMapper.deleteByPrimaryKey(id.reservedDate().value(), id.roomId().value().intValue());
        ReservableRoom result = reservableRoomMapper.selectByPrimaryKey(id);

        assertNull(result);
    }

    @Test
    void 予約可能会議室一覧を取得できる() {
        MeetingRoom record = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(record);
        ReservableRoomId id = new ReservableRoomId(new RoomId(1), new ReservedDate(LocalDate.now()));
        ReservableRoom room = new ReservableRoom(id);
        reservableRoomMapper.insert(room);
        id = new ReservableRoomId(new RoomId(1), new ReservedDate(LocalDate.now().plusDays(1)));
        room = new ReservableRoom(id);
        reservableRoomMapper.insert(room);

        List<ReservableRoom> result = reservableRoomMapper.selectAll();
        assertEquals(2, result.size());
    }

}
