package com.techlab.store.service;

import com.techlab.store.dto.ClientDTO;
import com.techlab.store.utils.ClientMapper;
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

    @Autowired
    private ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, StringUtils stringUtils) {
        this.clientRepository = clientRepository;
        this.stringUtils = stringUtils;
    }

    public ClientDTO save(ClientDTO dto) {
        if (!dto.getEmail().contains("@")) {
            throw new RuntimeException("Formato de email no valido: ");
        }

        Client newClient = clientMapper.toEntity(dto);
        Client savedClient = clientRepository.save(newClient);
        return clientMapper.toDto(savedClient);
    }


    public List<Client> findById(Long id) {
        return this.clientRepository.findAll();
    }

    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado"));
        return this.clientMapper.toDto(client);
    }

    public ClientDTO editClientById(Long id, ClientDTO dto){
        Client clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        this.clientMapper.updateClientFromDto(dto, clientEntity);
        Client savedEntity = clientRepository.save(clientEntity);
        return clientMapper.toDto(savedEntity);
    }



    public List<ClientDTO> findAllClient(String name){
        List<Client> clientsEntity = null;
        if (!name.isEmpty()){
            clientsEntity = this.clientRepository.findByNameContainingIgnoreCase(name);
        }else{
            clientsEntity = this.clientRepository.findAll();
        }
        return this.clientMapper.toDtoList(clientsEntity);

    }

    public ClientDTO deleteClientById(Long id) {
        Client clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        clientEntity.setDeleted(true);
        this.clientRepository.save(clientEntity);

        ClientDTO dto = this.clientMapper.toDto(clientEntity);
        this.clientMapper.updateClientFromDto(dto, clientEntity);
        return dto;
    }

}