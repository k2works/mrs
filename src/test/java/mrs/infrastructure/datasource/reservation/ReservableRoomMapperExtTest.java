package mrs.infrastructure.datasource.reservation;

import mrs.domain.model.ReservableRoom;
import mrs.domain.model.reservation.ReservableRoomId;
import mrs.domain.model.reservation.ReservedDate;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import mrs.infrastructure.datasource.room.MeetingRoomMapperExt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class ReservableRoomMapperExtTest {
    @Autowired
    MeetingRoomMapperExt meetingRoomMapper;

    @Autowired
    ReservableRoomMapperExt reservableRoomMapper;

    @BeforeEach
    void clean() {
        reservableRoomMapper.deleteByPrimaryKey(LocalDate.now().plusDays(1), 1);
        reservableRoomMapper.deleteByPrimaryKey(LocalDate.now(), 1);
        meetingRoomMapper.deleteByPrimaryKey(1);
    }

    @Test
    void 予約可能会議室を登録できる() {
        MeetingRoom record = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(record);
        ReservableRoom room = new ReservableRoom();
        room.setRoomId(1);
        room.setReservedDate(LocalDate.now());
        reservableRoomMapper.insert(room);

        ReservableRoomId id = new ReservableRoomId(new RoomId(1), new ReservedDate(LocalDate.now()));
        ReservableRoom result = reservableRoomMapper.selectByPrimaryKey(id);
        assertNotNull(result);
    }

    @Test
    void 予約可能会議室を削除できる() {
        MeetingRoom record = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(record);
        ReservableRoom room = new ReservableRoom();
        room.setRoomId(1);
        room.setReservedDate(LocalDate.now());
        reservableRoomMapper.insert(room);

        ReservableRoomId id = new ReservableRoomId(new RoomId(1), new ReservedDate(LocalDate.now()));
        reservableRoomMapper.deleteByPrimaryKey(id.reservedDate(),id.roomId().intValue());
        ReservableRoom result = reservableRoomMapper.selectByPrimaryKey(id);

        assertNull(result);
    }

    @Test
    void 予約可能会議室一覧を取得できる() {
        MeetingRoom record = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(record);
        ReservableRoom room = new ReservableRoom();
        room.setRoomId(1);
        room.setReservedDate(LocalDate.now());
        reservableRoomMapper.insert(room);
        room = new ReservableRoom();
        room.setRoomId(1);
        room.setReservedDate(LocalDate.now().plusDays(1));
        reservableRoomMapper.insert(room);

        List<ReservableRoom> result = reservableRoomMapper.selectAll();
        assertEquals(2, result.size());
    }

}
