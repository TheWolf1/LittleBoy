package com.little_boy.little_boy.Application.UseCases;

import com.little_boy.little_boy.Domain.Client.Entities.Client;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AppointmentManager {

    private final WebDriver driver;
    private final WebDriverWait wait;


    public AppointmentManager(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
    }

    public void writeTextInputManager(String id, String value){
        WebElement textInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        textInput.clear();
        textInput.sendKeys(value);

    }


    public void selectInputManager(String id, String visibleText){

        WebElement selectInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));

        Select select = new Select(selectInput);

        select.selectByVisibleText(visibleText);
    }


    public void clickOnButton(String idElement){
        WebElement btn = this.driver.findElement(By.id(idElement));

        btn.click();
    }

    public void submitOnButton(String idElement){
        WebElement btn = this.driver.findElement(By.id(idElement));

        btn.submit();
    }

    public void waitAndClickOnButton(String idElement){
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idElement)));

        btn.click();
    }



    public void selectCity(String cityName){

        selectInputManager("form", cityName);

        waitAndClickOnButton("btnAceptar");
    }


    public void selectOfficeAndProcedure(String officeAddress, String procedure){
        selectInputManager("sede", officeAddress);

        selectInputManager("tramiteGrupo[0]",procedure);

        waitAndClickOnButton("btnAceptar");
    }

    public void setDataClient(Client client){

        writeTextInputManager("txtIdCitado", client.getIdentify());
        writeTextInputManager("txtDesCitado", client.getName()+" "+client.getLastname());
    }

    public boolean hayCitasDisponibles() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(),'En este momento no hay citas disponibles')]")
            ));
            System.out.println("No hay citas disponibles.");
            return false;
        } catch (Exception e) {
            System.out.println("¡Sí hay citas!");
            return true;
        }
    }
}
