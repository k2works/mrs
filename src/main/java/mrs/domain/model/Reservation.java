package mrs.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "reserved_date"), @JoinColumn(name = "room_id")})
    private ReservableRoom reservableRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ReservableRoom reservableRoom() {
        return reservableRoom;
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

    public User user() {
        return user;
    }
}
