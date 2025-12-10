package com.techlab.store.service;

import com.techlab.store.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.techlab.store.repository.ClientRepository;
import com.techlab.store.entity.Client;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private final ClientRepository clientRepository;
    private final StringUtils stringUtils;


    public ClientService(ClientRepository clientRepository, StringUtils stringUtils) {
        this.clientRepository = clientRepository;
        this.stringUtils = stringUtils;
    }

    public Client save(Client cliente) {
        if (!cliente.getEmail().contains("@")) {
            throw new RuntimeException("Formato de email no valido: ");
        }
        return clientRepository.save(cliente);
    }


    public List<Client> findById(Long id) {
        return this.clientRepository.findAll();
    }

    public Client getClientById(Long id){
        Optional<Client> clientOptional = this.clientRepository.findById(id);

        if (clientOptional.isEmpty()){
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }

        return clientOptional.get();
    }


    public Client editClientById(Long id, Client dataToEdit){
        Client client = this.getClientById(id);

        if (!stringUtils.isEmpty(dataToEdit.getName())){
            System.out.printf("Editando el nombre del cliente: viejo:%s - nuevo:%s", client.getName(), dataToEdit.getName());
            client.setName(dataToEdit.getName());
        }

        return this.clientRepository.save(client);
    }



    public List<Client> findAllClient(String name){

        if (!name.isEmpty()){
            return this.clientRepository.findByNameContainingIgnoreCase(name);
        }

        return this.clientRepository.findAll();
    }

    public Client deleteClientById(Long id) {
        Client client = this.getClientById(id);

        client.setDeleted(true);
        this.clientRepository.save(client);

        return client;
    }


    public Client editProductById(Long id, Client dataToEdit){
        Client client = this.getClientById(id);

        if (!stringUtils.isEmpty(dataToEdit.getName())){
            System.out.printf("Editando el nombre del producto: viejo:%s - nuevo:%s", client.getName(), dataToEdit.getName());
            client.setName(dataToEdit.getName());
        }

        return this.clientRepository.save(client);
    }
}