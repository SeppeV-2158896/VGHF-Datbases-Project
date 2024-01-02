package be.vghf.repository;

import be.vghf.domain.User;

import java.security.MessageDigest;
import java.util.List;

public class UserRepository {
    public UserRepository(){}

    public List<User> getAllUsers() {
        var query = EntityManagerSingleton.getInstance().getCriteriaBuilder().createQuery(User.class);
        var root = query.from(User.class);

        query.select(root);

        return Repository.query(query);
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

}
