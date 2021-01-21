package mrs.presentation.api.reservation;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import mrs.application.service.user.ReservationUserDetails;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRoomId;
import mrs.domain.model.reservation.ReservedDate;
import mrs.domain.model.reservation.ReservedTime;
import mrs.domain.model.room.RoomId;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * API 会議室予約
 */
@RestController("会議室予約API")
@RequestMapping("api/reservations/{date}/{roomId}")
public class ReservationsController {
    ReservationCoordinator reservationCoordinator;

    public ReservationsController(ReservationCoordinator reservationCoordinator) {
        this.reservationCoordinator = reservationCoordinator;
    }

    @PostMapping
    void reserve(
            @AuthenticationPrincipal ReservationUserDetails userDetails,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
            @PathVariable("roomId") Integer roomId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam(value = "start", required = true) LocalTime start,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam(value = "end", required = true) LocalTime end
    ) {
        ReservableRoom reservableRoom = new ReservableRoom(new ReservableRoomId(new RoomId(roomId), new ReservedDate(date)));
        ReservedTime reservedTime = new ReservedTime(start, end);
        reservationCoordinator.reserveMeetingRoom(reservedTime, reservableRoom, userDetails);
    }
}
