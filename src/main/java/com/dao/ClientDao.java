package com.dao;

import com.model.Client;

import java.util.List;

public interface ClientDao {

    void saveALL(List<Client> clients);

    List<String> getPhonesForClientsYoungerThan(int age);

}
