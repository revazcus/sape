package com;

import com.dao.ClientDao;
import com.service.ClientCsvLoader;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SapeApplication.class)
class ClientDaoImplTest {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private ClientCsvLoader clientCsvLoader;


    @Test
    @Transactional
    void testGetPhonesForClientsYoungerThan30() throws IOException {
        clientCsvLoader.load("data/Clients.csv");
        List<String> phones = clientDao.getPhonesForClientsYoungerThan(30);
        assertEquals(2, phones.size());
        assertTrue(phones.contains("+79262853853"));
        assertTrue(phones.contains("+7-916-453-34-12"));
    }
}
