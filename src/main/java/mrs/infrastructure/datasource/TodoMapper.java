package mrs.infrastructure.datasource;

import mrs.domain.model.Todo;
import org.apache.ibatis.annotations.Insert;

public interface TodoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo
     *
     * @mbg.generated
     */
    @Insert({
            "insert into todo (id, title, ",
            "details, finished)",
            "values (#{id,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR}, ",
            "#{details,jdbcType=VARCHAR}, #{finished,jdbcType=BIT})"
    })
    int insert(Todo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo
     *
     * @mbg.generated
     */
    int insertSelective(Todo record);
}
