package mrs.presentation.reservation;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import mrs.application.service.reservation.AlreadyReservedException;
import mrs.application.service.reservation.UnavailableReservationException;
import mrs.application.service.user.ReservationUserDetails;
import mrs.domain.model.reservation.ReservableRoomId;
import mrs.domain.model.reservation.ReservationId;
import mrs.domain.model.reservation.Reservations;
import mrs.domain.model.reservation.ReservedDate;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 会議室予約画面
 */
@Controller
@RequestMapping("reservations/{date}/{roomId}")
public class ReservationsController {
    private final ReservationCoordinator reservationCoordinator;

    public ReservationsController(ReservationCoordinator reservationCoordinator) {
        this.reservationCoordinator = reservationCoordinator;
    }

    @ModelAttribute
    ReservationForm setUpForm() {
        ReservationForm form = new ReservationForm();
        // デフォルト値
        form.setStartTime(LocalTime.of(9, 0));
        form.setEndTime(LocalTime.of(10, 0));
        return form;
    }

    @GetMapping
    String reserveForm(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, @PathVariable("roomId") Integer roomId, Model model) {
        MeetingRoom room = reservationCoordinator.searchMeetingRoom(new RoomId(roomId));
        ReservableRoomId reservableRoomId = new ReservableRoomId(new RoomId(roomId), new ReservedDate(date));
        Reservations reservations = reservationCoordinator.searchReservations(reservableRoomId);

        List<LocalTime> timeList =
                Stream.iterate(LocalTime.of(0, 0), t -> t.plusMinutes(30))
                        .limit(24 * 2)
                        .collect(Collectors.toList());

        model.addAttribute("room", room);
        model.addAttribute("reservations", reservations.value());
        model.addAttribute("timeList", timeList);
        // model.addAttribute("user", dummyUser());
        return "reservation/reserveForm";
    }

    /**
     * 予約
     */
    @PostMapping
    String reserve(@Validated ReservationForm form, BindingResult bindingResult,
                   @AuthenticationPrincipal ReservationUserDetails userDetails,
                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
                   @PathVariable("roomId") Integer roomId, Model model) {
        if (bindingResult.hasErrors()) {
            return reserveForm(date, roomId, model);
        }

        try {
            reservationCoordinator.reserveMeetingRoom(form.getStartTime(), form.getEndTime(), userDetails, date, roomId);
        } catch (UnavailableReservationException | AlreadyReservedException e) {
            model.addAttribute("error", e.getMessage());
            return reserveForm(date, roomId, model);
        }
        return "redirect:/reservations/{date}/{roomId}";
    }

    /**
     * 取消
     */
    @PostMapping(params = "cancel")
    String cancel(@AuthenticationPrincipal ReservationUserDetails userDetails,
                  @RequestParam("reservationId") Integer reservationId,
                  @PathVariable("roomId") Integer roomId,
                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
                  Model model) {
        try {
            reservationCoordinator.cancelReservedMeetingRoom(new ReservationId(reservationId));
        } catch (AccessDeniedException e) {
            model.addAttribute("error", e.getMessage());
            return reserveForm(date, roomId, model);
        }
        return "redirect:/reservations/{date}/{roomId}";
    }
}
