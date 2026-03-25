package repository;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static UserRepository instance;
    private List<User> users = new ArrayList<>();

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void add(User u) {
        users.add(u);
    }

    public User authenticate(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) &&
                    u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}