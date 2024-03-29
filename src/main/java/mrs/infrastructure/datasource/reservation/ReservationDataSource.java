package mrs.infrastructure.datasource.reservation;

import mrs.application.repository.ReservationRepository;
import mrs.domain.model.reservation.Reservation;
import mrs.domain.model.reservation.reservable.room.ReservableRoomId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservationDataSource implements ReservationRepository {
    ReservationMapperExt reservationMapper;

    public ReservationDataSource(ReservationMapperExt reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<Reservation> findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(ReservableRoomId reservableRoomId) {
        return reservationMapper.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);
    }

    @Override
    public Reservation getOne(Integer id) {
        return reservationMapper.findById(id);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationMapper.findAll();
    }

    @Override
    public void save(Reservation reservation) {
        reservationMapper.insertSelective(reservation);
    }

    @Override
    public void saveById(Reservation reservation) {
        reservationMapper.insert(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservationMapper.deleteByPrimaryKey(reservation.reservationId().value());
    }

    @Override
    public Optional<Reservation> findById(int reservationId) {
        return Optional.ofNullable(reservationMapper.findById(reservationId));
    }

    @Override
    public void deleteAll() {
        reservationMapper.findAll().forEach(i -> reservationMapper.deleteByPrimaryKey(i.reservationId().value()));
    }
}
