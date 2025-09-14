package com.little_boy.little_boy.Application.Controllers;

import com.little_boy.little_boy.Domain.Client.Entities.Client;
import com.little_boy.little_boy.Domain.Client.Services.ClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Client save(@RequestBody Client client) {
        return clientService.save(client);
    }
}
