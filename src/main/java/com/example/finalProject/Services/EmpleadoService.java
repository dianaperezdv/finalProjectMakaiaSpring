package com.example.finalProject.Services;

import com.example.finalProject.Exception.InvalidStatementException;
import com.example.finalProject.Modules.Empleado;
import com.example.finalProject.Repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    private EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository){
        this.empleadoRepository = empleadoRepository;
    }
    

    public List<Empleado> obtenerEmpleados() {
        return this.empleadoRepository.findAll();
    }

    public boolean verificarEmpleadoExiste(Integer cedula) {
        Optional<Empleado> empleado1 = this.empleadoRepository.findById(cedula);
        return empleado1.isPresent();
    }
    public ResponseEntity crearEmpleado(Empleado empleado) {
        if (verificarEmpleadoExiste(empleado.getCedula())){
            return new ResponseEntity("Ya existe un empleado con esta cédula", HttpStatus.BAD_REQUEST);
        }
        else{
            if((empleado.getCedula().toString()).length() != 10){
            this.empleadoRepository.save(empleado);
            return new ResponseEntity(empleado,HttpStatus.ACCEPTED);
        }
            else{
            return new ResponseEntity("La cédula debe tener 10 dígitos", HttpStatus.BAD_REQUEST);
        }
    }
    }
    public Empleado obtenerEmpleadoCedula(Integer cedula) {
        return (this.empleadoRepository.findById(cedula)).get();
    }

    public ResponseEntity actualizarEmpleado (Empleado empleado) throws IllegalAccessException {
        if(verificarEmpleadoExiste(empleado.getCedula())){
            this.empleadoRepository.save(empleado);
            return new ResponseEntity(obtenerEmpleadoCedula(empleado.getCedula()), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity("No existe un empleado con esta cedula", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity eliminar(Integer cedula) {
        try{
            this.empleadoRepository.deleteById(cedula);
            return new ResponseEntity("El empleado " + cedula+ " se ha eliminado con éxito", HttpStatus.ACCEPTED);

        }
        catch (Exception e){
            throw new InvalidStatementException("No se ha podido eliminar el empleado");
        }

    }

}
