package mrs.application.repository;

import mrs.MrsDBTest;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRoomId;
import mrs.domain.model.reservation.datetime.ReservedDate;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@MrsDBTest
@DisplayName("予約可能会議室レポジトリ")
public class ReservableRoomRepositoryTest {
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private ReservableRoomRepository reservableRoomRepository;

    @Test
    public void 会議室一覧を取得する() {
        LocalDate date = LocalDate.now();
        java.util.stream.IntStream.rangeClosed(1, 2)
                .mapToObj(i -> new MeetingRoom(new RoomId(i), new RoomName("会議室")))
                .forEach(j -> meetingRoomRepository.save(j));
        java.util.stream.IntStream.rangeClosed(1, 2)
                .mapToObj(i -> new ReservableRoomId(new RoomId(i), new ReservedDate(date)))
                .map(ReservableRoom::new).forEach(k -> reservableRoomRepository.save(k));

        List<ReservableRoom> rooms = reservableRoomRepository.findByReservableRoomId_ReservedDateOrderByReservableRoomId_RoomIdAsc(date);

        assertNotNull(rooms);
        assertEquals(2, rooms.size());
        assertEquals(java.util.Optional.ofNullable(rooms.get(1).meetingRoom().roomName().value()), Optional.of("会議室"));
    }
}
