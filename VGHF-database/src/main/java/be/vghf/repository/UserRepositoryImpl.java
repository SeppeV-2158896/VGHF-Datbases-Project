package be.vghf.repository;

import be.vghf.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    public UserRepositoryImpl(){}

    @Override
    public List<User> getAllUsers(EntityManager entityManager) {
        var query = entityManager.getCriteriaBuilder().createQuery(User.class);
        var root = query.from(User.class);

        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }

}
