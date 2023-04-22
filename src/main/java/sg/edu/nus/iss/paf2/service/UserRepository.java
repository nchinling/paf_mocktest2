package sg.edu.nus.iss.paf2.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static sg.edu.nus.iss.paf2.repository.DBQueries.*;

import sg.edu.nus.iss.paf2.model.User;

@Repository
public class UserRepository {
   
    @Autowired
    JdbcTemplate template;

    public Optional<User> findUserbyUsername(String username){
        
        List<User> usersList = null;
        String query = GET_ALL_USERS;
        Optional<User> userInfo=Optional.empty();

        usersList = template.query(query, (rs, rownum) -> {

            User user = new User();
            user.setUserId(rs.getString("user_id"));
            user.setUsername(rs.getString("username"));
            user.setName(rs.getString("name"));
            return user; 
        });

        for (User user : usersList) {
            if (user.getUsername().equals(username)) {
                userInfo = Optional.of(user);
                break;
            }
        }

        return userInfo;

    }

    public String insertUser(User user){
        String query = INSERT_USER;
        UUID UserId = UUID.randomUUID();
        String UserId8Char = UserId.toString().substring(0, 8);
        user.setUserId(UserId8Char);

        template.update(query, user.getUserId(),
        user.getUsername());
        
        return user.getUserId();
    }
}
