package be.vghf.repository;

import be.vghf.domain.User;

import javax.persistence.Persistence;

public class Repository {
    public Repository(){
        var repo = new UserRepositoryImpl();

        for (User user : repo.getAllUsers(EntityManagerSingleton.getInstance())){
            System.out.println(user.toString());
        }



    }
}
