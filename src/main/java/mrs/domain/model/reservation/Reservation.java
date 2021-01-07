package mrs.domain.model.reservation;

import mrs.domain.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 予約
 */
@Entity
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "reserved_date"), @JoinColumn(name = "room_id")})
    private ReservableRoom reservableRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Reservation() {
    }

    public Reservation(ReservedTime reservedTime, ReservableRoom room, User user) {
        this.startTime = reservedTime.start;
        this.endTime = reservedTime.end;
        this.reservableRoom = room;
        this.user = user;
    }

    public boolean overlap(Reservation target) {
        if (!Objects.equals(reservableRoom.reservableRoomId(), target.reservableRoom.reservableRoomId())) {
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

    public ReservableRoom reservableRoom() {
        return reservableRoom;
    }

    public User user() {
        return user;
    }
}
