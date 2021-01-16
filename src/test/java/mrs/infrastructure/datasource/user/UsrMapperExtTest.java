package mrs.infrastructure.datasource.user;

import mrs.domain.model.Usr;
import mrs.domain.model.user.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
public class UsrMapperExtTest {
    @Autowired
    UsrMapperExt usrMapper;

    @Test
    void ユーザーを登録する() {
        Usr user = new Usr();
        user.setUserId("user");
        user.setFirstName("山田");
        user.setLastName("太郎");
        user.setPassword("パスワード");
        user.setRoleName(RoleName.USER.toString());
        usrMapper.insert(user);

        Usr result = usrMapper.selectByPrimaryKey("user");

        assertNotNull(result);
    }
}
