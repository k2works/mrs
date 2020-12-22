package mrs.domain.repository.reservation;

import mrs.MrsApplication;
import mrs.domain.model.ReservableRoomId;
import mrs.domain.model.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = MrsApplication.class)
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @Sql("/schema.sql")
    @Sql("/data.sql")
    public void 予約一覧を取得する() {
        LocalDate date = LocalDate.now();
        ReservableRoomId reservableRoomId = new ReservableRoomId(1, date);
        List<Reservation> reservations = reservationRepository.findAll();

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
    }
}
