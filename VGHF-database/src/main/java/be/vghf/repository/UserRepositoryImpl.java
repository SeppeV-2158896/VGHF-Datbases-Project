package be.vghf.repository;

import be.vghf.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    public UserRepositoryImpl(){}

    @Override
    public List<User> getAllUsers() {
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(User.class);
        var root = query.from(User.class);

        query.select(root);

        return Repository.query(query);
    }

}
