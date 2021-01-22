package mrs.infrastructure.datasource.reservation;

import mrs.MrsDBTest;
import mrs.application.repository.MeetingRoomRepository;
import mrs.application.repository.ReservableRoomRepository;
import mrs.application.repository.ReservationRepository;
import mrs.domain.model.facility.room.MeetingRoom;
import mrs.domain.model.facility.room.RoomId;
import mrs.domain.model.facility.room.RoomName;
import mrs.domain.model.reservation.datetime.ReservedDate;
import mrs.domain.model.reservation.reservable.room.ReservableRoom;
import mrs.domain.model.reservation.reservable.room.ReservableRoomId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MrsDBTest
@DisplayName("予約可能会議室データソース")
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
