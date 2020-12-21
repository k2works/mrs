package mrs.domain.repository.room;

import mrs.MrsApplication;
import mrs.domain.model.ReservableRoom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MrsApplication.class)
public class ReservableRoomRepositoryTest {

    @Autowired
    private ReservableRoomRepository reservableRoomRepository;

    @Test
    @Sql("/data.sql")
    public void 会議室一覧を取得する() {
        LocalDate date = LocalDate.now();
        List<ReservableRoom> rooms = reservableRoomRepository.findByReservableRoomId_ReservedDateOrderByReservableRoomId_RoomIdAsc(date);
        assertNotNull(rooms);
        assertEquals(2, rooms.size());
        assertEquals(java.util.Optional.ofNullable(rooms.get(1).meetingRoom().roomName()), Optional.of("有楽町"));
    }
}
