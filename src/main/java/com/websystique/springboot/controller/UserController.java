package com.websystique.springboot.controller;

import com.websystique.springboot.model.User;
import com.websystique.springboot.service.UserService;
import com.websystique.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    // -------------------Получить список пользователей---------------------------------------------

    @RequestMapping(value = "/user/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> listAllUsers() {
        return userService.findAllUsers();
    }

    // -------------------Получить пользователя по id------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/user/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Создание нового пользователя", user);

        if (userService.isUserExist(user)) {
            logger.error("Не возможно создать нового пользователя, пользователь уже существует", user.getName());
            return new ResponseEntity(new CustomErrorType("Не возможно создать нового пользователя с введенными данными, пользователь уже существует "), HttpStatus.CONFLICT);
        }
        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        logger.info("Создан новый пользователь ID =" + user.getId());
        return new ResponseEntity(" Создан пользователь с ID=" + user.getId(), HttpStatus.CREATED);
    }


    // ------------------- Удаляем пользователя-----------------------------------------

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        logger.info("Находим и удаляем пользователя по id", id);

        User user = userService.findById(id);
        if (user == null) {
            logger.error("Невозможно удалить пользователя, пользователь не найден", id);
            return new ResponseEntity(new CustomErrorType("Пользователь с данным id = " + id + " не найден."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Изменяем статус пользователя-----------------------------------------
    @RequestMapping(value = "/user/status/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeUserStatus(@PathVariable("id") long id, UriComponentsBuilder ucBuilder) {
        logger.info("Находим и меняем статус пользователя", id);
        User user = userService.findById(id);
        if (user == null) {
            logger.error("Невозможно изменить статус пользователяб нет такого пользователя", id);
            return new ResponseEntity(new CustomErrorType("Пользователь с данным id = \" + id + \" не найден."),
                    HttpStatus.NOT_FOUND);
        }
        userService.changeStatus(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/status/{id}").buildAndExpand(user.getStatus()).toUri());
        if (user.getStatus() == 1) {
            logger.info("Cтатус  пользователя ID =" + user.getId() + " поменялся с 2 на 1", id);
            return new ResponseEntity(" Cтатус  пользователя ID =" + user.getId() + " поменялся с 2 на 1", HttpStatus.CREATED);
        } else {
            logger.info("Cтатус  пользователя ID =" + user.getId() + " поменялся с 1 на 2", id);
            return new ResponseEntity(" Cтатус  пользователя ID =" + user.getId() + " поменялся с 1на 2", HttpStatus.CREATED);
        }
    }
}
