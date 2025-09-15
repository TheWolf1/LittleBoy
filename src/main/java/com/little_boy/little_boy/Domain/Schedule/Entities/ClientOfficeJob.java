package com.little_boy.little_boy.Domain.Schedule.Entities;

import com.little_boy.little_boy.Domain.Client.Entities.Client;
import com.little_boy.little_boy.Domain.Office.Entities.Office;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "client_office_jobs")
public class ClientOfficeJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Cliente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Relación con Oficina
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    // Programación estilo cron (ejemplo: "0 0 * * * *" → cada hora)
    @Column(name = "cron_expression", nullable = false)
    private String cronExpression;

    // Estado del job
    @Column(name = "active", nullable = false)
    private boolean active = true;

    // Última ejecución para evitar duplicidades
    @Column(name = "last_executed_at")
    private LocalDateTime lastExecutedAt;

    public ClientOfficeJob() {}

    public ClientOfficeJob(Client client, Office office, String cronExpression) {
        this.client = client;
        this.office = office;
        this.cronExpression = cronExpression;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getLastExecutedAt() {
        return lastExecutedAt;
    }

    public void setLastExecutedAt(LocalDateTime lastExecutedAt) {
        this.lastExecutedAt = lastExecutedAt;
    }
}
