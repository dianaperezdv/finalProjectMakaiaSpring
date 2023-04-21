package com.example.finalProject.Modules;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Random;

@Entity
@Setter
@Getter
@Table(name = "paquete")
public class Paquete implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "idPaquete")
    private Integer idPaquete;
    @Column (name = "tipoDePaquete")
    @Enumerated(value = EnumType.STRING)
    private TipoPaquete tipoDePaquete;
    @Column (name = "peso")
    private float peso;
    @Column (name = "valorDeclarado")
    private double valorDeclarado;

    public Paquete(TipoPaquete tipoDePaquete, float peso, double valorDeclarado){
        Random random = new Random();
        this.idPaquete = random.nextInt();
        this.tipoDePaquete = tipoDePaquete;
        this.peso = peso;
        this.valorDeclarado = valorDeclarado;
    }
    public Paquete(){}

    public TipoPaquete getTipoDePaquete() {
        return tipoDePaquete;
    }
}
