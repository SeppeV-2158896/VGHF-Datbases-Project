package be.vghf.repository;

import be.vghf.domain.User;

<<<<<<< HEAD
import java.util.List;

public class UserRepository {
    public UserRepository(){}

    public List<User> getAllUsers() {
=======
import java.security.MessageDigest;
import java.util.List;

public class UserRepository implements Repository{
    public UserRepository(){}

    public static List<User> getUserByName(String[] names) {
        String firstname = names[0];
        String lastname = names[1];

        var criteriaBuilder = EntityManagerSingleton.getInstance().getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(User.class);
        var root = query.from(User.class);

        query.where(criteriaBuilder.equal(root.get("firstName"), firstname));
        query.where(criteriaBuilder.equal(root.get("lastName"), lastname));

        return GenericRepository.query(query);
    }

    public List<User> getAll() {
>>>>>>> da937dc3907de5f6ba147234ae4215ae0759e60e
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(User.class);
        var root = query.from(User.class);

        query.select(root);

<<<<<<< HEAD
        return Repository.query(query);
    }
=======
        return GenericRepository.query(query);
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

>>>>>>> da937dc3907de5f6ba147234ae4215ae0759e60e
}
