package com.little_boy.little_boy.Application.UseCases;

import com.little_boy.little_boy.Domain.Client.Entities.Client;
import com.little_boy.little_boy.Domain.Office.Entities.Office;
import org.openqa.selenium.WebDriver;

public class CheckAppointmentHandler {

    public static final String URL_BASE="https://icp.administracionelectronica.gob.es/icpplus/index.html";

    private final Client client;
    private final Office office;
    private final WebDriver driver;


    public CheckAppointmentHandler(WebDriver driver, Client client, Office office){
        this.client = client;
        this.office = office;
        this.driver = driver;
    }


    public void handle(){

        this.driver.get(URL_BASE);

        AppointmentManager manager = new AppointmentManager(this.driver);

        System.out.println("Iniciando proceso de scrapping");

        manager.selectCity(this.office.getCity());

        manager.selectOfficeAndProcedure(this.office.getAddress(), this.office.getProcedure());

        // Nos aseguramos que existe el button y damos en entrar
        manager.waitAndClickOnButton("btnEntrar");

        manager.setDataClient(this.client);

        manager.writeTextInputManager("txtAnnoCitado", this.client.getBirthday());

        manager.selectInputManager("txtPaisNac", this.client.getNationality());


        manager.submitOnButton("btnEnviar");

        if (manager.hayCitasDisponibles()){
            System.out.println("Si hay citas disponibles");
        }else{
            System.out.println("no hay citas disponibles");
        }

    }
}

