package mrs.application.service.reservation;

import mrs.application.repository.ReservableRoomRepository;
import mrs.application.repository.ReservationRepository;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRoomId;
import mrs.domain.model.reservation.Reservation;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 予約サービス
 */
@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservableRoomRepository reservableRoomRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservableRoomRepository reservableRoomRepository) {
        this.reservationRepository = reservationRepository;
        this.reservableRoomRepository = reservableRoomRepository;
    }

    /**
     * 会議室を予約する
     */
    public Reservation reserve(Reservation reservation) {
        ReservableRoomId reservableRoomId = reservation.reservableRoom().reservableRoomId();
        // 悲観ロック
        ReservableRoom reservable = reservableRoomRepository.findOneForUpdateByReservableRoomId(reservableRoomId);
        if (reservable == null) {
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

    /**
     * 会議室の予約をキャンセルする
     */
    @PreAuthorize("hasRole('ADMIN') or #reservation.user.userId == principal.user.userId")
    public void cancel(@P("reservation") Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    /**
     * 会議室の予約を探す
     */
    public Reservation findOne(Integer reservationId) {
        return reservationRepository.getOne(reservationId);
    }

    /**
     * 予約可能会議室を探す
     */
    public List<Reservation> findReservations(ReservableRoomId reservableRoomId) {
        return reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);
    }
}
