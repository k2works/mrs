package mrs.application.service.room;

import mrs.MrsApplication;
import mrs.domain.model.reservation.ReservableRooms;
import mrs.domain.model.reservation.ReservedDate;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = MrsApplication.class)
@DisplayName("会議室サービス")
public class RoomServiceTest {
    @Nested
    @DisplayName("会議室を探す")
    class SearchMeetingRoom {
        @Autowired
        RoomService roomService;

        @Test
        @Sql("/schema.sql")
        @Sql("/data.sql")
        @Transactional
        public void 会議室が登録されていれば取得できる() {
            RoomId id = new RoomId(1);
            MeetingRoom room = roomService.findMeetingRoom(id);

            assertEquals(id, room.roomId());
        }
    }

    @Nested
    @DisplayName("予約可能会議室集合を探す")
    class SearchReservableRooms {
        @Autowired
        RoomService roomService;

        @Test
        @Sql("/schema.sql")
        @Sql("/data.sql")
        public void 予約可能会議室が存在すれば予約可能会議室集合を取得できる() {
            ReservedDate date = new ReservedDate(LocalDate.now());
            ReservableRooms rooms = roomService.findReservableRooms(date);

            assertEquals(2, rooms.value().size());
        }
    }
}

