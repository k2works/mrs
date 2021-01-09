package mrs.infrastructure.datasource;

import mrs.domain.model.todo.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TodoMapperTest {
    @Autowired
    TodoMapper todoMapper;

    @Test
    void やることが登録される() throws Exception {
        Todo newTodo = new Todo();
        newTodo.setTitle("飲み会");
        newTodo.setDetails("銀座 19:00");
        todoMapper.insert(newTodo);

        Todo result = todoMapper.select(1);
        assertEquals("飲み会",result.getTitle());
        assertEquals("銀座 19:00",result.getDetails());
    }
}
