package be.vghf.repository;

import be.vghf.domain.User;

import javax.persistence.Persistence;

public class Repository {
    public Repository(){
        var factory = Persistence.createEntityManagerFactory("be.vghf.domain");
        var entityManager = factory.createEntityManager();
        var repo = new UserRepositoryImpl();

        for (User user : repo.getAllUsers(entityManager)){
            System.out.println(user.toString());
        }



    }
}
