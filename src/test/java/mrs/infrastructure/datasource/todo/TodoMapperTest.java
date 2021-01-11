package mrs.infrastructure.datasource.todo;

import mrs.domain.model.todo.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TodoMapperTest {
    @Autowired
    TodoMapper todoMapper;

    private Todo newTodo;

    @BeforeEach
    void setUp() {
        newTodo = new Todo(
                1,
                "飲み会",
                "銀座 19:00",
                false
        );
        todoMapper.insert(newTodo);
    }

    @Test
    void やることが登録される() throws Exception {
        Todo result = todoMapper.select(1);
        assertEquals("飲み会", result.title());
        assertEquals("銀座 19:00", result.details());
    }

    @Test
    void やることを完了にできる() throws Exception {
        Todo result = todoMapper.select(1);
        assertFalse(result.finished());

        todoMapper.update(result.finish());
        result = todoMapper.select(1);
        assertTrue(result.finished());
    }

    @Test
    void やることの内容を更新できる() throws Exception {
        Todo firstTodo = new Todo(
                2,
                "飲み会",
                "銀座 19:00",
                false
        );
        todoMapper.insert(firstTodo);
        Todo result = todoMapper.select(2);
        assertEquals("銀座 19:00", result.details());

        Todo update = new Todo(result.id(), result.title(), "銀座 20:00", result.finished());
        todoMapper.update(update);
        result = todoMapper.select(2);
        assertEquals("銀座 20:00", result.details());
    }

}
