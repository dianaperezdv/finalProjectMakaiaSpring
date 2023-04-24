package com.example.finalProject.Modules;

import com.example.finalProject.Exception.InvalidStatementException;
import com.example.finalProject.Modules.Enums.TipoPaquete;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

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
    private String idPaquete;
    @Column (name = "tipoDePaquete")
    @Enumerated(value = EnumType.STRING)
    private TipoPaquete tipoDePaquete;
    @Column (name = "peso")
    private float peso;
    @Column (name = "valorDeclarado")
    private double valorDeclarado;

    public Paquete(float peso, double valorDeclarado){
        Random random = new Random();
        this.idPaquete = RandomStringUtils.randomAlphanumeric(10);
        try {
            this.tipoDePaquete = calcularTipo(peso);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.peso = peso;
        this.valorDeclarado = valorDeclarado;
    }
    public Paquete(){}

    public TipoPaquete getTipoDePaquete() {
        return tipoDePaquete;
    }

    public TipoPaquete calcularTipo(float peso) throws Exception {
        if (peso<0){
            throw new InvalidStatementException("El peso no es válido, ingrese un número mayor a 0");}
        else{
            try {
                if(peso<=2.0){
                    return TipoPaquete.LIVIANO;
                }
                else if (peso>2.0 & peso<5.0){
                    return TipoPaquete.MEDIANO;
                }
                else{
                    return TipoPaquete.GRANDE;
                }
            }
            catch (Exception e){
                throw new InvalidStatementException("El peso no es válido", e);
            }
        }
    }
}
