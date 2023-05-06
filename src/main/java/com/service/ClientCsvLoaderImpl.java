package com.service;

import com.dao.ClientDao;
import com.model.Client;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ClientCsvLoaderImpl implements ClientCsvLoader {

    private static final Logger log = Logger.getLogger(ClientCsvLoaderImpl.class.getName());

    private final ClientDao clientDao;

    public ClientCsvLoaderImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }


    public void load(String file) {
        // Получаем ресурс по имени файла
        ClassPathResource resource = new ClassPathResource(file);

        log.info("Читаем из файла " + file);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            // Пропускаем первую строку
            reader.readLine();

            log.info("Парсим содержимое с преобразованием в список Client");
            List<Client> clients = reader.lines()
                    // Параллельно читаем строки файла
                    .parallel()
                    // Отфильтровываем пустые строки
                    .filter(line -> !line.isEmpty())
                    // Преобразуем каждую строку в объект Client
                    .map(line -> {
                        String[] fields = line.split(",");
                        String name = fields.length > 0 && fields[0] != null ? fields[0].trim() : "";
                        int age = fields.length > 1 && fields[1] != null ? Integer.parseInt(fields[1].trim()) : 0;
                        int groupId = fields.length > 2 && fields[2] != null ? Integer.parseInt(fields[2].trim()) : 0;
                        String phone = fields.length > 3 && fields[3] != null ? fields[3].trim() : "";
                        LocalDate date = fields.length > 4 && fields[4] != null && fields[4].trim().matches("\\d{4}-\\d{2}-\\d{2}") ? LocalDate.parse(fields[4].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
                        return new Client(name, age, groupId, phone, date);
                    })
                    // Собираем список объектов Client
                    .toList();
            log.info("Список собран в количестве записей: " + clients.size());
            // Сохраняем список объектов Client в БД
            clientDao.saveALL(clients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
