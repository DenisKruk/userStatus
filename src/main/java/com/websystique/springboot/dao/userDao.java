package com.websystique.springboot.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websystique.springboot.model.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class userDao {
    private static final AtomicLong counter = new AtomicLong();

    private static List<User> usersDao;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String url = "jdbc:mysql://localhost:3306/users";
    private static final String user = "root";
    private static final String password = "root";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static List<User> readAll() {
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
//
//                    users.add(user);
//                } catch (SQLException e) {
        try {
            FileInputStream fis = new FileInputStream("users.json");// открытие потока, для чтения файла users.json

            usersDao = mapper.readValue(fis, new TypeReference<List<User>>() {
            });
            return usersDao;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();

    }

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
//        return users;
//    }

public  static void saveInFile(List<User>any){
    try(FileOutputStream fos = new FileOutputStream("users.json")) {
        String listOfusers = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(any);
        fos.write(listOfusers.getBytes());
        fos.flush();

    } catch (IOException e) { e.getMessage();
    }}
}