package com.example.finalProject.Modules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter @Getter
@Table(name="cliente")
public class Cliente extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "contrasena")
    private String contrasena;


    public Cliente(Integer cedula, String nombre, String apellido, String correoElectronico,
                   String celular, String direccionResidencia, String ciudadResidencia) {
        super(cedula,nombre, apellido, correoElectronico, celular, direccionResidencia, ciudadResidencia);
    }
    public Cliente(){}

}
