package com.example.finalProject.Controllers;


import com.example.finalProject.Modules.Empleado;
import com.example.finalProject.Services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('WRITE')")
    @PostMapping("/empleados")
    public Empleado crearEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.crearEmpleado(empleado);

    }
    @PreAuthorize("hasRole('READ')")
    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados() {
        return empleadoService.obtenerEmpleados();
    }

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/empleados/{cedula}")
    public Empleado obtenerEmpleadoPorCedula(@PathVariable Integer cedula){
        return empleadoService.obtenerEmpleadoCedula(cedula);
    }

    @PreAuthorize("hasRole('WRITE')")
    @DeleteMapping("/empleados/{cedula}")
    public ResponseEntity eliminarEmpleado(@PathVariable("cedula") Integer cedula) {
        return empleadoService.eliminar(cedula);
    }

    @PreAuthorize("hasRole('WRITE')")
    @PutMapping ("/empleados")
    public Empleado actualizarEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.actualizarEmpleado(empleado);
    }

}
