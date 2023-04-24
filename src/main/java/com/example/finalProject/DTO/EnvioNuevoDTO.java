package com.example.finalProject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioNuevoDTO {

    private Integer cedula;
    private String ciudadOrigen;
    private String ciudadDestino;
    private String direccionDestino;
    private String nombreRecibe;
    private String celularRecibe;
    private double valorDeclarado;
    private float peso;


    public EnvioNuevoDTO(){}
    public EnvioNuevoDTO(Integer cedula, String ciudadOrigen, String ciudadDestino,
                         String direccionDestino, String nombreRecibe,
                         String celularRecibe, double valorDeclarado, float peso){
        this.cedula = cedula;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.direccionDestino = direccionDestino;
        this.nombreRecibe = nombreRecibe;
        this.celularRecibe = celularRecibe;
        this.valorDeclarado = valorDeclarado;
        this.peso = peso;
    }



}
