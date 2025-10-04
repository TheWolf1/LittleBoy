package com.little_boy.little_boy.Infrastructure.Commands;

import com.little_boy.little_boy.Application.UseCases.CheckAppointmentHandler;
import com.little_boy.little_boy.Domain.Client.Entities.Client;
import com.little_boy.little_boy.Domain.Client.Services.ClientService;
import com.little_boy.little_boy.Domain.Office.Entities.Office;
import com.little_boy.little_boy.Domain.Office.Services.OfficeService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Component;

@Component
public class CheckAppointmentCommand
{
    private final ClientService clientService;
    private final OfficeService officeService;

    public CheckAppointmentCommand(ClientService clientService, OfficeService officeService) {
        this.clientService = clientService;
        this.officeService = officeService;
    }


    public void run(String... args) throws Exception {

        System.out.println("Ejecutamos el comando de check appointment");

        System.setProperty("webdriver.firefox.driver", "/usr/bin/firefox");

        FirefoxOptions firefoxOptions = new FirefoxOptions();

        // Headless en servidor y tamaño estable (Firefox usa --width/--height)
        firefoxOptions.addArguments("--headless");
        firefoxOptions.addArguments("--width=1920");
        firefoxOptions.addArguments("--height=1080");

        // Lanzar Selenium
        WebDriver driver = new FirefoxDriver(firefoxOptions);

        Client client = this.clientService.findById(1L);
        Office office = this.officeService.findById(3L);

        CheckAppointmentHandler handler = new CheckAppointmentHandler(driver, client, office);
        handler.handle();

        driver.quit();

        System.out.println("✅ Proceso finalizado");


    }
}
