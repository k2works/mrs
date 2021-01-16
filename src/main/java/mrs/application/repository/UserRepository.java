package mrs.application.repository;

import mrs.domain.model.user.User;

/**
 * ユーザーリポジトリ
 */
public interface UserRepository {
    User findById(String username);

    void save(User user);

    void delete(String username);
}
