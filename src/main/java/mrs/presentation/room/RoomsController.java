package mrs.presentation.room;

import mrs.application.service.room.RoomService;
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
    private final RoomService roomService;

    public RoomsController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    String listRooms(Model model) {
        ReservedDate date = new ReservedDate(LocalDate.now());
        ReservableRooms rooms = roomService.findReservableRooms(date);
        model.addAttribute("date", date.value());
        model.addAttribute("rooms", rooms.value());
        return "room/listRooms";
    }

    @GetMapping("{date}")
    String listRooms(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, Model model
    ) {
        ReservableRooms rooms = roomService.findReservableRooms(new ReservedDate(date));
        model.addAttribute("rooms", rooms.value());
        return "room/listRooms";
    }
}
