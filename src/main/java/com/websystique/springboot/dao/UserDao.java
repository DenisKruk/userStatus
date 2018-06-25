package com.websystique.springboot.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websystique.springboot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class UserDao {

    public static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private static final ObjectMapper mapper = new ObjectMapper();
//    private static final String url = "jdbc:mysql://localhost:3306/users";
//    private static final String user = "root";
//    private static final String password = "root";
//    private static Connection con;
//    private static Statement stmt;
//    private static ResultSet rs;

//     метод для базы данных
//    public static List<User> readAll() {
    //---------подключение к серверу MySQL ----------
//        List<User>users=new ArrayList<>();
//        try {
//            con = DriverManager.getConnection(url, user, password);
//            stmt = con.createStatement();
//            rs=stmt.executeQuery("SELECT * FROM users ;");
//            while (rs.next()) {
//                try {
//                    User user = new User();
//                    user.setId(Integer.valueOf(rs.getString("id")));
//                    user.setName(rs.getString("name"));
//                    user.setSecondName(rs.getString("second_name"));
//                    user.seteMail(rs.getString("e_mail"));
//                    user.setStatus(Integer.valueOf(rs.getString("status")));
//                    users.add(user);
//                } catch (SQLException e) {
//
//                }
//            }
//
//        } catch (SQLException sqlEx) {
//            sqlEx.printStackTrace();
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException se) {
//            }
//            try {
//                stmt.close();
//            } catch (SQLException se) {
//            }
//        }
//
//        return readAll();
//    }
//по аналогии можно создать соединение с MySQL для сохранения, удаления и обновления...
// (различие  в использовании Result Set и executeQuery для получения ответа от БД и последующей его обработки;
// и использование executeUpdate без получения ответа от БД)

    public static List<User> readAll() {
        //---------------Чтение файла JSon--------------
        try (FileInputStream fis = new FileInputStream("users.json")) {// открытие потока, для чтения файла users.json

            List<User> usersDao = mapper.readValue(fis, new TypeReference<List<User>>() {
            });
            usersDao.sort(User::compare); // сортировка по ID
            return usersDao;
        } catch (IOException ex) {
            logger.error("ошибка в процессе чтения файла", ex.getMessage());
        }
        return Collections.emptyList();

    }


    public static void saveInFile(List<User> users) {
        try (FileOutputStream fos = new FileOutputStream("users.json")) {
            String listOfUsers = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
            fos.write(listOfUsers.getBytes());
            fos.flush();
        } catch (IOException e) {
            logger.error("ошибка в процессе сохранения в файл", e.getMessage());
        }
    }
}