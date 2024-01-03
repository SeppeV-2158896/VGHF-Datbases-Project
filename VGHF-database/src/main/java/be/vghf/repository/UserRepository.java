package be.vghf.repository;

import be.vghf.domain.User;

import java.util.List;

public class UserRepository {
    public UserRepository(){}

    public List<User> getAllUsers() {
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(User.class);
        var root = query.from(User.class);

        query.select(root);

        return Repository.query(query);
    }
}
