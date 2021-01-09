package mrs.application.repository;

import mrs.domain.model.todo.Todo;

import java.util.List;

/**
 * やることレポジトリ
 */
public interface TodoRepository {
    List<Todo> selectAllJoin();
}
