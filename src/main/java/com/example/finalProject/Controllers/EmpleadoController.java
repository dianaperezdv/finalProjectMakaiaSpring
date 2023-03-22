package com.example.finalProject.Controllers;


import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Modules.Empleado;
import com.example.finalProject.Service.EmpleadoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("apiMensajeria/v1")
public class EmpleadoController {

    private EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService){

        this.empleadoService = empleadoService;
    }

    @PostMapping("/empleados")
    public ResponseEntity crearCliente(@RequestBody Empleado empleado) {
        return empleadoService.crearEmpleado(empleado);

    }
    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados() {
        return empleadoService.obtenerEmpleados();
    }

    @GetMapping("/empleados/{cedula}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorCedula(@PathVariable String cedula){
        try {
            Empleado empleado1 = empleadoService.obtenerEmpleadoCedula(cedula);
            return ResponseEntity.ok(empleado1);
        }
        catch (Exception e){
            return new ResponseEntity("No existe un empleado con esa cedula", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/empleados/{cedula}")
    public String eliminar(@PathVariable("cedula") String cedula) {
        return empleadoService.eliminar(cedula);
    }

    @PutMapping ("/empleados/{cedula}")
    public ResponseEntity actualizarEmpleado(@PathVariable("cedula") String cedula, @RequestBody Empleado empleado)
    {       try {
        Empleado empleado1 = empleadoService.actualizarEmpleado(empleado);
        return ResponseEntity.ok(empleado1);
    }
    catch (Exception e){
        return new ResponseEntity("No se pudo actualizar", HttpStatus.BAD_REQUEST);
    }
    }



}
