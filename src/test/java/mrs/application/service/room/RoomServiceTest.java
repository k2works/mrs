package mrs.application.service.room;

import mrs.MrsApplication;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.room.MeetingRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = MrsApplication.class)
@DisplayName("会議室サービス")
public class RoomServiceTest {
    @Nested
    @DisplayName("会議室一覧")
    class ListRooms {
        @Autowired
        RoomService roomService;

        @Test
        @Sql("/schema.sql")
        @Sql("/data.sql")
        @Transactional
        public void 会議室一覧を取得する() {
            MeetingRoom rooms = roomService.findMeetingRoom(1);

            assertEquals(1, rooms.roomId().value());
        }

        @Test
        @Sql("/schema.sql")
        @Sql("/data.sql")
        public void 予約可能会議室一覧を取得する() {
            LocalDate date = LocalDate.now();
            List<ReservableRoom> rooms = roomService.findReservableRooms(date);

            assertEquals(2, rooms.size());
        }
    }
}

