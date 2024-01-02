package be.vghf.repository;

import be.vghf.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
}
