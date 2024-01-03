package be.vghf.repository;

import be.vghf.domain.Game;

import java.util.List;

public class GameRepository implements Repository{
    public GameRepository(){}

    public List<Game> getAll(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Game.class);
        var root = query.from(Game.class);

        query.select(root);

        return GenericRepository.query(query);
    }
}
