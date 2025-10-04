package com.little_boy.little_boy.Application.UseCases;

import com.little_boy.little_boy.Domain.Client.Entities.Client;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;

import java.time.Duration;

public class AppointmentManager {

    private final WebDriver driver;
    private final WebDriverWait wait;


    public AppointmentManager(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
    }

    private void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'center'});", el
        );
    }

    private void waitOverlaysGone() {
        // Ajusta estos selectores si identificas el overlay real de la web
        String[] selectors = new String[] {
                ".modal.show", ".modal-backdrop", ".overlay", ".loading", ".blockUI", "#capaFondo"
        };
        for (String css : selectors) {
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(css)));
            } catch (Exception ignored) {}
        }
    }

    private void safeClick(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        waitOverlaysGone();
        scrollIntoView(el);
        try {
            el.click();
        } catch (WebDriverException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    public void writeTextInputManager(String id, String value){
        WebElement textInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        scrollIntoView(textInput);
        textInput.clear();
        textInput.sendKeys(value);
    }



    public void selectInputManager(String id, String visibleText){

        WebElement selectInput = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        scrollIntoView(selectInput);
        new Select(selectInput).selectByVisibleText(visibleText);
        // tras seleccionar, suelen dispararse recargas/overlays
        waitOverlaysGone();
        try { wait.until(ExpectedConditions.elementToBeClickable(selectInput)); } catch (Exception ignored) {}

    }


    public void clickOnButton(String idElement){
        safeClick(By.id(idElement));

    }

    public void submitOnButton(String idElement){
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id(idElement)));
        scrollIntoView(btn);
        try {
            btn.submit();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }

    }

    public void waitAndClickOnButton(String idElement){
        safeClick(By.id(idElement));
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
