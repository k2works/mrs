package mrs.domain.model.reservation;

import mrs.domain.model.room.RoomId;
import mrs.domain.model.user.User;
import mrs.domain.model.user.UserId;

import java.time.LocalTime;
import java.util.Objects;

/**
 * 予約
 */
public class Reservation {
    private ReservationId reservationId;
    private ReservedDate reservedDate;
    private ReservedTime reservedTime;
    private RoomId roomId;
    private UserId userId;
    private ReservableRoomId reservableRoomId;
    private ReservableRoom reservableRoom;
    private User user;

    @Deprecated
    public Reservation() {
    }

    public Reservation(ReservationId reservationId, ReservedDate reservedDate, ReservedTime reservedTime, ReservableRoom room, User user) {
        this.reservationId = reservationId;
        this.reservedDate = reservedDate;
        this.reservedTime = reservedTime;
        this.roomId = room.reservableRoomId().roomId();
        this.userId = user.userId();
        this.reservableRoom = room;
        this.user = user;
    }

    public Reservation(ReservedDate reservedDate, ReservedTime reservedTime, ReservableRoom room, User user) {
        this.reservedDate = reservedDate;
        this.reservedTime = reservedTime;
        this.roomId = room.reservableRoomId().roomId();
        this.userId = user.userId();
        this.reservableRoom = room;
        this.user = user;
    }

    public boolean overlap(Reservation target) {
        if (!Objects.equals(reservableRoomId, target.reservableRoom.reservableRoomId())) {
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

    public ReservableRoomId reservableRoomId() {
        return reservableRoomId;
    }
}
