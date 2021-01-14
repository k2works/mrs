package mrs.infrastructure.datasource.reservation;

import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRoomId;
import mrs.infrastructure.datasource.ReservableRoomMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservableRoomMapperExt extends ReservableRoomMapper {
    ReservableRoom selectByPrimaryKey(ReservableRoomId reservableRoomId);

    List<ReservableRoom> selectAll();
}
