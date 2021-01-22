package mrs.infrastructure.datasource.user;

import mrs.application.repository.UserRepository;
import mrs.domain.model.user.User;
import mrs.domain.model.user.UserId;
import org.springframework.stereotype.Repository;

@Repository
public class UsrDataSource implements UserRepository {
    UsrMapperExt usrMapper;

    public UsrDataSource(UsrMapperExt usrMapper) {
        this.usrMapper = usrMapper;
    }

    @Override
    public User findById(String username) {
        return usrMapper.selectByPrimaryKey(username);
    }

    @Override
    public void save(User user) {
        usrMapper.insert(user);
    }

    @Override
    public void delete(UserId userId) {
        usrMapper.deleteByPrimaryKey(userId);
    }
}
