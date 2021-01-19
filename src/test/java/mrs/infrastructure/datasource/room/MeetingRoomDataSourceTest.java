package mrs.infrastructure.datasource.room;

import mrs.MrsDBTest;
import mrs.application.repository.MeetingRoomRepository;
import mrs.application.repository.ReservableRoomRepository;
import mrs.application.repository.ReservationRepository;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MrsDBTest
@DisplayName("会議室データソース")
public class MeetingRoomDataSourceTest {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservableRoomRepository reservableRoomRepository;
    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    @BeforeEach
    void clean() {
        reservationRepository.deleteAll();
        reservableRoomRepository.deleteAll();
        meetingRoomRepository.deleteAll();
    }

    @Test
    void 会議室を登録する() {
        MeetingRoom room = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomRepository.save(room);

        MeetingRoom result = meetingRoomRepository.findBy(1);

        assertNotNull(result);
    }

    @Test
    void 会議室を削除する() {
        MeetingRoom room = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomRepository.save(room);

        MeetingRoom result = meetingRoomRepository.findBy(1);
        meetingRoomRepository.delete(result);
        result = meetingRoomRepository.findBy(1);

        assertNull(result);
    }

    @Test
    void 会議室一覧を取得できる() {
        MeetingRoom room = new MeetingRoom(new RoomId(1), new RoomName("会議室1"));
        meetingRoomRepository.save(room);
        room = new MeetingRoom(new RoomId(2), new RoomName("会議室2"));
        meetingRoomRepository.save(room);

        List<MeetingRoom> result = meetingRoomRepository.findAll();

        assertEquals(2, result.size());
    }
}
