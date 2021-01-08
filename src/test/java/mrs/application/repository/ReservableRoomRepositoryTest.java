package mrs.application.repository;

import mrs.MrsApplication;
import mrs.domain.model.reservation.ReservableRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = MrsApplication.class)
@DisplayName("予約可能会議室レポジトリ")
public class ReservableRoomRepositoryTest {

    @Autowired
    private ReservableRoomRepository reservableRoomRepository;

    @Test
    @Sql("/schema.sql")
    @Sql("/data.sql")
    public void 会議室一覧を取得する() {
        LocalDate date = LocalDate.now();
        List<ReservableRoom> rooms = reservableRoomRepository.findByReservableRoomId_ReservedDateOrderByReservableRoomId_RoomIdAsc(date);
        assertNotNull(rooms);
        assertEquals(2, rooms.size());
        assertEquals(java.util.Optional.ofNullable(rooms.get(1).meetingRoom().roomName().value()), Optional.of("有楽町"));
    }
}
