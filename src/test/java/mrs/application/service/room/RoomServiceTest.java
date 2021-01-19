package mrs.application.service.room;

import mrs.MrsDBTest;
import mrs.application.repository.MeetingRoomRepository;
import mrs.application.repository.ReservableRoomRepository;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRoomId;
import mrs.domain.model.reservation.ReservableRooms;
import mrs.domain.model.reservation.ReservedDate;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@MrsDBTest
@DisplayName("会議室サービス")
public class RoomServiceTest {
    @Nested
    @DisplayName("会議室を探す")
    class SearchMeetingRoom {
        @Autowired
        RoomService roomService;

        @Autowired
        private MeetingRoomRepository meetingRoomRepository;

        @Autowired
        private ReservableRoomRepository reservableRoomRepository;


        @Test
        public void 会議室が登録されていれば取得できる() {
            meetingRoomRepository.save(new MeetingRoom(new RoomId(1), new RoomName("会議室")));

            RoomId id = new RoomId(1);
            MeetingRoom room = roomService.findMeetingRoom(id);

            assertEquals(id, room.roomId());
        }
    }

    @Nested
    @DisplayName("予約可能会議室集合を探す")
    class SearchReservableRooms {
        @Autowired
        RoomService roomService;

        @Autowired
        private MeetingRoomRepository meetingRoomRepository;

        @Autowired
        private ReservableRoomRepository reservableRoomRepository;


        @Test
        public void 予約可能会議室が存在すれば予約可能会議室集合を取得できる() {
            java.util.stream.IntStream.rangeClosed(1, 2)
                    .mapToObj(i -> new MeetingRoom(new RoomId(i), new RoomName("会議室")))
                    .forEach(j -> meetingRoomRepository.save(j));
            ReservedDate date = new ReservedDate(LocalDate.now());
            java.util.stream.IntStream.rangeClosed(1, 2)
                    .mapToObj(i -> new ReservableRoomId(new RoomId(i), date))
                    .map(ReservableRoom::new).forEach(k -> reservableRoomRepository.save(k));

            ReservableRooms rooms = roomService.findReservableRooms(date);

            assertEquals(2, rooms.value().size());
        }
    }
}

