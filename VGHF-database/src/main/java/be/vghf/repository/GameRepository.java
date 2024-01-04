package be.vghf.repository;

import be.vghf.domain.Game;
import be.vghf.domain.User;

import java.util.ArrayList;
import java.util.List;

public class GameRepository implements Repository{
    public GameRepository(){}

    public List<Game> getAll(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Game.class);
        var root = query.from(Game.class);

        query.select(root);

        return GenericRepository.query(query);
    }

    public List<Game> getGameByName(String[] name){
        ArrayList<Game> games = new ArrayList<>();
        for(String str : name){
            var criteriaBuilder = EntityManagerSingleton.getInstance().getCriteriaBuilder();
            var query = criteriaBuilder.createQuery(Game.class);
            var root = query.from(Game.class);

            query.where(criteriaBuilder.like(root.get("title"), "%" + str + "%"));

            List<Game> queryResults = GenericRepository.query(query);

            for(Game game : queryResults){
                if(!games.contains(game)){
                    games.add(game);
                }
            }
        }
        return games;
    }
}
