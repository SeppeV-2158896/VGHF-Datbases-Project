package be.vghf.repository;

import be.vghf.domain.Game;
import be.vghf.domain.Loan_Receipts;
import be.vghf.domain.Location;
import be.vghf.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

    public static Set<Game> getGameByName(String[] name){
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

    public static void deleteGameWithReferences(Game game) {
        EntityTransaction transaction = EntityManagerSingleton.getInstance().getTransaction();

        try {
            transaction.begin();

            // Get all loan receipts related to the game
            List<Loan_Receipts> loanReceipts = EntityManagerSingleton.getInstance().createQuery(
                            "SELECT lr FROM Loan_Receipts lr WHERE lr.game = :game", Loan_Receipts.class)
                    .setParameter("game", game)
                    .getResultList();

            // Delete loan receipts related to the game
            for (Loan_Receipts loanReceipt : loanReceipts) {
                GenericRepository.delete(loanReceipt);
            }

            // Get all locations referencing the game
            List<Location> locations = EntityManagerSingleton.getInstance().createQuery(
                            "SELECT game.currentLocation FROM Game game WHERE game.currentLocation = :location", Location.class)
                    .setParameter("location", game.getCurrentLocation())
                    .getResultList();

            // Clear references to the game in locations
            for (Location location : locations) {
                location.getCurrentGames().remove(game);
            }

            GenericRepository.delete(game);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


}
