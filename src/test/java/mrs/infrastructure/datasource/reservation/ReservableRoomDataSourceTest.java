package mrs.infrastructure.datasource.reservation;

import mrs.application.repository.MeetingRoomRepository;
import mrs.application.repository.ReservableRoomRepository;
import mrs.application.repository.ReservationRepository;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRoomId;
import mrs.domain.model.reservation.Reservation;
import mrs.domain.model.reservation.ReservedDate;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ReservableRoomDataSourceTest {
    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    @Autowired
    ReservableRoomRepository reservableRoomRepository;

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationMapperExt reservationMapper;

    @Autowired
    ReservableRoomDataSource reservableRoomDataSource;

    @BeforeEach
    void clean() {
        MeetingRoom room = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomRepository.delete(room);
        ReservableRoomId id = new ReservableRoomId(new RoomId(1), new ReservedDate(LocalDate.now()));
        reservableRoomRepository.delete(id);
        // TODO レポジトリ実装
        reservationMapper.deleteByPrimaryKey(1);
    }

    @Test
    void 予約可能会議室を取得する() {
        MeetingRoom room = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomRepository.save(room);
        ReservableRoomId id = new ReservableRoomId(new RoomId(1), new ReservedDate(LocalDate.now()));
        ReservableRoom reservableRoom = new ReservableRoom(id);
        reservableRoomRepository.save(reservableRoom);

        ReservableRoom result = reservableRoomDataSource.findOneForUpdateByReservableRoomId(id);

        assertNotNull(result);
        assertEquals(id, result.reservableRoomId());
        assertEquals(room, result.meetingRoom());
    }
}
