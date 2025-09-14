package com.little_boy.little_boy.Domain.Office.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "offices")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String procedure;
    private String input_name_id;
    private String input_procedure_id;


    public Office(){}

    public Office(String id, String name, String procedure, String input_name_id, String input_procedure_id) {
        this.name = name;
        this.procedure = procedure;
        this.input_name_id = input_name_id;
        this.input_procedure_id = input_procedure_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getInput_name_id() {
        return input_name_id;
    }

    public void setInput_name_id(String input_name_id) {
        this.input_name_id = input_name_id;
    }

    public String getInput_procedure_id() {
        return input_procedure_id;
    }

    public void setInput_procedure_id(String input_procedure_id) {
        this.input_procedure_id = input_procedure_id;
    }
}
