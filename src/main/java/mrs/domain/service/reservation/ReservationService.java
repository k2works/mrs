package mrs.domain.service.reservation;

import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;
import mrs.domain.model.Reservation;
import mrs.domain.repository.reservation.ReservationRepository;
import mrs.domain.repository.room.ReservableRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservableRoomRepository reservableRoomRepository;

    public Reservation reserve(Reservation reservation) {
        ReservableRoomId reservableRoomId = reservation.getReservableRoom().getReservableRoomId();
        // 悲観ロック
        ReservableRoom reservable = reservableRoomRepository.findOneForUpdateByReservableRoomId(reservableRoomId);
        if(reservable == null) {
            throw new UnavailableReservationException("入力の日付・部屋の組み合わせは予約できません。");
        }
        // 重複チェック
        boolean overlap = reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId).stream().anyMatch(x -> x.overlap(reservation));
        if (overlap) {
            throw new AlreadyReservedException("入力の時間帯はすでに予約済です。");
        }
        // 予約情報の登録
        reservationRepository.save(reservation);
        return reservation;
    }

    @PreAuthorize("hasRole('ADMIN') or #reservation.user.userId == principal.user.userId")
    public void cancel(@P("reservation") Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    public Reservation findOne(Integer reservationId) {
        return reservationRepository.getOne(reservationId);
    }

    public List<Reservation> findReservations(ReservableRoomId reservableRoomId) {
        return reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);
    }
}
