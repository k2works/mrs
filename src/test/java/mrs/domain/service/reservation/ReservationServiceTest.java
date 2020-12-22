package mrs.domain.service.reservation;

import mrs.MrsApplication;
import mrs.domain.model.*;
import mrs.domain.repository.reservation.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = MrsApplication.class)
public class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationRepository reservationRepository;

    @Test
    @Sql("/schema.sql")
    @Sql("/data.sql")
    public void 会議室を予約する() {
        Integer roomId = 1;
        LocalDate date = LocalDate.now();
        ReservableRoom reservableRoom = new ReservableRoom(
                new ReservableRoomId(roomId, date));
        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalTime.of(9, 0));
        reservation.setEndTime(LocalTime.of(10, 0));
        reservation.setReservableRoom(reservableRoom);
        reservation.setUser(dummyUser());
        reservationService.reserve(reservation);

        Reservation result = reservationRepository.getOne(1);
        assertNotNull(result);
    }

    private User dummyUser() {
        User user = new User();
        user.setUserId("taro-yamada");
        user.setFirstName("太郎");
        user.setLastName("山田");
        user.setRoleName(RoleName.USER);
        return user;
    }
}
