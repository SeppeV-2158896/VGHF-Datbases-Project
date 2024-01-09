package be.vghf.repository;

import be.vghf.domain.Dev_company;
import be.vghf.domain.Game;
import be.vghf.domain.Loan_Receipts;
import be.vghf.domain.User;

import javax.persistence.criteria.Predicate;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.round;

public class UserRepository implements Repository{
    public UserRepository(){}

    public static List<User> getUserByName(String name) {

        var criteriaBuilder = EntityManagerSingleton.getInstance().getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(User.class);
        var root = query.from(User.class);

        Predicate[] predicates = new Predicate[3];

        predicates[0] = criteriaBuilder.like(root.get("username"),"%" + name + "%");
        predicates[1] = criteriaBuilder.like(root.get("firstName"),"%" + name + "%");
        predicates[2] = criteriaBuilder.like(root.get("lastName"),"%" + name + "%");

        query.where(criteriaBuilder.or(predicates));

        return GenericRepository.query(query);
    }



    public List<User> getAll() {
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(User.class);
        var root = query.from(User.class);

        query.select(root);

        return GenericRepository.query(query);
    }

    public List<User> getUserByAddress(String[] address){
        Set<User> users = new HashSet<>();

        for(String str : address){
            var criteriaBuilder = EntityManagerSingleton.getInstance().getCriteriaBuilder();
            var query = criteriaBuilder.createQuery(User.class);
            var root = query.from(User.class);

            Predicate[] predicates = new Predicate[4];

            predicates[0] = criteriaBuilder.like(root.get("streetName"),"%" + str + "%");
            predicates[1] = criteriaBuilder.like(root.get("postalCode"),"%" + str + "%");
            predicates[2] = criteriaBuilder.like(root.get("city"),"%" + str + "%");
            predicates[3] = criteriaBuilder.like(root.get("country"),"%" + str + "%");

            query.where(criteriaBuilder.or(predicates));

            List<User> queryResults = GenericRepository.query(query);

            users.addAll(queryResults);
        }
        return new ArrayList<>(users);
    }

    public static String hashPassword(String input){
        //source: https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(input.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            return sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Double getTotalFine(User user){
        var entityManager = EntityManagerSingleton.getInstance();
        var query = entityManager.createQuery(
                "SELECT SUM(CAST(lr.fine AS float )) FROM Loan_Receipts lr WHERE lr.customer = :user AND lr.fine IS NOT NULL",
                Double.class
        );
        query.setParameter("user", user);

        return (double) round(query.getSingleResult() == null ? 0 : query.getSingleResult());
    }

    public static Double getOutstandingFine(User user) {
        var entityManager = EntityManagerSingleton.getInstance();
        var query = entityManager.createQuery(
                "SELECT SUM(CASE WHEN lr.returnDate IS NULL " +
                        "THEN (julianday('now') - julianday(lr.loanedDate, '+' || lr.loanTerm || ' days')) " +
                        "ELSE (julianday(lr.returnDate) - julianday(lr.loanedDate, '+' || lr.loanTerm || ' days')) END) " +
                        "FROM Loan_Receipts lr WHERE lr.customer = :user " +
                        "AND (lr.returnDate IS NULL AND (julianday('now') - julianday(lr.loanedDate, '+' || lr.loanTerm || ' days')) > 0 " +
                        "OR lr.returnDate IS NOT NULL AND (julianday(lr.returnDate) - julianday(lr.loanedDate, '+' || lr.loanTerm || ' days')) > 0)",
                Double.class
        );
        query.setParameter("user", user);

        return (double) round((query.getSingleResult() == null ? 0 : query.getSingleResult()*0.25));
    }

    public static Integer getCurrentAmountOfLoanedItems(User user){
        var entityManager = EntityManagerSingleton.getInstance();
        var query = entityManager.createQuery(
                "SELECT COUNT(lr) FROM Loan_Receipts lr WHERE lr.customer = :user AND lr.returnDate IS NULL",
                Long.class
        );
        query.setParameter("user", user);

        return (int) (query.getSingleResult() == null ? 0 : query.getSingleResult());
    }

}
