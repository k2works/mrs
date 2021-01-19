package mrs.infrastructure.datasource.reservation;

import mrs.MrsDBTest;
import mrs.application.repository.MeetingRoomRepository;
import mrs.application.repository.ReservableRoomRepository;
import mrs.application.repository.ReservationRepository;
import mrs.application.repository.UserRepository;
import mrs.domain.model.reservation.*;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import mrs.domain.model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MrsDBTest
@AutoConfigureMockMvc
@DisplayName("予約データソース")
public class ReservationDataSourceTest {
    @Autowired
    MeetingRoomRepository meetingRoomRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationDataSource reservationDataSource;
    @Autowired
    private ReservableRoomRepository reservableRoomRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void 開始時間順に予約可能会議室集合を取得できる() {
        ReservationId id = new ReservationId(1);
        ReservedDate date = new ReservedDate(LocalDate.now());
        ReservedTime time = new ReservedTime(LocalTime.of(10, 0), LocalTime.of(11, 0));
        MeetingRoom meetingRoom = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomRepository.save(meetingRoom);
        ReservableRoom room = new ReservableRoom(new ReservableRoomId(new RoomId(1), date), meetingRoom);
        reservableRoomRepository.save(room);
        User user = userRepository.findById("test");
        Reservation reservation = new Reservation(id, date, time, room, user);
        reservationRepository.save(reservation);

        ReservableRoomId reservableRoomId = new ReservableRoomId();
        List<Reservation> result = reservationDataSource.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);

        assertNotNull(result);
    }
}
