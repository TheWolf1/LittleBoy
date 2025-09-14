package com.little_boy.little_boy.Infrastructure.APIRest.Controllers;

import com.little_boy.little_boy.Domain.Office.Entities.Office;
import com.little_boy.little_boy.Domain.Office.Services.OfficeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offices")
public class OfficeController {

    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @PostMapping
    public Office save(@RequestBody Office office){
        return officeService.save(office);
    }
}
