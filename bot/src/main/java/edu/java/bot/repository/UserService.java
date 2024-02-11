package edu.java.bot.repository;

import edu.java.bot.users.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserService {

    private final Map<Long, User> users = new HashMap<>();

    public void saveUser(User user) {
        users.put(user.getId(), user);
    }

    public Optional<User> findUserById(Long id) {
        return (users.containsKey(id)) ? Optional.of(users.get(id)) : Optional.empty();
    }
}
