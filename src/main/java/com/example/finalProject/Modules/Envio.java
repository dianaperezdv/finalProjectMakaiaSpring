package com.example.finalProject.Modules;

import com.example.finalProject.Modules.Enums.EstadoEnvio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name="cliente", foreignKey = @ForeignKey(name = "fk_cliente"),referencedColumnName = "cedula")
    private Cliente cliente;
    @Column (name = "ciudadOrigen")
    private String ciudadOrigen;
    @Column (name = "ciudadDestino")
    private String ciudadDestino;
    @Column (name = "direccionDestino")
    private String direccionDestino;
    @Column (name = "nombreRecibe")
    private String nombreRecibe;
    @Column (name = "celularRecibe")
    private String celularRecibe;
    @Column (name = "horaEntrega")
    private LocalTime horaEntrega;
    @Column (name = "estadoEnvio")
    @Enumerated(EnumType.STRING)
    private EstadoEnvio estadoEnvio;
    @Column (name = "valorEnvio")
    private double valorEnvio;
    @OneToOne(fetch=FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name="Paquete")
    private Paquete paquete;

    public Envio(Cliente cliente, String ciudadOrigen, String ciudadDestino, String direccionDestino, String nombreRecibe, String celularRecibe, LocalTime horaEntrega, EstadoEnvio estadoEnvio, double valorEnvio, Paquete paquete) {
        this.idGuia = RandomStringUtils.randomAlphanumeric(10);
        this.cliente = cliente;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.direccionDestino = direccionDestino;
        this.nombreRecibe = nombreRecibe;
        this.celularRecibe = celularRecibe;
        this.horaEntrega = horaEntrega;
        this.estadoEnvio = estadoEnvio;
        this.valorEnvio = valorEnvio;
        this.paquete = paquete;
    }

    public Envio(String idGuia, Cliente cliente, String ciudadOrigen, String ciudadDestino, String direccionDestino, String nombreRecibe, String celularRecibe, LocalTime horaEntrega, EstadoEnvio estadoEnvio, double valorEnvio, Paquete paquete) {
        this.idGuia = idGuia;
        this.cliente = cliente;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.direccionDestino = direccionDestino;
        this.nombreRecibe = nombreRecibe;
        this.celularRecibe = celularRecibe;
        this.horaEntrega = horaEntrega;
        this.estadoEnvio = estadoEnvio;
        this.valorEnvio = valorEnvio;
        this.paquete = paquete;
    }
    public Envio(){}

}

