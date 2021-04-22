package mrs.presentation.api.reservation;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import mrs.application.service.user.ReservationUserDetails;
import mrs.application.service.user.ReservationUserDetailsService;
import mrs.domain.model.facility.room.RoomId;
import mrs.domain.model.reservation.ReservationId;
import mrs.domain.model.reservation.Reservations;
import mrs.domain.model.reservation.datetime.ReservedDate;
import mrs.domain.model.reservation.datetime.ReservedTime;
import mrs.domain.model.reservation.reservable.room.ReservableRoom;
import mrs.domain.model.reservation.reservable.room.ReservableRoomId;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * API 会議室予約
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController("会議室予約API")
@RequestMapping(value = {"api/reservations/{date}/{roomId}"})
public class ReservationsController {
    ReservationUserDetailsService reservationUserDetailsService;
    ReservationCoordinator reservationCoordinator;

    public ReservationsController(ReservationCoordinator reservationCoordinator, ReservationUserDetailsService reservationUserDetailsService) {
        this.reservationCoordinator = reservationCoordinator;
        this.reservationUserDetailsService = reservationUserDetailsService;
    }

    /**
     * 予約一覧
     */
    @GetMapping
    Reservations listReservations(
            @AuthenticationPrincipal ReservationUserDetails userDetails,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
            @PathVariable("roomId") Integer roomId
    ) {
        ReservableRoomId reservableRoomId = new ReservableRoomId(new RoomId(roomId), new ReservedDate(date));
        return reservationCoordinator.searchReservations(reservableRoomId);
    }

    /**
     * 予約
     */
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

    /**
     * 取消
     */
    @DeleteMapping
    void cancel(
            @AuthenticationPrincipal ReservationUserDetails userDetails,
            @RequestParam("reservationId") Integer reservationId,
            @PathVariable("roomId") Integer roomId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date
    ) {
        reservationCoordinator.cancelReservedMeetingRoom(new ReservationId(reservationId));
    }
}
