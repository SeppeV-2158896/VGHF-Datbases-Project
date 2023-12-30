package be.vghf.repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerSingleton {
    private static final EntityManager instance =
            Persistence.createEntityManagerFactory("be.vghf").createEntityManager();

    public static EntityManager getInstance() {
        return instance;
    }
}
