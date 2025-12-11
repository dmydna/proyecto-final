package com.techlab.store.controller;

import com.techlab.store.entity.Client;
import com.techlab.store.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public Client createCliente(@RequestBody Client cliente) {
        return clientService.save(cliente);
    }

    @GetMapping
    public List<Client> getAllClients(
            @RequestParam(required = false, defaultValue = "") String name){
        return this.clientService.findAllClient(name);
    }
    @GetMapping("/{id}")
    public Client getClienteById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PutMapping("/{id}")
    public Client editClientById(@PathVariable Long id, @RequestBody Client dataToEdit){
        return this.clientService.editClientById(id, dataToEdit);
    }

    @DeleteMapping("/{id}")
    public Client deleteProductById(@PathVariable Long id){
        return this.clientService.deleteClientById(id);
    }

}
