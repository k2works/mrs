package mrs.infrastructure.datasource.todo;

import mrs.domain.model.todo.Todo;
import mrs.infrastructure.datasource.TodoMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoMapperExt extends TodoMapper {
    Todo select(int id);

    int update(Todo record);

    List<Todo> selectAllJoin();
}
