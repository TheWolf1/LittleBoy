package com.little_boy.little_boy.Infrastructure.Commands;

import com.little_boy.little_boy.Application.UseCases.CheckAppointmentHandler;
import com.little_boy.little_boy.Domain.Schedule.Entities.ClientOfficeJob;
import com.little_boy.little_boy.Domain.Schedule.Repositories.ClientOfficeJobRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JobExecutor {

    private final ClientOfficeJobRepository jobRepository;

    public JobExecutor(ClientOfficeJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Se ejecuta cada minuto
    @Scheduled(fixedRate = 60000)
    public void checkJobs() {
        List<ClientOfficeJob> jobs = jobRepository.findAll();

        LocalDateTime now = LocalDateTime.now();

        for (ClientOfficeJob job : jobs) {
            if (!job.isActive()) continue;

            if (shouldRun(job, now)) {
                System.out.println("🚀 Ejecutando job: " + job.getId() + " a las " + now);

                // Ejecutar el handler real para ese cliente y oficina
                WebDriver driver = null;
                try {
                    System.setProperty("webdriver.firefox.driver", "/usr/bin/firefox");
                    FirefoxOptions firefoxOptions = new FirefoxOptions();

                    // Headless + tamaño estable en Firefox
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");

                    driver = new FirefoxDriver(firefoxOptions);

                    new CheckAppointmentHandler(driver, job.getClient(), job.getOffice()).handle();
                } catch (Exception e) {
                    System.err.println("❌ Error ejecutando job " + job.getId() + ": " + e.getMessage());
                } finally {
                    try { if (driver != null) driver.quit(); } catch (Exception ignored) {}
                }

                // Actualizamos la última ejecución para no repetirlo en el mismo tick
                job.setLastExecutedAt(now);
                jobRepository.save(job);
            }
        }
    }

    private boolean shouldRun(ClientOfficeJob job, LocalDateTime now) {
        try {
            String expr = job.getCronExpression();
            if (expr == null || expr.trim().isEmpty()) return false;

            CronExpression cron = CronExpression.parse(expr.trim());

            // Consideramos el último momento de control; si nunca se ejecutó, miramos el minuto anterior
            LocalDateTime last = job.getLastExecutedAt();
            if (last == null) {
                last = now.minusMinutes(1); // la app está "caliente" y revisa cada minuto
            }

            LocalDateTime next = cron.next(last);
            if (next == null) return false; // no hay futuras ejecuciones

            // Si el próximo instante planificado es <= ahora, toca ejecutarlo
            return !next.isAfter(now);
        } catch (Exception e) {
            System.err.println("⚠️ Cron inválido para job " + job.getId() + ": '" + job.getCronExpression() + "' -> " + e.getMessage());
            return false;
        }
    }
}
