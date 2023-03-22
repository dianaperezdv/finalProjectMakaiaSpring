package com.example.finalProject.Service;

import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Modules.Empleado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoService {
    private List<Empleado> empleados;
    public EmpleadoService(){
        this.empleados = new ArrayList<>();
    }

    public List<Empleado> obtenerEmpleados() {
        return empleados;
    }

    public boolean verificarCedulaNoUsada(String cedula) {
        return (empleados.stream()
                .filter(x -> x.getCedula().equals(cedula))
                .findFirst().isEmpty());
    }
    public ResponseEntity crearEmpleado(Empleado empleado) {
        if (!verificarCedulaNoUsada(empleado.getCedula())){
            return new ResponseEntity("Ya existe un cliente con esta cédula", HttpStatus.BAD_REQUEST);
        }
        else{
            this.empleados.add(empleado);
            return new ResponseEntity(empleado,HttpStatus.ACCEPTED);
        }
    }
    public Empleado obtenerEmpleadoCedula(String cedula) {
        return empleados.stream()
                .filter(x -> x.getCedula().equals(cedula))
                .findFirst().get();
    }

    public Empleado actualizarEmpleado (Empleado empleado) throws IllegalAccessException {
        Empleado empleadoExistente = obtenerEmpleadoCedula(empleado.getCedula());
        if(empleadoExistente != null){

            Class<?> empleadoClass  = empleado.getClass();
            Field[] campos = empleadoClass.getDeclaredFields();
            for(Field campo : campos){
                campo.setAccessible(true);
                Object valorCampo = campo.get(empleado);
                if(valorCampo!=null){
                    campo.set(empleado,valorCampo);
                }

            }
            return empleadoExistente;

        }
        return empleadoExistente;
    }

    public String eliminar(String cedula) {
        try{
            Empleado empleado = obtenerEmpleadoCedula(cedula);
            empleados.remove(empleado);
            String exito = "El empleado se ha eliminado con éxito";
            return exito;
        }
        catch (Exception e){
            String error = "No se ha podido guardar el empleado";
            return error;
        }

    }

}
