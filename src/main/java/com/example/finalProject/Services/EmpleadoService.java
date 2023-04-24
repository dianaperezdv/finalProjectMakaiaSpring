package com.example.finalProject.Services;

import com.example.finalProject.Exception.InvalidStatementException;
import com.example.finalProject.Modules.Empleado;
import com.example.finalProject.Repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Empleado crearEmpleado(Empleado empleado) {
        if (verificarEmpleadoExiste(empleado.getCedula())){
            throw new InvalidStatementException("Ya existe un empleado con la cédula "  + empleado.getCedula() + " en el sistema");
        }
        else{
            if((empleado.getCedula().toString()).length() == 10){
                if(validateEmail(empleado.getCorreoElectronico())){
                    return this.empleadoRepository.save(empleado);
                }
                else{
                    throw new InvalidStatementException("El correo electrónico no es válido");
                }
            }
            else{
                throw new InvalidStatementException("La cédula debe tener 10 dígitos");
            }
        }
    }
    public Empleado obtenerEmpleadoCedula(Integer cedula) {
        if (verificarEmpleadoExiste(cedula)){
            return (this.empleadoRepository.findById(cedula)).get();
        }
        else{
            throw new InvalidStatementException("No existe un empleado con la cédula número " + cedula);
        }
    }

    public Empleado actualizarEmpleado (Empleado empleado) {
        if(verificarEmpleadoExiste(empleado.getCedula())){
            return this.empleadoRepository.save(empleado);
        }
        else {
            throw new InvalidStatementException("No existe un empleado con la cédula "  + empleado.getCedula() + " en el sistema");
        }
    }

    public ResponseEntity eliminar(Integer cedula) {
        if (!verificarEmpleadoExiste(cedula)){
            throw new InvalidStatementException("No existe un empleado con la cédula "  + cedula + " en el sistema" );
        }
        else{
            this.empleadoRepository.deleteById(cedula);
            return new ResponseEntity("El empleado " + cedula+ " se ha eliminado con éxito", HttpStatus.ACCEPTED);
        }
    }

    public boolean validateEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

}
