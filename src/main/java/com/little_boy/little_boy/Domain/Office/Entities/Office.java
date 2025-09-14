package com.little_boy.little_boy.Domain.Office.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "offices")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String city;
    private String address;
    @Column(name = "procedure_name")
    private String procedure;
    private String input_address_id;
    private String input_procedure_id;


    public Office(){}

    public Office(String id, String city ,String address, String procedure, String input_address_id, String input_procedure_id) {
        this.city = city;
        this.address = address;
        this.procedure = procedure;
        this.input_address_id = input_address_id;
        this.input_procedure_id = input_procedure_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getInput_address_id() {
        return input_address_id;
    }

    public void setInput_address_id(String input_address_id) {
        this.input_address_id = input_address_id;
    }

    public String getInput_procedure_id() {
        return input_procedure_id;
    }

    public void setInput_procedure_id(String input_procedure_id) {
        this.input_procedure_id = input_procedure_id;
    }
}
