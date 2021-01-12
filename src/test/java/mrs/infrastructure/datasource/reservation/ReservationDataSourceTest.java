package mrs.infrastructure.datasource.reservation;

import mrs.domain.model.reservation.*;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationDataSourceTest {
    @Autowired
    ReservationDataSource reservationDataSource;

    @Autowired
    ReservationMapperExt reservationMapper;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void clean() {
        reservationMapper.deleteByPrimaryKey(1);
    }

    @Test
    void 開始時間順に予約可能会議室集合を取得できる() {
        ReservationId id = new ReservationId(1);
        ReservedDate date = new ReservedDate(LocalDate.now());
        ReservedTime time = new ReservedTime(LocalTime.of(10, 0), LocalTime.of(11, 0));
        ReservableRoom room = new ReservableRoom(new ReservableRoomId(new RoomId(1), date));
        User user = new User(new UserId("Test"), new Password("Password"), new Name("山田", "太郎"), RoleName.USER);
        Reservation reservation = new Reservation(id, date, time, room, user);
        reservationMapper.insert(reservation);

        ReservableRoomId reservableRoomId = new ReservableRoomId();
        List<Reservation> result = reservationDataSource.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);

        assertNotNull(result);
    }
}
