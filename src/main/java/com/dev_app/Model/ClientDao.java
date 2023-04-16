package com.dev_app.Model;

import org.springframework.jdbc.core.JdbcTemplate;

public interface ClientDao {
    boolean CustomerExist(JdbcTemplate DriverDB , String mail, String password);

    String GetId(JdbcTemplate DriverDB, String mail, String password);

    boolean CustomerHaveTicket(JdbcTemplate DriverDB, String id);

    Client GetData(JdbcTemplate DriverDB, String id);
}
