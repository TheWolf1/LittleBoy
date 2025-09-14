package com.little_boy.little_boy.Domain.Office.Repositories;

import com.little_boy.little_boy.Domain.Office.Entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, Long> {
}
