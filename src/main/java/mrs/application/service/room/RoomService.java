package mrs.application.service.room;

import mrs.application.repository.MeetingRoomRepository;
import mrs.application.repository.ReservableRoomRepository;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.room.MeetingRoom;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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

    public MeetingRoom findMeetingRoom(Integer roomId) {
        return meetingRoomRepository.getOne(roomId);
    }

    public List<ReservableRoom> findReservableRooms(LocalDate date) {
        return reservableRoomRepository.findByReservableRoomId_ReservedDateOrderByReservableRoomId_RoomIdAsc(date);
    }
}
