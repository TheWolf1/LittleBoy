package com.little_boy.little_boy.Domain.Client.Repositories;

import com.little_boy.little_boy.Domain.Client.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
