package com.example.finalProject.Modules;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;


@Entity
@Setter
@Getter
@Table(name= "Envio")
public class Envio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "idGuia")
    private String idGuia;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="clients", foreignKey = @ForeignKey(name = "fk_cliente"),referencedColumnName = "cedula")
    private Cliente cliente;
    @Column (name = "ciudadOrigen")
    private String ciudadOrigen;
    @Column (name = "ciudadDestino")
    private String ciudadDestino;
    @Column (name = "direccionDestino")
    private String direccionDestino;
    @Column (name = "nombrePersonaQueRecibe")
    private String nombrePersonaQueRecibe;
    @Column (name = "celularPersonaQueRecibe")
    private String celularPersonaQueRecibe;
    @Column (name = "horaEntrega")
    private LocalTime horaEntrega;
    @Column (name = "estadoEnvio")
    private String estadoEnvio;
    @Column (name = "valorEnvio")
    private double valorEnvio;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Paquete")
    private Paquete paquete;

    public Envio(Cliente cliente, String ciudadOrigen, String ciudadDestino, String direccionDestino, String nombrePersonaQueRecibe, String celularPersonaQueRecibe, LocalTime horaEntrega, String estadoEnvio, double valorEnvio, Paquete paquete) {
        this.idGuia = RandomStringUtils.randomAlphanumeric(10);
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
    public Envio(){}

    public String getIdGuia() {
        return idGuia;
    }

    public void setIdGuia(String idGuia) {
        this.idGuia = idGuia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public String getNombrePersonaQueRecibe() {
        return nombrePersonaQueRecibe;
    }

    public void setNombrePersonaQueRecibe(String nombrePersonaQueRecibe) {
        this.nombrePersonaQueRecibe = nombrePersonaQueRecibe;
    }

    public String getCelularPersonaQueRecibe() {
        return celularPersonaQueRecibe;
    }

    public void setCelularPersonaQueRecibe(String celularPersonaQueRecibe) {
        this.celularPersonaQueRecibe = celularPersonaQueRecibe;
    }

    public LocalTime getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(LocalTime horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public double getValorEnvio() {
        return valorEnvio;
    }

    public void setValorEnvio(double valorEnvio) {
        this.valorEnvio = valorEnvio;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }
}

