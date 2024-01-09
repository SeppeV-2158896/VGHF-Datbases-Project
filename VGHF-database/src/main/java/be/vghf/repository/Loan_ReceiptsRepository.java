package be.vghf.repository;

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
}
