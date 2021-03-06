package mrs.application.coordinator.reservation;

import mrs.application.service.reservation.ReservationService;
import mrs.application.service.room.RoomService;
import mrs.application.service.user.ReservationUserDetails;
import mrs.domain.model.facility.room.MeetingRoom;
import mrs.domain.model.facility.room.RoomId;
import mrs.domain.model.reservation.Reservation;
import mrs.domain.model.reservation.ReservationId;
import mrs.domain.model.reservation.Reservations;
import mrs.domain.model.reservation.datetime.ReservedDate;
import mrs.domain.model.reservation.datetime.ReservedDateTime;
import mrs.domain.model.reservation.datetime.ReservedTime;
import mrs.domain.model.reservation.reservable.room.ReservableRoom;
import mrs.domain.model.reservation.reservable.room.ReservableRoomId;
import mrs.domain.model.reservation.reservable.room.ReservableRooms;
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
        ReservedDateTime reservedDateTime = new ReservedDateTime(reservedDate, reservedTime);
        Reservation reservation = new Reservation(
                reservedDateTime,
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
