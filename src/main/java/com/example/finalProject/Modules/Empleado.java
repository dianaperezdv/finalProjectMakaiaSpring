package com.example.finalProject.Modules;

import com.example.finalProject.Modules.Enums.TipoEmpleado;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;

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
                    String direccionResidencia, String ciudadResidencia, String tipoDeSangreRH,
                    Integer antiguedadEmpleado, TipoEmpleado tipoDeEmpleado, String contrasena) {
        super(cedula, nombre, apellido, correoElectronico, celular, direccionResidencia, ciudadResidencia);
        this.tipoDeSangreRH = tipoDeSangreRH;
        this.antiguedadEmpleado = antiguedadEmpleado;
        this.tipoDeEmpleado = tipoDeEmpleado;
        this.contrasena = contrasena;
    }

    public Empleado(){}


}
