package be.vghf.repository;

import be.vghf.domain.Game;
import be.vghf.domain.Loan_Receipts;
import be.vghf.domain.User;

import java.util.List;

public class Loan_ReceiptsRepository implements Repository{
    public Loan_ReceiptsRepository(){}

    public static List getLoansByUser(User user) {
        var entityManager = EntityManagerSingleton.getInstance();
        var query = entityManager.createQuery(
                "SELECT lr FROM Loan_Receipts lr WHERE lr.customer = :user");
        query.setParameter("user", user);

        return query.getResultList();
    }

    public List<Loan_Receipts> getAll(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Loan_Receipts.class);
        var root = query.from(Loan_Receipts.class);

        query.select(root);

        return GenericRepository.query(query);
    }

    public static Loan_Receipts getActiveLoanByGame(Game game) {
        var entityManager = EntityManagerSingleton.getInstance();
        var query = entityManager.createQuery(
                "SELECT lr FROM Loan_Receipts lr WHERE lr.game = :game AND lr.returnDate IS NULL", Loan_Receipts.class);
        query.setParameter("game", game);
        query.setMaxResults(1); // To retrieve only one result

        List<Loan_Receipts> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
