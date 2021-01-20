package mrs.domain.model.reservation;

import mrs.domain.model.room.MeetingRoom;

import java.time.LocalDate;

/**
 * 予約可能会議室
 */
public class ReservableRoom {
    private ReservableRoomId reservableRoomId;

    private MeetingRoom meetingRoom;

    private LocalDate reservedDate;

    private Integer roomId;

    public ReservableRoom() {
    }

    public ReservableRoom(ReservableRoomId reservableRoomId) {
        this.reservableRoomId = reservableRoomId;
        this.reservedDate = reservableRoomId.reservedDate().value;
        this.roomId = reservableRoomId.roomId().value();
    }

    public ReservableRoom(ReservableRoomId reservableRoomId, MeetingRoom meetingRoom) {
        this.reservableRoomId = reservableRoomId;
        this.meetingRoom = meetingRoom;
        this.reservedDate = reservableRoomId.reservedDate().value;
        this.roomId = reservableRoomId.roomId().value();
    }

    public MeetingRoom meetingRoom() {
        return meetingRoom;
    }

    public ReservableRoomId reservableRoomId() {
        return reservableRoomId;
    }

    public LocalDate reservedDate() {
        return reservedDate;
    }

    public Integer roomId() {
        return roomId;
    }
}
