package com.websystique.springboot.controller;

import java.util.List;

import com.websystique.springboot.service.UserService;
import com.websystique.springboot.service.UserServiceImpl;
import com.websystique.springboot.util.CustomErrorType;
import com.websystique.springboot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/api")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    // -------------------Получить список пользователей---------------------------------------------

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // -------------------Получить пользователя по id------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Поис пользователя с ID =", id);
        User user = userService.findById(id);
        if (user == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Пользователь с id " + id
                    + " не найден"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // -------------------Создаем нового пользователя-------------------------------------------

    @RequestMapping(value = "/user/new", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Создание нового пользователя", user);

        if (userService.isUserExist(user)) {
            logger.error("Не возможно создать нового пользователя, пользователь уже существует", user.getName());
            return new ResponseEntity(new CustomErrorType("Не возможно создать нового пользователя с введенными данными, пользователь уже существует " ),HttpStatus.CONFLICT);
        }
        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }


    // ------------------- Удаляем пользователя-----------------------------------------

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting com.websystique.springboot.modelebsystique.springboot.User with id {}", id);

        User user = userService.findById(id);
        if (user == null) {
            logger.error("Невозможно удалить пользователя, пользователь не найден", id);
            return new ResponseEntity(new CustomErrorType("Пользователь с данным id = " + id + " не найден."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }



}