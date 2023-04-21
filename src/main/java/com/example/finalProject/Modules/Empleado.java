package com.example.finalProject.Modules;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name="empleado")
public class Empleado extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="tipoDeSangreRH")
    private String tipoDeSangreRH;
    @Column (name="antiguedadEmpleado")
    private Integer antiguedadEmpleado;
    @Enumerated(value = EnumType.STRING)
    @Column(name="tipoDeEmpleado")
    private TipoEmpleado tipoDeEmpleado;
    @Column (name="contrasena")
    private String contrasena;


    @Autowired
    public Empleado(Integer cedula, String nombre, String apellido, String correoElectronico, String celular,
                    String direccionDeResidencia, String ciudadDeResidencia, String tipoDeSangreRH,
                    Integer antiguedadEmpleado, TipoEmpleado tipoDeEmpleado, String contrasena) {
        super(cedula, nombre, apellido, correoElectronico, celular, direccionDeResidencia, ciudadDeResidencia);
        this.tipoDeSangreRH = tipoDeSangreRH;
        this.antiguedadEmpleado = antiguedadEmpleado;
        this.tipoDeEmpleado = tipoDeEmpleado;
        this.contrasena = contrasena;
    }

    public Empleado(){}


}
