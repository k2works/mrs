package mrs.infrastructure.datasource;

import mrs.domain.model.Usr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UsrMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr
     *
     * @mbg.generated
     */
    @Delete({
        "delete from usr",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr
     *
     * @mbg.generated
     */
    @Insert({
        "insert into usr (user_id, first_name, ",
        "last_name, password, ",
        "role_name)",
        "values (#{userId,jdbcType=VARCHAR}, #{firstName,jdbcType=VARCHAR}, ",
        "#{lastName,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{roleName,jdbcType=VARCHAR})"
    })
    int insert(Usr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr
     *
     * @mbg.generated
     */
    int insertSelective(Usr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "user_id, first_name, last_name, password, role_name",
        "from usr",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    @ResultMap("mrs.infrastructure.datasource.UsrMapper.BaseResultMap")
    Usr selectByPrimaryKey(String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Usr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr
     *
     * @mbg.generated
     */
    @Update({
        "update usr",
        "set first_name = #{firstName,jdbcType=VARCHAR},",
          "last_name = #{lastName,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "role_name = #{roleName,jdbcType=VARCHAR}",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Usr record);
}