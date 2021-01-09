package mrs.infrastructure.datasource.todo;

import mrs.domain.model.todo.Todo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoMapper {
    void insert(Todo todo);
    Todo select(int id);
}
