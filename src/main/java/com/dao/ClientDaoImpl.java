package com.dao;

import com.model.Client;
import com.service.ClientCsvLoaderImpl;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Repository
public class ClientDaoImpl implements ClientDao {

    private static final Logger log = Logger.getLogger(ClientDaoImpl.class.getName());


    // Поле для выполнения SQL-запросов к БД
    private final JdbcTemplate jdbcTemplate;

    public ClientDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveALL(List<Client> clients) {
        log.info("Сохраняем список в БД");
        String sql = "INSERT INTO clients (name, age, group_id, phone, date) VALUES (?, ?, ?, ?, ?)";
        // Выполнение запроса в пакетном режиме
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Client client = clients.get(i);
                ps.setString(1, client.getName());
                if (client.getAge() > 0) {
                    ps.setInt(2, client.getAge());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }
                if (client.getGroupId() > 0) {
                    ps.setInt(3, client.getGroupId());
                } else {
                    ps.setNull(3, Types.INTEGER);
                }
                ps.setString(4, client.getPhone());
                if (client.getDate() != null) {
                    ps.setDate(5, Date.valueOf(client.getDate()));
                } else {
                    ps.setNull(5, Types.DATE);
                }
            }

            // Метод, возвращающий количество записей, которые нужно добавить
            @Override
            public int getBatchSize() {
                return clients.size();
            }
        });
        log.info("Сохранение выполнено");
    }

    @Override
    public List<String> getPhonesForClientsYoungerThan(int age) {
        log.info("Вытаскиваем из БД список людей моложе " + age);
        String sql = "SELECT phone FROM clients WHERE age < ?";
        // Выполнение запроса и преобразование результата в список строк
        return jdbcTemplate.queryForList(sql, String.class, age)
                .stream()
                .filter(Objects::nonNull)
                .toList();
    }
}
