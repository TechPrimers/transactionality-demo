package com.techprimers.transactionalitydemo.service;

import com.techprimers.transactionalitydemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void insert(List<User> users) {

        for (User user : users) {
            System.out.println("Inserting Data for User name: " + user.getName());
            jdbcTemplate.update("insert into USER(Name, Dept, Salary) values (?, ?, ?)",
                    preparedStatement -> {
                        preparedStatement.setString(1, user.getName());
                        preparedStatement.setString(2, user.getDept());
                        preparedStatement.setLong(3, user.getSalary());
                    });
        }
    }

    public List<User> getUsers() {
        System.out.println("Retrieve all Users List...");
        List<User> userList = jdbcTemplate.query("select Name, Dept, Salary from USER", (resultSet, i) -> new User(resultSet.getString("Name"),
                resultSet.getString("Dept"),
                resultSet.getLong("Salary")));

        return userList;
    }

}
