package be.vghf.repository;

import be.vghf.domain.Game;
import be.vghf.domain.Loan_Receipts;

import java.util.List;

public class Loan_ReceiptsRepository {
    public Loan_ReceiptsRepository(){}

    public List<Loan_Receipts> getAllLoanReceipts(){
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(Loan_Receipts.class);
        var root = query.from(Loan_Receipts.class);

        query.select(root);

        return GenericRepository.query(query);
    }
}
