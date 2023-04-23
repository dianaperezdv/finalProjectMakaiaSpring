package com.example.finalProject.Modules;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Setter
@Getter
public class Usuario {
    @Id
    @Column(name = "cedula", length = 10)
    private Integer cedula;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column (name = "correoElectronico")
    private String correoElectronico;
    @Column(name = "celular")
    private String celular;
    @Column(name = "direccionResidencia")
    private String direccionResidencia;
    @Column(name = "ciudadResidencia")
    private String ciudadResidencia;

    public Usuario(Integer cedula, String nombre, String apellido, String correoElectronico, String celular, String direccionResidencia, String ciudadResidencia) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.celular = celular;
        this.direccionResidencia = direccionResidencia;
        this.ciudadResidencia = ciudadResidencia;
    }

    public Usuario(){}
}