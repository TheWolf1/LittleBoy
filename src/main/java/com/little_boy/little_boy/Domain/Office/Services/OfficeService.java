package com.little_boy.little_boy.Domain.Office.Services;

import com.little_boy.little_boy.Domain.Office.Entities.Office;
import com.little_boy.little_boy.Domain.Office.Repositories.OfficeRepository;
import org.springframework.stereotype.Service;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public Office save(Office office) {
        return officeRepository.save(office);
    }

    public Office findById(Long id) {
        return officeRepository.findById(id).orElse(null);
    }

    public void delete(Office office) {
        officeRepository.delete(office);
    }

    public Iterable<Office> findAll() {
        return officeRepository.findAll();
    }
}
