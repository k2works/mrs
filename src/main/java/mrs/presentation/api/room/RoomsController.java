package mrs.presentation.api.room;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import mrs.domain.model.reservation.datetime.ReservedDate;
import mrs.domain.model.reservation.reservable.room.ReservableRooms;
import org.springframework.format.annotation.DateTimeFormat;
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
public class RoomsController {
    ReservationCoordinator reservationCoordinator;

    public RoomsController(ReservationCoordinator reservationCoordinator) {
        this.reservationCoordinator = reservationCoordinator;
    }

    @GetMapping
    ReservableRooms listRooms() {
        return reservationCoordinator.searchReservableRooms(new ReservedDate(LocalDate.now()));
    }

    @GetMapping("{date}")
    ReservableRooms listRooms(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date
    ) {
        ReservedDate reservedDate = new ReservedDate(date);
        return reservationCoordinator.searchReservableRooms(reservedDate);
    }
}
