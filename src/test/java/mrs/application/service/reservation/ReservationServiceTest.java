package mrs.application.service.reservation;

import mrs.MrsApplication;
import mrs.WebSecurityConfig;
import mrs.application.repository.MeetingRoomRepository;
import mrs.application.repository.ReservableRoomRepository;
import mrs.application.repository.ReservationRepository;
import mrs.application.repository.UserRepository;
import mrs.domain.model.reservation.*;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import mrs.domain.model.user.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
@SpringBootTest(classes = MrsApplication.class)
@ContextConfiguration(classes = WebSecurityConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("予約サービス")
public class ReservationServiceTest {
    @Nested
    @DisplayName("会議室を予約する")
    class Reserve {
        @Autowired
        ReservationService reservationService;
        @Autowired
        ReservationRepository reservationRepository;
        @Autowired
        MeetingRoomRepository meetingRoomRepository;
        @Autowired
        ReservableRoomRepository reservableRoomRepository;
        @Autowired
        UserRepository userRepository;

        @Test
        @Sql("/data.sql")
        @Transactional
        public void 会議室を予約が成功したら予約データが登録される() {
            MeetingRoom room = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
            ReservableRoom reservableRoom = 予約が可能な会議室を作る(room);
            mrs.domain.model.reservation.Reservation reservation = 予約を作る(reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
            予約する(reservationService, reservation);

            mrs.domain.model.reservation.Reservation result = 予約を検索する(1, reservationRepository);
            assertNotNull(result);
        }

        @Test
        public void 該当する予約された会議室が存在しなければ例外メッセージを表示する() {
            MeetingRoom room = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
            ReservableRoom reservableRoom = 予約が可能な会議室を作る(room);
            Reservation reservation = 予約を作る(reservableRoom);

            Throwable result = assertThrows(UnavailableReservationException.class, () -> {
                予約する(reservationService, reservation);
            });
            assertEquals("入力の日付・部屋の組み合わせは予約できません。", result.getMessage());
        }

        @Test
        public void 予約時刻が重複しなければ登録できる() {
            ユーザーを登録する(userRepository);
            MeetingRoom room = 会議室を作る(meetingRoomRepository);
            ReservableRoom reservableRoom = 予約が可能な会議室を作る(room);
            予約会議室を登録する(reservableRoom, reservableRoomRepository);
            Reservation reservation = 予約を作る(reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
            予約する(reservationService, reservation);
            Reservation reservation2 = 予約を作る(reservableRoom, LocalTime.of(11, 0), LocalTime.of(12, 0));
            予約する(reservationService, reservation2);

            List<Reservation> result = 予約を全件検索する(reservationRepository);
            assertEquals(2, result.size());
        }

        @Test
        public void 予約時刻が重複すれば登録できない_パターン1() {
            ユーザーを登録する(userRepository);
            MeetingRoom room = 会議室を作る(meetingRoomRepository);
            ReservableRoom reservableRoom = 予約が可能な会議室を作る(room);
            予約会議室を登録する(reservableRoom, reservableRoomRepository);
            Reservation reservation = 予約を作る(reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
            予約する(reservationService, reservation);
            Reservation reservation2 = 予約を作る(reservableRoom, LocalTime.of(9, 30), LocalTime.of(10, 30));

            Throwable result = assertThrows(AlreadyReservedException.class, () -> {
                予約する(reservationService, reservation2);
            });
            assertEquals("入力の時間帯はすでに予約済です。", result.getMessage());
        }

        @Test
        public void 予約時刻が重複すれば登録できない_パターン2() {
            ユーザーを登録する(userRepository);
            MeetingRoom room = 会議室を作る(meetingRoomRepository);
            ReservableRoom reservableRoom = 予約が可能な会議室を作る(room);
            予約会議室を登録する(reservableRoom, reservableRoomRepository);
            Reservation reservation = 予約を作る(reservableRoom, LocalTime.of(9, 0), LocalTime.of(12, 0));
            予約する(reservationService, reservation);
            Reservation reservation2 = 予約を作る(reservableRoom, LocalTime.of(10, 0), LocalTime.of(11, 0));

            Throwable result = assertThrows(AlreadyReservedException.class, () -> {
                予約する(reservationService, reservation2);
            });
            assertEquals("入力の時間帯はすでに予約済です。", result.getMessage());
        }
    }

    @Nested
    @DisplayName("会議室の予約をキャンセルする")
    class Cancel {
        @Autowired
        ReservationService reservationService;
        @Autowired
        ReservationRepository reservationRepository;
        @Autowired
        MeetingRoomRepository meetingRoomRepository;
        @Autowired
        ReservableRoomRepository reservableRoomRepository;
        @Autowired
        UserRepository userRepository;

        @Test
        @WithMockUser(username = "test", roles = {"ADMIN"})
        public void 予約をキャンセルしたら予約データが削除される() {
            ユーザーを登録する(userRepository);
            MeetingRoom room = 会議室を作る(meetingRoomRepository);
            ReservableRoom reservableRoom = 予約が可能な会議室を作る(room);
            予約会議室を登録する(reservableRoom, reservableRoomRepository);
            Reservation reservation = 予約を作る(reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
            予約する(reservationService, reservation);
            キャンセルする(reservationService, reservation);

            List<Reservation> result = 予約を全件検索する(reservationRepository);
            assertEquals(0, result.size());
        }
    }

    private ReservableRoom 予約が可能な会議室を作る(MeetingRoom room) {
        RoomId roomId = new RoomId(1);
        ReservedDate date = new ReservedDate(LocalDate.now());
        return new ReservableRoom(
                new ReservableRoomId(roomId, date),
                room
        );
    }

    private void 予約会議室を登録する(ReservableRoom reservableRoom, ReservableRoomRepository repository) {
        repository.save(reservableRoom);
    }

    private void 予約する(ReservationService service, Reservation reservation) {
        service.reserve(reservation);
    }

    private void キャンセルする(ReservationService service, Reservation reservation) {
        service.cancel(reservation);
    }

    private MeetingRoom 会議室を作る(MeetingRoomRepository repository) {
        MeetingRoom room = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        repository.save(room);
        return room;
    }

    private Reservation 予約を作る(ReservableRoom reservableRoom) {
        ReservedTime reservedTime = new ReservedTime(null, null);
        Reservation reservation = new Reservation(reservedTime, reservableRoom, null);
        return reservation;
    }

    private Reservation 予約を作る(ReservableRoom reservableRoom, LocalTime start, LocalTime end) {
        ReservedTime reservedTime = new ReservedTime(start, end);
        User user = ユーザーを作る();
        Reservation reservation = new Reservation(reservedTime, reservableRoom, user);
        return reservation;
    }

    private User ユーザーを作る() {
        User user = new User(
                new UserId("tar-yamada"),
                new Password("$2a$10$oxSJ1.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK"),
                new Name("山田", "太郎"),
                RoleName.ADMIN
        );
        return user;
    }

    private void ユーザーを登録する(UserRepository repository) {
        repository.save(ユーザーを作る());
    }

    private Reservation 予約を検索する(Integer id, ReservationRepository repository) {
        return repository.getOne(id);
    }

    private List<Reservation> 予約を全件検索する(ReservationRepository repository) {
        return repository.findAll();
    }
}

