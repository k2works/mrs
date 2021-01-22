package mrs.application.repository;

import mrs.MrsDBTest;
import mrs.domain.model.reservation.*;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import mrs.domain.model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MrsDBTest
@DisplayName("予約レポジトリ")
public class ReservationRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private ReservableRoomRepository reservableRoomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        User user = userRepository.findById("test");
        LocalDate date = LocalDate.now();
        java.util.stream.IntStream.rangeClosed(1, 2)
                .mapToObj(i -> new MeetingRoom(new RoomId(i), new RoomName("会議室")))
                .forEach(j -> meetingRoomRepository.save(j));
        java.util.stream.IntStream.rangeClosed(1, 2)
                .mapToObj(i -> new ReservableRoomId(new RoomId(i), new ReservedDate(date)))
                .map(ReservableRoom::new).forEach(k -> reservableRoomRepository.save(k));
        reservableRoomRepository.findAll().stream().map(i ->
                new Reservation(
                        new ReservationId(i.reservableRoomId().roomId().value()),
                        new ReservedDateTime(
                                new ReservedDate(date),
                                new ReservedTime(LocalTime.of(10, 0), LocalTime.of(10, 30))
                        ),
                        new ReservableRoom(i.reservableRoomId(), i.meetingRoom()),
                        user
                )
        ).forEach(k -> reservationRepository.save(k));
    }

    @Test
    public void 予約一覧を取得する() {
        List<Reservation> reservations = reservationRepository.findAll();

        assertNotNull(reservations);
        assertEquals(2, reservations.size());
    }

    @Test
    public void 予約したユーザーを保持している() {
        Optional<Reservation> reservation = reservationRepository.findById(1);
        Reservation value = reservation.get();

        Assertions.assertNotNull(value);
        Assertions.assertEquals("山田 太郎", value.user().name().toString());
    }

    @Test
    public void 予約した部屋を保持している() {
        Optional<Reservation> reservation = reservationRepository.findById(1);
        Reservation value = reservation.get();

        Assertions.assertNotNull(value);
        Assertions.assertEquals(1, value.reservableRoom().reservableRoomId().roomId().value());
        Assertions.assertEquals(LocalDate.now(), value.reservableRoom().reservableRoomId().reservedDate().value());
    }
}
