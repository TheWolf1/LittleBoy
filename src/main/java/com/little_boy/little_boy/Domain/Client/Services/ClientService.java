package com.little_boy.little_boy.Domain.Client.Services;


import com.little_boy.little_boy.Domain.Client.Entities.Client;
import com.little_boy.little_boy.Domain.Client.Repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }
}
