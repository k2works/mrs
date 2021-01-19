package mrs.application.service.room;

import mrs.application.repository.MeetingRoomRepository;
import mrs.application.repository.ReservableRoomRepository;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRooms;
import mrs.domain.model.reservation.ReservedDate;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 会議室サービス
 */
@Service
@Transactional
public class RoomService {
    private final MeetingRoomRepository meetingRoomRepository;
    private final ReservableRoomRepository reservableRoomRepository;

    public RoomService(MeetingRoomRepository meetingRoomRepository, ReservableRoomRepository reservableRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
        this.reservableRoomRepository = reservableRoomRepository;
    }

    /**
     * 会議室を探す
     */
    public MeetingRoom findMeetingRoom(RoomId roomId) {
        return meetingRoomRepository.findBy(roomId.value());
    }

    /**
     * 予約可能会議室一覧を探す
     */
    public ReservableRooms findReservableRooms(ReservedDate date) {
        List<ReservableRoom> result = reservableRoomRepository.findByReservableRoomId_ReservedDateOrderByReservableRoomId_RoomIdAsc(date.value());
        return new ReservableRooms(result);
    }
}
