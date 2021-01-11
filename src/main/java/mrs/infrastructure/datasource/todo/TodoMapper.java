package mrs.infrastructure.datasource.todo;

import mrs.domain.model.todo.Todo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoMapper {
    void insert(Todo todo);
    Todo select(int id);
    List<Todo> selectAllJoin();
    int update(Todo record);
}
