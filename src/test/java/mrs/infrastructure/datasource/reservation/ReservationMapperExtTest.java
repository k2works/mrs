package mrs.infrastructure.datasource.reservation;

import mrs.domain.model.Reservation;
import mrs.domain.model.reservation.ReservableRoomId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ReservationMapperExtTest {
    @Autowired
    ReservationMapperExt reservationMapper;

    @Test
    void 予約が登録できる() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1);
        reservationMapper.insert(reservation);

        Reservation result = reservationMapper.selectByPrimaryKey(1);

        assertEquals(1, result.getReservationId());
    }

    @Test
    void 予約を更新できる() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(2);
        reservationMapper.insert(reservation);

        Reservation update = reservationMapper.selectByPrimaryKey(2);
        update.setStartTime(LocalTime.of(10, 0));
        reservationMapper.updateByPrimaryKey(update);
        Reservation result = reservationMapper.selectByPrimaryKey(2);

        assertEquals("10:00", result.getStartTime().toString());
    }

    @Test
    void 予約を削除できる() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(3);
        reservationMapper.insert(reservation);
        Reservation delete = reservationMapper.selectByPrimaryKey(3);

        reservationMapper.deleteByPrimaryKey(delete.getReservationId());
        Reservation result = reservationMapper.selectByPrimaryKey(3);

        assertNull(result);
    }

    @Test
    void 開始時間順に予約可能会議室集合を取得できる() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(4);
        reservationMapper.insert(reservation);

        ReservableRoomId reservableRoomId = new ReservableRoomId();
        List<Reservation> result = reservationMapper.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);

        assertNotNull(result);
    }
}
