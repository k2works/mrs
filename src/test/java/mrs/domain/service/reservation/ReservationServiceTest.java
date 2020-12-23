package mrs.domain.service.reservation;

import mrs.MrsApplication;
import mrs.domain.model.*;
import mrs.domain.repository.reservation.ReservationRepository;
import mrs.domain.repository.reservation.UserRepository;
import mrs.domain.repository.room.MeetingRoomRepository;
import mrs.domain.repository.room.ReservableRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = MrsApplication.class)
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
        Integer roomId = 1;
        LocalDate date = LocalDate.now();
        ReservableRoom reservableRoom = new ReservableRoom(
                new ReservableRoomId(roomId, date));
        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalTime.of(9, 0));
        reservation.setEndTime(LocalTime.of(10, 0));
        reservation.setReservableRoom(reservableRoom);
        reservation.setUser(dummyUser());
        reservationService.reserve(reservation);

        Reservation result = reservationRepository.getOne(1);
        assertNotNull(result);
    }

    @Test
    public void 該当する予約された会議室が存在しなければ例外メッセージを表示する() {
        Integer roomId = 1;
        LocalDate date = LocalDate.now();
        ReservableRoom reservableRoom = new ReservableRoom(
                new ReservableRoomId(roomId, date));
        Reservation reservation = new Reservation();
        reservation.setReservableRoom(reservableRoom);

        Throwable result = assertThrows(UnavailableReservationException.class, () -> {
            reservationService.reserve(reservation);
        });
        assertEquals("入力の日付・部屋の組み合わせは予約できません。", result.getMessage());
    }

    @Test
    public void 予約時刻が重複しなければ登録できる() {
        userRepository.save(dummyUser());
        MeetingRoom room = new MeetingRoom();
        room.setRoomName("会議室A");
        meetingRoomRepository.save(room);
        Integer roomId = 1;
        LocalDate date = LocalDate.now();
        ReservableRoom reservableRoom = new ReservableRoom(
                new ReservableRoomId(roomId, date));
        reservableRoom.setMeetingRoom(room);
        reservableRoomRepository.save(reservableRoom);

        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalTime.of(9, 0));
        reservation.setEndTime(LocalTime.of(10, 0));
        reservation.setReservableRoom(reservableRoom);
        reservation.setUser(dummyUser());
        reservationService.reserve(reservation);

        Reservation reservation2 = new Reservation();
        reservation2.setStartTime(LocalTime.of(11, 00));
        reservation2.setEndTime(LocalTime.of(12, 00));
        reservation2.setReservableRoom(reservableRoom);
        reservation2.setUser(dummyUser());
        reservationService.reserve(reservation2);

        List<Reservation> result = reservationRepository.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void 予約時刻が重複すれば登録できない_パターン1() {
        userRepository.save(dummyUser());
        MeetingRoom room = new MeetingRoom();
        room.setRoomName("会議室A");
        meetingRoomRepository.save(room);
        Integer roomId = 1;
        LocalDate date = LocalDate.now();
        ReservableRoom reservableRoom = new ReservableRoom(
                new ReservableRoomId(roomId, date));
        reservableRoom.setMeetingRoom(room);
        reservableRoomRepository.save(reservableRoom);

        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalTime.of(9, 0));
        reservation.setEndTime(LocalTime.of(10, 0));
        reservation.setReservableRoom(reservableRoom);
        reservation.setUser(dummyUser());
        reservationService.reserve(reservation);

        Reservation reservation2 = new Reservation();
        reservation2.setStartTime(LocalTime.of(9, 30));
        reservation2.setEndTime(LocalTime.of(10, 30));
        reservation2.setReservableRoom(reservableRoom);
        reservation2.setUser(dummyUser());

        Throwable result = assertThrows(AlreadyReservedException.class, () -> {
            reservationService.reserve(reservation2);
        });
        assertEquals("入力の時間帯はすでに予約済です。", result.getMessage());
    }

    @Test
    public void 予約時刻が重複すれば登録できない_パターン2() {
        userRepository.save(dummyUser());
        MeetingRoom room = new MeetingRoom();
        room.setRoomName("会議室A");
        meetingRoomRepository.save(room);
        Integer roomId = 1;
        LocalDate date = LocalDate.now();
        ReservableRoom reservableRoom = new ReservableRoom(
                new ReservableRoomId(roomId, date));
        reservableRoom.setMeetingRoom(room);
        reservableRoomRepository.save(reservableRoom);

        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalTime.of(9, 0));
        reservation.setEndTime(LocalTime.of(12, 0));
        reservation.setReservableRoom(reservableRoom);
        reservation.setUser(dummyUser());
        reservationService.reserve(reservation);

        Reservation reservation2 = new Reservation();
        reservation2.setStartTime(LocalTime.of(10, 0));
        reservation2.setEndTime(LocalTime.of(11, 0));
        reservation2.setReservableRoom(reservableRoom);
        reservation2.setUser(dummyUser());

        Throwable result = assertThrows(AlreadyReservedException.class, () -> {
            reservationService.reserve(reservation2);
        });
        assertEquals("入力の時間帯はすでに予約済です。", result.getMessage());
    }

    @Test
    public void 予約を取り消す() {
        userRepository.save(dummyUser());
        MeetingRoom room = new MeetingRoom();
        room.setRoomName("会議室A");
        meetingRoomRepository.save(room);
        Integer roomId = 1;
        LocalDate date = LocalDate.now();
        ReservableRoom reservableRoom = new ReservableRoom(
                new ReservableRoomId(roomId, date));
        reservableRoom.setMeetingRoom(room);
        reservableRoomRepository.save(reservableRoom);

        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalTime.of(9, 0));
        reservation.setEndTime(LocalTime.of(10, 0));
        reservation.setReservableRoom(reservableRoom);
        reservation.setUser(dummyUser());
        reservationService.reserve(reservation);
        reservationService.cancel(reservation.getReservationId(), reservation.getUser());

        List<Reservation> result = reservationRepository.findAll();
        assertEquals(0, result.size());
    }

    private User dummyUser() {
        User user = new User();
        user.setUserId("taro-yamada");
        user.setFirstName("太郎");
        user.setLastName("山田");
        user.setRoleName(RoleName.USER);
        user.setPassword("$2a$10$oxSJ1.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK");
        return user;
    }
}
