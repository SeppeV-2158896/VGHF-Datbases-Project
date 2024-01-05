package be.vghf.repository;

import be.vghf.domain.Console;
import be.vghf.domain.Game;

import java.util.ArrayList;
import java.util.List;

public class ConsoleRepository implements Repository{
    public ConsoleRepository(){}

    public List<Console> getAll(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Console.class);
        var root = query.from(Console.class);

        query.select(root);

        return GenericRepository.query(query);
    }

    public List<Console> getConsoleByName(String[] name){
        ArrayList<Console> consoles = new ArrayList<>();
        for(String str : name){
            var criteriaBuilder = EntityManagerSingleton.getInstance().getCriteriaBuilder();
            var query = criteriaBuilder.createQuery(Console.class);
            var root = query.from(Console.class);

            query.where(criteriaBuilder.like(root.get("consoleName"), "%" + str + "%"));

            List<Console> queryResults = GenericRepository.query(query);

            for(Console console : queryResults){
                if(!consoles.contains(console)){
                    consoles.add(console);
                }
            }
        }
        return consoles;
    }
}
