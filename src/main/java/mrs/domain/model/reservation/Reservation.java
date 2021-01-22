package mrs.domain.model.reservation;

import mrs.domain.model.user.User;

import java.time.LocalTime;
import java.util.Objects;

/**
 * 予約
 */
public class Reservation {
    private ReservationId reservationId;
    private ReservedDate reservedDate;
    private ReservedTime reservedTime;
    private ReservedDateTime reservedDateTime;
    private ReservableRoom reservableRoom;
    private User user;

    @Deprecated
    public Reservation() {
    }

    public Reservation(ReservationId reservationId, ReservedDateTime reservedDateTime, ReservableRoom room, User user) {
        this.reservationId = reservationId;
        this.reservedDate = reservedDateTime.date;
        this.reservedTime = reservedDateTime.time;
        this.reservableRoom = room;
        this.user = user;
    }

    public Reservation(ReservedDateTime reservedDateTime, ReservableRoom room, User user) {
        this.reservedDate = reservedDateTime.date;
        this.reservedTime = reservedDateTime.time;
        this.reservableRoom = room;
        this.user = user;
    }

    public boolean overlap(Reservation target) {
        if (!Objects.equals(reservableRoom.reservableRoomId(), target.reservableRoom.reservableRoomId())) {
            return false;
        }
        if (reservedTime.start.equals(target.reservedTime.start) && reservedTime.end.equals(target.reservedTime.end)) {
            return true;
        }
        return target.reservedTime.end.isAfter(reservedTime.start) && reservedTime.end.isAfter(target.reservedTime.start);
    }

    public ReservationId reservationId() {
        return reservationId;
    }

    public ReservedDate reservedDate() {
        return reservedDate;
    }

    public ReservedTime reservedTime() {
        return reservedTime;
    }

    public LocalTime startTime() {
        return reservedTime.start;
    }

    public LocalTime endTime() {
        return reservedTime.end;
    }

    public ReservableRoom reservableRoom() {
        return reservableRoom;
    }

    public User user() {
        return user;
    }
}
