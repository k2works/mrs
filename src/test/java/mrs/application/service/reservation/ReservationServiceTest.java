package mrs.application.service.reservation;

import mrs.MrsDBTest;
import mrs.application.repository.MeetingRoomRepository;
import mrs.application.repository.ReservableRoomRepository;
import mrs.application.repository.ReservationRepository;
import mrs.application.repository.UserRepository;
import mrs.domain.model.facility.room.MeetingRoom;
import mrs.domain.model.facility.room.RoomId;
import mrs.domain.model.facility.room.RoomName;
import mrs.domain.model.reservation.Reservation;
import mrs.domain.model.reservation.ReservationId;
import mrs.domain.model.reservation.datetime.ReservedDate;
import mrs.domain.model.reservation.datetime.ReservedDateTime;
import mrs.domain.model.reservation.datetime.ReservedTime;
import mrs.domain.model.reservation.reservable.room.ReservableRoom;
import mrs.domain.model.reservation.reservable.room.ReservableRoomId;
import mrs.domain.model.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MrsDBTest
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

        @BeforeEach
        void clean() {
            reservationRepository.deleteAll();
            reservableRoomRepository.deleteAll();
            meetingRoomRepository.deleteAll();
        }

        @Test
        public void 会議室を予約が成功したら予約データが登録される() {
            ユーザーを登録する(userRepository);
            MeetingRoom room = 会議室を作る(meetingRoomRepository);
            ReservableRoom reservableRoom = 予約が可能な会議室を作る(room);
            予約会議室を登録する(reservableRoom, reservableRoomRepository);
            Reservation reservation = 予約を作る(new ReservationId(1), reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
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
            Reservation reservation = 予約を作る(new ReservationId(1), reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
            予約する(reservationService, reservation);
            Reservation reservation2 = 予約を作る(new ReservationId(2), reservableRoom, LocalTime.of(11, 0), LocalTime.of(12, 0));
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
            Reservation reservation = 予約を作る(new ReservationId(1), reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
            予約する(reservationService, reservation);
            Reservation reservation2 = 予約を作る(new ReservationId(2), reservableRoom, LocalTime.of(9, 30), LocalTime.of(10, 30));

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
            Reservation reservation = 予約を作る(new ReservationId(1), reservableRoom, LocalTime.of(9, 0), LocalTime.of(12, 0));
            予約する(reservationService, reservation);
            Reservation reservation2 = 予約を作る(new ReservationId(2), reservableRoom, LocalTime.of(10, 0), LocalTime.of(11, 0));

            Throwable result = assertThrows(AlreadyReservedException.class, () -> {
                予約する(reservationService, reservation2);
            });
            assertEquals("入力の時間帯はすでに予約済です。", result.getMessage());
        }
    }

    private Reservation 予約を作る(ReservationId id, ReservableRoom room, LocalTime start, LocalTime end) {
        ReservedTime time = new ReservedTime(start, end);
        User user = ユーザーを作る();
        ReservedDate date = new ReservedDate(room.reservableRoomId().reservedDate().value());
        ReservedDateTime dateTime = new ReservedDateTime(date, time);
        return new Reservation(id, dateTime, room, user);
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
        ReservedDate reservedDate = new ReservedDate(LocalDate.now());
        ReservedTime reservedTime = new ReservedTime(null, null);
        User user = ユーザーを作る();
        ReservedDateTime reservedDateTime = new ReservedDateTime(reservedDate, reservedTime);
        return new Reservation(reservedDateTime, reservableRoom, user);
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

        @BeforeEach
        void clean() {
            reservationRepository.deleteAll();
            reservableRoomRepository.deleteAll();
            meetingRoomRepository.deleteAll();
        }

        @Test
        @WithMockUser(username = "test", roles = {"ADMIN"})
        public void 予約をキャンセルしたら予約データが削除される() {
            ユーザーを登録する(userRepository);
            MeetingRoom room = 会議室を作る(meetingRoomRepository);
            ReservableRoom reservableRoom = 予約が可能な会議室を作る(room);
            予約会議室を登録する(reservableRoom, reservableRoomRepository);
            Reservation reservation = 予約を作る(new ReservationId(1), reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
            予約する(reservationService, reservation);

            Reservation cancelReservation = reservationRepository.getOne(1);
            キャンセルする(reservationService, cancelReservation);

            List<Reservation> result = 予約を全件検索する(reservationRepository);
            assertEquals(0, result.size());
        }
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

