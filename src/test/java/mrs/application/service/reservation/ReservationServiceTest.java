package mrs.application.service.reservation;

import mrs.MrsApplication;
import mrs.WebSecurityConfig;
import mrs.application.repository.MeetingRoomRepository;
import mrs.application.repository.ReservableRoomRepository;
import mrs.application.repository.ReservationRepository;
import mrs.application.repository.UserRepository;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRoomId;
import mrs.domain.model.reservation.Reservation;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.user.RoleName;
import mrs.domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = MrsApplication.class)
@ContextConfiguration(classes = WebSecurityConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationServiceTest {
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
    public void 会議室を予約する() {
        ReservableRoom reservableRoom = 予約が可能な会議室を作る();
        Reservation reservation = 予約を作る(reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
        予約する(reservation);

        Reservation result = 予約を検索する(1);
        assertNotNull(result);
    }

    @Test
    public void 該当する予約された会議室が存在しなければ例外メッセージを表示する() {
        ReservableRoom reservableRoom = 予約が可能な会議室を作る();
        Reservation reservation = 予約を作る(reservableRoom);

        Throwable result = assertThrows(UnavailableReservationException.class, () -> {
            予約する(reservation);
        });
        assertEquals("入力の日付・部屋の組み合わせは予約できません。", result.getMessage());
    }

    @Test
    public void 予約時刻が重複しなければ登録できる() {
        ユーザーを登録する();
        MeetingRoom room = 会議室を作る();
        ReservableRoom reservableRoom = 予約が可能な会議室を作る();
        予約会議室を登録する(room, reservableRoom);
        Reservation reservation = 予約を作る(reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
        予約する(reservation);
        Reservation reservation2 = 予約を作る(reservableRoom, LocalTime.of(11, 0), LocalTime.of(12, 0));
        予約する(reservation2);

        List<Reservation> result = 予約を全件検索する();
        assertEquals(2, result.size());
    }

    @Test
    public void 予約時刻が重複すれば登録できない_パターン1() {
        ユーザーを登録する();
        MeetingRoom room = 会議室を作る();
        ReservableRoom reservableRoom = 予約が可能な会議室を作る();
        予約会議室を登録する(room, reservableRoom);
        Reservation reservation = 予約を作る(reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
        予約する(reservation);
        Reservation reservation2 = 予約を作る(reservableRoom, LocalTime.of(9, 30), LocalTime.of(10, 30));

        Throwable result = assertThrows(AlreadyReservedException.class, () -> {
            予約する(reservation2);
        });
        assertEquals("入力の時間帯はすでに予約済です。", result.getMessage());
    }

    @Test
    public void 予約時刻が重複すれば登録できない_パターン2() {
        ユーザーを登録する();
        MeetingRoom room = 会議室を作る();
        ReservableRoom reservableRoom = 予約が可能な会議室を作る();
        予約会議室を登録する(room, reservableRoom);
        Reservation reservation = 予約を作る(reservableRoom, LocalTime.of(9, 0), LocalTime.of(12, 0));
        予約する(reservation);
        Reservation reservation2 = 予約を作る(reservableRoom, LocalTime.of(10, 0), LocalTime.of(11, 0));

        Throwable result = assertThrows(AlreadyReservedException.class, () -> {
            予約する(reservation2);
        });
        assertEquals("入力の時間帯はすでに予約済です。", result.getMessage());
    }

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    public void 予約を取り消す() {
        ユーザーを登録する();
        MeetingRoom room = 会議室を作る();
        ReservableRoom reservableRoom = 予約が可能な会議室を作る();
        予約会議室を登録する(room, reservableRoom);
        Reservation reservation = 予約を作る(reservableRoom, LocalTime.of(9, 0), LocalTime.of(10, 0));
        予約する(reservation);
        キャンセルする(reservation);

        List<Reservation> result = 予約を全件検索する();
        assertEquals(0, result.size());
    }

    private void 予約する(Reservation reservation) {
        reservationService.reserve(reservation);
    }

    private void キャンセルする(Reservation reservation) {
        reservationService.cancel(reservation);
    }

    private MeetingRoom 会議室を作る() {
        MeetingRoom room = new MeetingRoom();
        room.setRoomName("会議室A");
        meetingRoomRepository.save(room);
        return room;
    }

    private Reservation 予約を作る(ReservableRoom reservableRoom) {
        Reservation reservation = new Reservation();
        reservation.setReservableRoom(reservableRoom);
        return reservation;
    }

    private Reservation 予約を作る(ReservableRoom reservableRoom, LocalTime start, LocalTime end) {
        Reservation reservation = new Reservation();
        reservation.setStartTime(start);
        reservation.setEndTime(end);
        reservation.setReservableRoom(reservableRoom);
        reservation.setUser(ユーザーを作る());
        return reservation;
    }

    private ReservableRoom 予約が可能な会議室を作る() {
        Integer roomId = 1;
        LocalDate date = LocalDate.now();
        return new ReservableRoom(
                new ReservableRoomId(roomId, date));
    }

    private void 予約会議室を登録する(MeetingRoom room, ReservableRoom reservableRoom) {
        reservableRoom.setMeetingRoom(room);
        reservableRoomRepository.save(reservableRoom);
    }

    private User ユーザーを作る() {
        User user = new User();
        user.setUserId("taro-yamada");
        user.setFirstName("太郎");
        user.setLastName("山田");
        user.setRoleName(RoleName.USER);
        user.setPassword("$2a$10$oxSJ1.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK");
        return user;
    }

    private void ユーザーを登録する() {
        userRepository.save(ユーザーを作る());
    }

    private Reservation 予約を検索する(Integer id) {
        return reservationRepository.getOne(id);
    }

    private List<Reservation> 予約を全件検索する() {
        return reservationRepository.findAll();
    }
}
