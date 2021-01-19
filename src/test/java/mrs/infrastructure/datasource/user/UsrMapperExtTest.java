package mrs.infrastructure.datasource.user;

import mrs.MrsDBTest;
import mrs.domain.model.user.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@MrsDBTest
@DisplayName("予約ユーザーマッパー")
public class UsrMapperExtTest {
    @Autowired
    UsrMapperExt usrMapper;

    @Test
    void ユーザーを登録する() {
        User user = new User(
                new UserId("user"),
                new Password("password"),
                new Name("山田", "太郎"),
                RoleName.USER
        );
        usrMapper.insert(user);

        User result = usrMapper.selectByPrimaryKey("user");

        assertNotNull(result);
    }
}
