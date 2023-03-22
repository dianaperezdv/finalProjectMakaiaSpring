package com.example.finalProject.Modules;

import java.time.LocalDate;
public class Empleado extends Usuario {

    private String tipoDeSangreRH;
    private LocalDate antiguedadEmpleado;
    private TipoEmpleado tipoDeEmpleado;


    public Empleado(String cedula, String nombre, String apellido, String correoElectronico, String celular, String direccionDeResidencia, String ciudadDeResidencia, String tipoDeSangreRH, LocalDate antiguedadEmpleado, TipoEmpleado tipoDeEmpleado) {
        super(cedula, nombre, apellido, correoElectronico, celular, direccionDeResidencia, ciudadDeResidencia);
        this.tipoDeSangreRH = tipoDeSangreRH;
        this.antiguedadEmpleado = antiguedadEmpleado;
        this.tipoDeEmpleado = tipoDeEmpleado;
    }

    public String getTipoDeSangreRH() {
        return tipoDeSangreRH;
    }

    public void setTipoDeSangreRH(String tipoDeSangreRH) {
        this.tipoDeSangreRH = tipoDeSangreRH;
    }

    public LocalDate getAntiguedadEmpleado() {
        return antiguedadEmpleado;
    }

    public void setAntiguedadEmpleado(LocalDate antiguedadEmpleado) {
        this.antiguedadEmpleado = antiguedadEmpleado;
    }

    public TipoEmpleado getTipoDeEmpleado() {
        return tipoDeEmpleado;
    }

    public void setTipoDeEmpleado(TipoEmpleado tipoDeEmpleado) {
        this.tipoDeEmpleado = tipoDeEmpleado;
    }
}
