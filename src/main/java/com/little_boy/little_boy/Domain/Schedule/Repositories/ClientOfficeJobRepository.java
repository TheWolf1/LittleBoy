package com.little_boy.little_boy.Domain.Schedule.Repositories;

import com.little_boy.little_boy.Domain.Schedule.Entities.ClientOfficeJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientOfficeJobRepository extends JpaRepository<ClientOfficeJob, Long> {
}
