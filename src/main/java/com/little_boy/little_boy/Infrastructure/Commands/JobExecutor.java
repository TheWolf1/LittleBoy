package com.little_boy.little_boy.Infrastructure.Commands;

import com.little_boy.little_boy.Domain.Schedule.Entities.ClientOfficeJob;
import com.little_boy.little_boy.Domain.Schedule.Repositories.ClientOfficeJobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobExecutor {

    private final ClientOfficeJobRepository jobRepository;

    public JobExecutor(ClientOfficeJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Scheduled(fixedRate = 60000) // cada minuto revisa los jobs
    public void checkJobs() {
        List<ClientOfficeJob> jobs = jobRepository.findAll();

        for (ClientOfficeJob job : jobs) {
            if (!job.isActive()) continue;

            // Aquí puedes interpretar tu cron o intervalo
            if (shouldRun(job)) {
                System.out.println("🚀 Ejecutando job: " + job.getId());


                // Aquí llamas al Handler real
                // new CheckAppointmentHandler(driver, job.getClient(), job.getOffice()).handle();
            }
        }
    }

    private boolean shouldRun(ClientOfficeJob job) {
        // Lógica para decidir si toca ejecutar este job ahora
        // Ejemplo: parsear cron con Quartz o almacenar "lastExecuted" en la entidad
        return true;
    }
}
