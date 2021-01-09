package mrs.infrastructure.datasource.todo;

import mrs.application.repository.TodoRepository;
import mrs.domain.model.todo.Todo;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TodoDataSource implements TodoRepository {
    TodoMapper todoMapper;

    public TodoDataSource(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    @Override
    @Transactional
    public List<Todo> selectAllJoin() {
        return todoMapper.selectAllJoin();
    }
}
