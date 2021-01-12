package mrs.application.repository;

import mrs.MrsApplication;
import mrs.domain.model.reservation.ReservableRoomId;
import mrs.domain.model.reservation.Reservation;
import mrs.domain.model.reservation.ReservedDate;
import mrs.domain.model.room.RoomId;
import org.junit.jupiter.api.Assertions;
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

@Sql("/schema.sql")
@Sql("/data.sql")
@SpringBootTest(classes = MrsApplication.class)
@DisplayName("予約レポジトリ")
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void 予約一覧を取得する() {
        LocalDate date = LocalDate.now();
        ReservableRoomId reservableRoomId = new ReservableRoomId(new RoomId(1), new ReservedDate(date));
        List<Reservation> reservations = reservationRepository.findAll();

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
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
        Assertions.assertEquals(1, value.reservableRoomId().roomId());
        Assertions.assertEquals(LocalDate.now(), value.reservableRoomId().reservedDate());
    }
}
