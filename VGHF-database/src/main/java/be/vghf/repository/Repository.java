package be.vghf.repository;

import be.vghf.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import java.security.MessageDigest;
import java.util.List;

public class Repository {
    public Repository(){
    }

    public static <T> void save(T object){
        EntityManager em = EntityManagerSingleton.getInstance();

        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    public static <T> List<T> query(CriteriaQuery<T> query){
        return EntityManagerSingleton.getInstance().createQuery(query).getResultList();
    }

}
