package com.websystique.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websystique.springboot.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.websystique.springboot.dao.userDao.readAll;
import static com.websystique.springboot.dao.userDao.saveInFile;


@Service("userService")
public class UserServiceImpl implements UserService {

    private static final AtomicLong counter = new AtomicLong();



    public synchronized List<User> findAllUsers() {

        List<User> users = readAll();
        return users ;
    }

    public synchronized User findById(long id) {
        List<User> users = readAll();
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }


    public synchronized void saveUser(User user) {
        List<User> users = readAll();
        user.setId(counter.incrementAndGet());
        users.add(user);
        saveInFile(users);
    }


    public synchronized void deleteUserById(long id) {
        List<User> users = readAll();
        users.removeIf(user -> user.getId() == id);
        saveInFile(users);
    }

    public boolean isUserExist(User user) {
        List<User> users = readAll();
        boolean result = false;
        for (User user1 : users) {
            if (user.equals(user1)) {
                result = true;
            }
        }
        return result;
    }


}