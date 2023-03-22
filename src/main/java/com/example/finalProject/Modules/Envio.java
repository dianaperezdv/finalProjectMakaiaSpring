package com.example.finalProject.Modules;

import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Modules.Paquete;

import java.time.LocalTime;

public class Envio {
    private String idGuia;
    private Cliente cliente;
    private String ciudadOrigen;
    private String ciudadDestino;
    private String direccionDestino;
    private String nombrePersonaQueRecibe;
    private String celularPersonaQueRecibe;
    private LocalTime horaEntrega;
    private String estadoEnvio;
    private double valorEnvio;
    private Paquete paquete;

    public Envio(String idGuia, Cliente cliente, String ciudadOrigen, String ciudadDestino, String direccionDestino, String nombrePersonaQueRecibe, String celularPersonaQueRecibe, LocalTime horaEntrega, String estadoEnvio, double valorEnvio, Paquete paquete) {
        //    Random random = new Random();
        //    this.numeroGuia = String.valueOf(random.nextInt(1_000_000_000));
        this.idGuia = idGuia;
        this.cliente = cliente;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.direccionDestino = direccionDestino;
        this.nombrePersonaQueRecibe = nombrePersonaQueRecibe;
        this.celularPersonaQueRecibe = celularPersonaQueRecibe;
        this.horaEntrega = horaEntrega;
        this.estadoEnvio = estadoEnvio;
        this.valorEnvio = valorEnvio;
        this.paquete = paquete;
    }
}
