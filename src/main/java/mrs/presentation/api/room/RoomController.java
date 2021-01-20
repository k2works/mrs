package mrs.presentation.api.room;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import mrs.domain.model.reservation.ReservableRooms;
import mrs.domain.model.reservation.ReservedDate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * API 会議室一覧
 */
@RestController("会議室一覧API")
@RequestMapping("/api/rooms")
public class RoomController {
    ReservationCoordinator reservationCoordinator;

    public RoomController(ReservationCoordinator reservationCoordinator) {
        this.reservationCoordinator = reservationCoordinator;
    }

    @GetMapping
    ReservableRooms listRooms() {
        return reservationCoordinator.searchReservableRooms(new ReservedDate(LocalDate.now()));
    }

    @GetMapping("{date}")
    ReservableRooms listRooms(@PathVariable String date) {
        ReservedDate reservedDate = new ReservedDate(LocalDate.parse(date));
        return reservationCoordinator.searchReservableRooms(reservedDate);
    }
}
