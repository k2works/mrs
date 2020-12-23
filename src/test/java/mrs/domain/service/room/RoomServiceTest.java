package mrs.domain.service.room;

import mrs.MrsApplication;
import mrs.domain.model.ReservableRoom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = MrsApplication.class)
public class RoomServiceTest {
    @Autowired
    RoomService roomService;

    @Test
    @Sql("/schema.sql")
    @Sql("/data.sql")
    public void 会議室一覧を取得する() {
        LocalDate date = LocalDate.now();
        List<ReservableRoom> rooms = roomService.findReservableRooms(date);

        assertEquals(2, rooms.size());
    }
}