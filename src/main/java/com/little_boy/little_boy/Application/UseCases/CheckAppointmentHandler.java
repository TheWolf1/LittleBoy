package com.little_boy.little_boy.Application.UseCases;

import com.little_boy.little_boy.Domain.Client.Entities.Client;
import com.little_boy.little_boy.Domain.Office.Entities.Office;
import com.little_boy.little_boy.Infrastructure.Services.WhatsappService;
import org.openqa.selenium.WebDriver;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

        System.out.println("Selecciónando la ciudad de "+this.office.getCity());
        manager.selectCity(this.office.getCity());

        System.out.println("En la oficina "+this.office.getAddress());
        manager.selectOfficeAndProcedure(this.office.getAddress(), this.office.getProcedure());

        // Nos aseguramos que existe el button y damos en entrar
        manager.waitAndClickOnButton("btnEntrar");

        manager.setDataClient(this.client);

        System.out.println("Agregando los datos del cliente"+" "+this.client.getName()+" "+this.client.getLastname());
        manager.writeTextInputManager("txtAnnoCitado", this.client.getBirthday());

        manager.selectInputManager("txtPaisNac", this.client.getNationality());


        manager.submitOnButton("btnEnviar");

        if (manager.hayCitasDisponibles()){
            WhatsappService ws = new WhatsappService();
            // Enviamos notificación por whatsapp si encontramos la cita
            ws.sendNotification("Se ha encontrado una cita disponible en "+this.office.getCity());
        }else{
            System.out.println("No se han encontrado citas disponibles.");
        }

    }


}

