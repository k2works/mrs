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
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer roomId;
    private String userId;
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
        this.startTime = reservedTime.start;
        this.endTime = reservedTime.end;
        this.roomId = room.reservableRoomId().roomId().value();
        this.userId = user.userId().value();
        this.reservableRoom = room;
        this.user = user;
    }

    public Reservation(ReservedDate reservedDate, ReservedTime reservedTime, ReservableRoom room, User user) {
        this.reservedDate = reservedDate;
        this.reservedTime = reservedTime;
        this.startTime = reservedTime.start;
        this.endTime = reservedTime.end;
        this.roomId = room.reservableRoomId().roomId().value();
        this.userId = user.userId().value();
        this.reservableRoom = room;
        this.user = user;
    }

    public boolean overlap(Reservation target) {
        if (!Objects.equals(reservableRoomId, target.reservableRoom.reservableRoomId())) {
            return false;
        }
        if (startTime.equals(target.startTime) && endTime.equals(target.endTime)) {
            return true;
        }
        return target.endTime.isAfter(startTime) && endTime.isAfter(target.startTime);
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
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
