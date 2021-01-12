package mrs.application.repository;

import mrs.domain.model.reservation.ReservableRoomId;
import mrs.domain.model.reservation.Reservation;

import java.util.List;
import java.util.Optional;

/**
 * 予約レポジトリ
 */
public interface ReservationRepository {
    List<Reservation> findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(ReservableRoomId reservableRoomId);

    Reservation getOne(Integer id);

    List<Reservation> findAll();

    void save(Reservation reservation);

    void delete(Reservation reservation);

    Optional<Reservation> findById(int i);
}
