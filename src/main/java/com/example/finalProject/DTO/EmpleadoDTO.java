package com.example.finalProject.DTO;
import com.example.finalProject.Modules.Enums.TipoEmpleado;
import java.time.LocalDate;

public class EmpleadoDTO {

    private Integer cedula;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String celular;
    private String direccionResidencia;
    private String ciudadResidencia;
    private String tipoDeSangreRH;
    private LocalDate antiguedadEmpleado;
    private TipoEmpleado tipoDeEmpleado;

    public EmpleadoDTO(){}
}
