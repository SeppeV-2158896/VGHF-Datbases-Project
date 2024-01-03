package be.vghf.repository;

import be.vghf.domain.Console;
import be.vghf.domain.Game;

import java.util.List;

public class ConsoleRepository {
    public ConsoleRepository(){}

    public List<Console> getAllConsoles(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Console.class);
        var root = query.from(Console.class);

        query.select(root);

        return GenericRepository.query(query);
    }
}
