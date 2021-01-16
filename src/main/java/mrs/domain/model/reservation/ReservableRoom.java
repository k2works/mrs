package mrs.domain.model.reservation;

import mrs.domain.model.room.MeetingRoom;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 予約可能会議室
 */
public class ReservableRoom implements Serializable {
    private ReservableRoomId reservableRoomId;

    private MeetingRoom meetingRoom;

    private LocalDate reservedDate;

    private Integer roomId;

    public ReservableRoom() {
    }

    public ReservableRoom(ReservableRoomId reservableRoomId) {
        this.reservableRoomId = reservableRoomId;
        this.reservedDate = reservableRoomId.reservedDate();
        this.roomId = reservableRoomId.roomId();
    }

    public ReservableRoom(ReservableRoomId reservableRoomId, MeetingRoom meetingRoom) {
        this.reservableRoomId = reservableRoomId;
        this.meetingRoom = meetingRoom;
        this.reservedDate = reservableRoomId.reservedDate();
        this.roomId = reservableRoomId.roomId();
    }

    public MeetingRoom meetingRoom() {
        return meetingRoom;
    }

    public ReservableRoomId reservableRoomId() {
        return reservableRoomId;
    }

}
