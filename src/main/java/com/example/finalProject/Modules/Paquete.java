package com.example.finalProject.Modules;

import java.util.Random;

public class Paquete {
    private int idPaquete;
    private TipoPaquete tipoDePaquete;
    private float peso;
    private double valorDeclarado;

    public Paquete(TipoPaquete tipoDePaquete, float peso, double valorDeclarado){
        Random random = new Random();
        this.idPaquete = random.nextInt();
        this.tipoDePaquete = tipoDePaquete;
        this.peso = peso;
        this.valorDeclarado = valorDeclarado;
    }
}
