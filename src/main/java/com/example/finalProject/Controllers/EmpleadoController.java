package com.example.finalProject.Controllers;


import com.example.finalProject.Modules.Empleado;
import com.example.finalProject.Services.EmpleadoService;
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
    public ResponseEntity<Empleado> obtenerEmpleadoPorCedula(@PathVariable Integer cedula){
        return empleadoService.obtenerEmpleadoCedula(cedula);
    }

    @DeleteMapping("/empleados/{cedula}")
    public ResponseEntity eliminar(@PathVariable("cedula") Integer cedula) {
        return empleadoService.eliminar(cedula);
    }

    @PutMapping ("/empleados")
    public ResponseEntity actualizarEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.actualizarEmpleado(empleado);
    }

}
