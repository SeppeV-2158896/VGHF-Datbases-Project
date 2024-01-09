package be.vghf.repository;

import be.vghf.domain.Console;
import be.vghf.domain.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConsoleRepository implements Repository{
    public ConsoleRepository(){}

    public List<Console> getAll(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Console.class);
        var root = query.from(Console.class);

        query.select(root);

        return GenericRepository.query(query);
    }

    public List<Console> getConsoleByName(String[] name){
        Set<Console> consoles = new HashSet<>();
        for(String str : name){
            var criteriaBuilder = EntityManagerSingleton.getInstance().getCriteriaBuilder();
            var query = criteriaBuilder.createQuery(Console.class);
            var root = query.from(Console.class);

            query.where(criteriaBuilder.like(root.get("consoleName"), "%" + str + "%"));

            List<Console> queryResults = GenericRepository.query(query);

            consoles.addAll(queryResults);
        }
        return new ArrayList<>(consoles);
    }
}
