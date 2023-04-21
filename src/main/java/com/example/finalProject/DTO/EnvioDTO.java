package com.example.finalProject.DTO;

public class EnvioDTO {
    private String idGuia;
    private String estadoEnvio;
    private Integer cedula;
    private String ciudadOrigen;
    private String ciudadDestino;
    private String direccionDestino;
    private String nombrePersonaQueRecibe;
    private String celularPersonaQueRecibe;
    private double valorDeclarado;
    private float peso;

    public EnvioDTO(String idGuia, String estadoEnvio){
        this.estadoEnvio = estadoEnvio;
        this.idGuia = idGuia;
    }

    public EnvioDTO(){}
    public EnvioDTO(Integer cedula, String ciudadOrigen, String ciudadDestino,
                    String direccionDestino, String nombrePersonaQueRecibe,
                    String celularPersonaQueRecibe, double valorDeclarado, float peso){
        this.cedula = cedula;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.direccionDestino = direccionDestino;
        this.nombrePersonaQueRecibe = nombrePersonaQueRecibe;
        this.celularPersonaQueRecibe = celularPersonaQueRecibe;
        this.valorDeclarado = valorDeclarado;
        this.peso = peso;
    }

    public EnvioDTO(String idGuia, String estadoEnvio, Integer cedula, String ciudadOrigen, String ciudadDestino,
                    String direccionDestino, String nombrePersonaQueRecibe, String celularPersonaQueRecibe,
                    double valorDeclarado, float peso) {
        this.idGuia = idGuia;
        this.estadoEnvio = estadoEnvio;
        this.cedula = cedula;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.direccionDestino = direccionDestino;
        this.nombrePersonaQueRecibe = nombrePersonaQueRecibe;
        this.celularPersonaQueRecibe = celularPersonaQueRecibe;
        this.valorDeclarado = valorDeclarado;
        this.peso = peso;
    }

    public String getIdGuia() {
        return idGuia;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public Integer getCedula() {
        return cedula;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public String getNombrePersonaQueRecibe() {
        return nombrePersonaQueRecibe;
    }

    public String getCelularPersonaQueRecibe() {
        return celularPersonaQueRecibe;
    }

    public double getValorDeclarado() {
        return valorDeclarado;
    }

    public float getPeso() {
        return peso;
    }
}
