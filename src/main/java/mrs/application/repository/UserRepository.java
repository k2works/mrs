package mrs.application.repository;

import mrs.domain.model.user.User;
import mrs.domain.model.user.UserId;

/**
 * ユーザーリポジトリ
 */
public interface UserRepository {
    User findById(String username);

    void save(User user);

    void delete(UserId userId);
}
