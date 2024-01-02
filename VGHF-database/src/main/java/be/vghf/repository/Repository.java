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
    public String hashPassword(String input){
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
}
