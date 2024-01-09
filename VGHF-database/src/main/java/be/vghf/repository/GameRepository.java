package be.vghf.repository;

import be.vghf.domain.Game;
import be.vghf.domain.Location;
import be.vghf.domain.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameRepository implements Repository{
    public GameRepository(){}

    public List<Game> getAll(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Game.class);
        var root = query.from(Game.class);

        query.select(root);

        return GenericRepository.query(query);
    }

    public Set<Game> getGameByName(String[] name){
        Set<Game> games = new HashSet<>();

        for(String str : name){
            var criteriaBuilder = EntityManagerSingleton.getInstance().getCriteriaBuilder();
            var query = criteriaBuilder.createQuery(Game.class);
            var root = query.from(Game.class);

            query.where(criteriaBuilder.like(root.get("title"), "%" + str + "%"));

            List<Game> queryResults = GenericRepository.query(query);

            games.addAll(queryResults);
        }
        return games;
    }

    public Set<Game> getAllByLocation(Location location) {
        var allGames = getAll();
        return allGames.stream().filter(game -> game.getCurrentLocation() == location).collect(Collectors.toSet());
    }
}
