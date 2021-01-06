package mrs.application.repository;

import mrs.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ユーザーリポジトリ
 */
public interface UserRepository extends JpaRepository<User, String> {
}
