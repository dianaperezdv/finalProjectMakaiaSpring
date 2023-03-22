package com.example.finalProject.Modules;

public abstract class Usuario {
    private String cedula;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String celular;
    private String direccionDeResidencia;
    private String ciudadDeResidencia;

    public Usuario(String cedula, String nombre, String apellido, String correoElectronico, String celular, String direccionDeResidencia, String ciudadDeResidencia) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.celular = celular;
        this.direccionDeResidencia = direccionDeResidencia;
        this.ciudadDeResidencia = ciudadDeResidencia;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccionDeResidencia() {
        return direccionDeResidencia;
    }

    public void setDireccionDeResidencia(String direccionDeResidencia) {
        this.direccionDeResidencia = direccionDeResidencia;
    }

    public String getCiudadDeResidencia() {
        return ciudadDeResidencia;
    }

    public void setCiudadDeResidencia(String ciudadDeResidencia) {
        this.ciudadDeResidencia = ciudadDeResidencia;
    }
}