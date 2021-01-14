package mrs.domain.model.reservation;

import mrs.domain.model.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 予約
 */
public class Reservation {
    private Integer reservationId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate reservedDate;
    private Integer roomId;
    private String userId;
    private ReservableRoomId reservableRoomId;
    private ReservableRoom reservableRoom;
    private User user;

    @Deprecated
    public Reservation() {
    }

    public Reservation(ReservedTime reservedTime, ReservableRoom room, User user) {
        this.startTime = reservedTime.start;
        this.endTime = reservedTime.end;
        this.reservableRoom = room;
        this.user = user;
    }

    public Reservation(ReservationId reservationId, ReservedTime reservedTime, ReservableRoom room, User user) {
        this.reservationId = reservationId.value;
        this.startTime = reservedTime.start;
        this.endTime = reservedTime.end;
        this.reservableRoom = room;
        this.user = user;
    }

    public Reservation(ReservationId reservationId, ReservedDate reservedDate, ReservedTime reservedTime, ReservableRoom room, User user) {
        this.reservationId = reservationId.value;
        this.reservedDate = reservedDate.value;
        this.startTime = reservedTime.start;
        this.endTime = reservedTime.end;
        this.roomId = room.reservableRoomId().roomId();
        this.userId = user.userId().value();
        this.reservableRoom = room;
        this.user = user;
    }

    public Reservation(ReservedDate reservedDate, ReservedTime reservedTime, ReservableRoom room, User user) {
        this.reservedDate = reservedDate.value;
        this.startTime = reservedTime.start;
        this.endTime = reservedTime.end;
        this.roomId = room.reservableRoomId().roomId();
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

    public Integer getReservationId() {
        return reservationId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public ReservedTime reservedTime() {
        return new ReservedTime(startTime, endTime);
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
