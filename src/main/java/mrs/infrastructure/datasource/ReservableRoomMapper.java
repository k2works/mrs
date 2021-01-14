package mrs.infrastructure.datasource;

import java.time.LocalDate;
import java.util.Date;
import mrs.domain.model.reservation.ReservableRoom;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReservableRoomMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reservable_room
     *
     * @mbg.generated
     */
    @Delete({
        "delete from reservable_room",
        "where reserved_date = #{reservedDate,jdbcType=DATE}",
          "and room_id = #{roomId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(@Param("reservedDate") LocalDate reservedDate, @Param("roomId") Integer roomId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reservable_room
     *
     * @mbg.generated
     */
    @Insert({
        "insert into reservable_room (reserved_date, room_id)",
        "values (#{reservedDate,jdbcType=DATE}, #{roomId,jdbcType=INTEGER})"
    })
    int insert(ReservableRoom record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reservable_room
     *
     * @mbg.generated
     */
    int insertSelective(ReservableRoom record);
}
