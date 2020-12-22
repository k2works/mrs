package mrs.domain.service.room;

import mrs.domain.model.MeetingRoom;
import mrs.domain.model.ReservableRoom;
import mrs.domain.repository.room.MeetingRoomRepository;
import mrs.domain.repository.room.ReservableRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RoomService {

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    public MeetingRoom findMeetingRoom(Integer roomId) {
        return meetingRoomRepository.getOne(roomId);
    }

    @Autowired
    ReservableRoomRepository reservableRoomRepository;

    public List<ReservableRoom> findReservableRooms(LocalDate date) {
       return reservableRoomRepository.findByReservableRoomId_ReservedDateOrderByReservableRoomId_RoomIdAsc(date);
    }
}
