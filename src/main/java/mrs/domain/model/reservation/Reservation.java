package mrs.domain.model.reservation;

import mrs.domain.model.user.User;

import java.time.LocalTime;
import java.util.Objects;

/**
 * 予約
 */
public class Reservation {
    private ReservationId reservationId;
    private ReservedDateTime reservedDateTime;
    private ReservableRoom reservableRoom;
    private User user;

    @Deprecated
    public Reservation() {
    }

    public Reservation(ReservationId reservationId, ReservedDateTime reservedDateTime, ReservableRoom room, User user) {
        this.reservationId = reservationId;
        this.reservedDateTime = reservedDateTime;
        this.reservableRoom = room;
        this.user = user;
    }

    public Reservation(ReservedDateTime reservedDateTime, ReservableRoom room, User user) {
        this.reservedDateTime = reservedDateTime;
        this.reservableRoom = room;
        this.user = user;
    }

    public boolean overlap(Reservation target) {
        if (!Objects.equals(reservableRoom.reservableRoomId(), target.reservableRoom.reservableRoomId())) {
            return false;
        }
        if (reservedDateTime.time.start.equals(target.reservedDateTime.time.start) && reservedDateTime.time.end.equals(target.reservedDateTime.time.end)) {
            return true;
        }
        return target.reservedDateTime.time.end.isAfter(reservedDateTime.time.start) && reservedDateTime.time.end.isAfter(target.reservedDateTime.time.start);
    }

    public ReservationId reservationId() {
        return reservationId;
    }

    public ReservedDate reservedDate() {
        return reservedDateTime.date;
    }

    public ReservedTime reservedTime() {
        return reservedDateTime.time;
    }

    public LocalTime startTime() {
        return reservedDateTime.time.start;
    }

    public LocalTime endTime() {
        return reservedDateTime.time.end;
    }

    public ReservableRoom reservableRoom() {
        return reservableRoom;
    }

    public User user() {
        return user;
    }
}
