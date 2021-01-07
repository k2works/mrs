package mrs.domain.model.reservation;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 予約可能会議室ID
 */
@Embeddable
public class ReservableRoomId implements Serializable {

    private Integer roomId;

    private LocalDate reservedDate;

    public ReservableRoomId() {
    }

    public ReservableRoomId(Integer roomId, LocalDate reservedDate) {
        //TODO 値オブジェクトに置き換える
        this.roomId = roomId;
        this.reservedDate = reservedDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((reservedDate == null) ? 0 : reservedDate.hashCode());
        result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        ReservableRoomId other = (ReservableRoomId) obj;
        if (reservedDate == null) {
            if (other.reservedDate != null) return false;
        } else if (!reservedDate.equals(other.reservedDate)) {
            return false;
        }
        if (roomId == null) {
            if (other.roomId != null) return false;
        } else if (!roomId.equals(other.roomId))
            return false;
        return true;
    }

    public Integer roomId() {
        return roomId;
    }

    public LocalDate reservedDate() {
        return reservedDate;
    }
}
