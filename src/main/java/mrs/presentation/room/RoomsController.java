package mrs.presentation.room;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import mrs.domain.model.reservation.ReservableRooms;
import mrs.domain.model.reservation.ReservedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

/**
 * 会議室一覧画面
 */
@Controller
@RequestMapping("rooms")
public class RoomsController {
    private final ReservationCoordinator reservationCoordinator;

    public RoomsController(ReservationCoordinator reservationCoordinator) {
        this.reservationCoordinator = reservationCoordinator;
    }

    @GetMapping
    String listRooms(Model model) {
        ReservedDate date = new ReservedDate(LocalDate.now());
        ReservableRooms rooms = reservationCoordinator.searchReservableRooms(date);
        model.addAttribute("date", date.value());
        model.addAttribute("rooms", rooms.value());
        return "room/listRooms";
    }

    @GetMapping("{date}")
    String listRooms(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, Model model
    ) {
        ReservedDate reservedDate = new ReservedDate(date);
        ReservableRooms rooms = reservationCoordinator.searchReservableRooms(reservedDate);
        model.addAttribute("rooms", rooms.value());
        return "room/listRooms";
    }
}
