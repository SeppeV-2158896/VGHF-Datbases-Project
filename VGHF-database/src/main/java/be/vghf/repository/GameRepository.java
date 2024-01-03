package be.vghf.repository;

import be.vghf.domain.Game;

import java.util.List;

public class GameRepository {
    public GameRepository(){}

    public List<Game> getAllGames(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Game.class);
        var root = query.from(Game.class);

        query.select(root);

        return Repository.query(query);
    }
}
