package mrs.application.coordinator.reservation;

import mrs.application.service.reservation.ReservationService;
import mrs.application.service.room.RoomService;
import mrs.application.service.user.ReservationUserDetails;
import mrs.domain.model.reservation.*;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import org.springframework.stereotype.Service;

/**
 * 会議室予約業務
 */
@Service
public class ReservationCoordinator {
    ReservationService reservationService;
    RoomService roomService;

    public ReservationCoordinator(ReservationService reservationService, RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    /**
     * 会議室を予約する
     */
    public void reserveMeetingRoom(ReservedTime reservedTime, ReservableRoom reservableRoom, ReservationUserDetails userDetails) {
        ReservedDate reservedDate = new ReservedDate(reservableRoom.reservableRoomId().reservedDate().value());
        Reservation reservation = new Reservation(
                reservedDate,
                reservedTime,
                reservableRoom,
                userDetails.getUser()
        );
        reservationService.reserve(reservation);
    }

    /**
     * 会議室の予約をキャンセルする
     */
    public void cancelReservedMeetingRoom(ReservationId reservationId) {
        Reservation reservation = reservationService.findOne(reservationId);
        reservationService.cancel(reservation);
    }

    /**
     * 会議室の予約一覧を探す
     */
    public Reservations searchReservations(ReservableRoomId reservableRoomId) {
        return reservationService.findReservations(reservableRoomId);
    }

    /**
     * 会議室を探す
     */
    public MeetingRoom searchMeetingRoom(RoomId roomId) {
        return roomService.findMeetingRoom(roomId);
    }

    /**
     * 予約可能会議室一覧を探す
     */
    public ReservableRooms searchReservableRooms(ReservedDate reservedDate) {
        return roomService.findReservableRooms(reservedDate);
    }
}
