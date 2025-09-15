package com.little_boy.little_boy.Application.UseCases;

import com.little_boy.little_boy.Domain.Client.Entities.Client;
import com.little_boy.little_boy.Domain.Office.Entities.Office;
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

        // 1. Verificar status code antes de Selenium
        if (!isUrlAvailable()) {
            System.out.println("⚠️ El servidor devolvió 429 Too Many Requests. Abortando proceso.");
            return;
        }

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

    private boolean isUrlAvailable() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE))
                    .header("User-Agent", "Mozilla/5.0")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            System.out.println("📡 Status code recibido: " + statusCode);

            return statusCode != 429; // si es 429 → bloquear
        } catch (Exception e) {
            e.printStackTrace();
            return false; // si falla la conexión, también bloquea
        }
    }
}

