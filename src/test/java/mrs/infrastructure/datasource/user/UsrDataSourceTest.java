package mrs.infrastructure.datasource.user;

import mrs.MrsDBTest;
import mrs.domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MrsDBTest
public class UsrDataSourceTest {
    @Autowired
    UsrDataSource usrDataSource;

    @Test
    void 予約ユーザーを取得する() {
        User result = usrDataSource.findById("test");
        assertNotNull(result);
    }
}
