package com.websystique.springboot.service;

import com.websystique.springboot.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.websystique.springboot.dao.UserDao.readAll;
import static com.websystique.springboot.dao.UserDao.saveInFile;


@Service("userService")
public class UserServiceImpl implements UserService {

    private static Long unicalId() {
        Long count = 1L;
        List<User> users = readAll();
        if (users.isEmpty()) {
            return count;
        }
        for (User user : users) {
            if (count <= user.getId()) {
                count = user.getId() + 1;
            }
        }
        return count;
    }


    @Override
    public synchronized List<User> findAllUsers() {
        List<User> users = readAll();
        return users;
    }

    @Override
    public synchronized User findById(long id) {
        List<User> users = readAll();
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public synchronized void saveUser(User user) {
        List<User> users = readAll();
        user.setId(unicalId());
        users.add(user);
        saveInFile(users);
    }

    @Override
    public synchronized void deleteUserById(long id) {
        List<User> users = readAll();
        users.removeIf(user -> user.getId() == id);
        saveInFile(users);
    }

    public boolean isUserExist(User user) {
        List<User> users = readAll();
        for (User user1 : users) {
            if (user.equals(user1)) {
                return true; // здесь изменил тк у тебя получалось что цикл шел до конца, не смотря на то что существующий юзер найден
            }
        }
        return false;
    }

    @Override
    public synchronized void changeStatus(User user) {
        List<User> users = readAll();
        for (User user1 : users) {
            if (user1.getId() == user.getId()) {
                if (user1.getStatus() == 1) {
                    user1.setStatus(2);
                    saveInFile(users);
                } else {
                    user1.setStatus(1);
                }
                saveInFile(users);
            }
        }
    }
}