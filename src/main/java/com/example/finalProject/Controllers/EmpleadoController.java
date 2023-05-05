package com.example.finalProject.Controllers;


import com.example.finalProject.Modules.Empleado;
import com.example.finalProject.Services.EmpleadoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("apiMensajeria/v1")
@Api(value = "Clientes", description = "Controlador de gestión de empleados")
public class EmpleadoController {

    private EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService){

        this.empleadoService = empleadoService;
    }

    @PreAuthorize("hasRole('WRITE')")
    @PostMapping("/empleados")
    @ApiOperation(value = "Crear un nuevo empleado", response = Empleado.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "El empleado ha sido creado exitosamente", response = Empleado.class),
            @ApiResponse(code = 400, message = "La solicitud es incorrecta", response = Exception.class),
            @ApiResponse(code = 401, message = "No autorizado", response = Empleado.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = Exception.class)})

    public Empleado crearEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.crearEmpleado(empleado);

    }
    @PreAuthorize("hasRole('READ')")
    @GetMapping("/empleados")
    @ApiOperation(value = "Obtener lista de los empleados registrados en la base de datos", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lista de empleados obtenida exitosamente", response = List.class),
            @ApiResponse(code = 401, message = "No autorizado", response = List.class),
            @ApiResponse(code = 403, message = "Prohibido", response = List.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = List.class)

    })
    public List<Empleado> obtenerEmpleados() {
        return empleadoService.obtenerEmpleados();
    }

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/empleados/{cedula}")
    @ApiOperation(value = "Obtener un empleado por cédula", response = Empleado.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Empleado obtenido exitosamente", response = Empleado.class),
            @ApiResponse(code = 401, message = "No autorizado", response = Empleado.class),
            @ApiResponse(code = 403, message = "Prohibido", response = Empleado.class),
            @ApiResponse(code = 404, message = "Recurso no encontrado o empleado inexistente", response = Exception.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = Empleado.class)
    })
    public Empleado obtenerEmpleadoPorCedula(@PathVariable Integer cedula){
        return empleadoService.obtenerEmpleadoCedula(cedula);
    }

    @PreAuthorize("hasRole('WRITE')")
    @DeleteMapping("/empleados/{cedula}")
    @ApiOperation(value = "Eliminar un empleado de la base de datos", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Empleado eliminado exitosamente", response = ResponseEntity.class),
            @ApiResponse(code = 401, message = "No autorizado", response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "Prohibido", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Recurso no encontrado o empleado inexistente", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = ResponseEntity.class)})
    public ResponseEntity eliminarEmpleado(@PathVariable("cedula") Integer cedula) {
        return empleadoService.eliminar(cedula);
    }

    @PreAuthorize("hasRole('WRITE')")
    @PutMapping ("/empleados")
    @ApiOperation(value = "Actualizar información de un empleado", response = Empleado.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Empleado fue actualizado exitosamente", response = Empleado.class),
            @ApiResponse(code = 400, message = "Solicitud incorrecta", response = Exception.class),
            @ApiResponse(code = 404, message = "El empleado no existe", response = Exception.class),
            @ApiResponse(code = 401, message = "No autorizado", response = Empleado.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = Empleado.class)})
    public Empleado actualizarEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.actualizarEmpleado(empleado);
    }

}
